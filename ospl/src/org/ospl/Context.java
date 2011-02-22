package org.ospl;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.Set;

import org.ospl.exception.UnknownProcessorDescriptionException;
import org.ospl.exception.UnknownSinkException;
import org.ospl.exception.UnknownSourceException;
import org.ospl.exception.UnknownValueConverterException;
import org.ospl.generator.GeneratedProcessorDescription;

public class Context {
  
  private final Map<String, ProcessorDescription> processorDescriptions;
  
  private final Map<Class<?>, ValueConverter> valueConverters;
  
  private final Map<String, Node> nodes;
  
  private final Set<Node> invalidNodes;
  
  private final List<ContextListener> listeners;
  
  private final Thread thread;
  
  public Context() {
    this.processorDescriptions = new HashMap<String, ProcessorDescription>();
    this.valueConverters = new HashMap<Class<?>, ValueConverter>();
    this.nodes = new HashMap<String, Node>();
    this.invalidNodes = new HashSet<Node>();
    this.listeners = new LinkedList<ContextListener>();
    
    this.thread = Thread.currentThread();
    
    this.registerProcessorDescriptions(Context.loadDefinedProcessorDescriptions());
    this.registerProcessorDescriptions(Context.loadGeneratedProcessorDescriptions());
    
    this.registerValueConverters(Context.loadDefinedValueConverters());
  }
  
  private final static Iterable<? extends ProcessorDescription> loadDefinedProcessorDescriptions() {
    return ServiceLoader.load(ProcessorDescription.class);
  }
  
  private final static Iterable<? extends ProcessorDescription> loadGeneratedProcessorDescriptions() {
    return ServiceLoader.load(GeneratedProcessorDescription.class);
  }
  
  private final static Iterable<? extends ValueConverter> loadDefinedValueConverters() {
    return ServiceLoader.load(ValueConverter.class);
  }
  
  public final void registerProcessorDescriptions(final Iterable<? extends ProcessorDescription> processorDescriptions) {
    for (ProcessorDescription processorDescription : processorDescriptions) {
      this.registerProcessorDescription(processorDescription);
    }
  }
  
  public final void registerProcessorDescription(final ProcessorDescription processorDescription) {
    for (String name : processorDescription.getNames()) {
      System.err.println("Found processor description '" + name + "' using class '" + processorDescription.getClass().getName() + "'");
      this.processorDescriptions.put(name, processorDescription);
    }
  }
  
  public final Collection<ProcessorDescription> getProcessorDescription() {
    return this.processorDescriptions.values();
  }
  
  public final void registerValueConverters(final Iterable<? extends ValueConverter> valueConverters) {
    for (ValueConverter valueConverter : valueConverters) {
      this.registerValueConverter(valueConverter);
    }
  }
  
  public final void registerValueConverter(final ValueConverter valueConverter) {
    System.err.println("Found value converter for '" + valueConverter.getValueClass().getName() + "' using class '" + valueConverter.getClass().getName() + "'");
    this.valueConverters.put(valueConverter.getValueClass(), valueConverter);
  }
  
  public final ValueConverter getValueConverter(final Class<?> valueClass) {
    ValueConverter valueConverter = this.valueConverters.get(valueClass);
    
    if (valueConverter == null) {
      throw new UnknownValueConverterException(valueClass);
    }
    
    return valueConverter;
  }
  
  public final ValueConverter getValueConverter(final Sink sink) {
    return this.getValueConverter(sink.getFieldClass());
  }
  
  public final Node createNode(final String descriptionName, final String nodeName) {
    ProcessorDescription description = this.processorDescriptions.get(descriptionName);
    
    if (description == null) {
      throw new UnknownProcessorDescriptionException(descriptionName);
    }
    
    Node node = description.createNode(this, nodeName);
    this.nodes.put(nodeName, node);
    this.invalidNodes.add(node);
    
    this.fireNodeAdded(node);
    
    return node;
  }
  
  public final ValueSource createValueSource(final Node sinkNode, final String sinkName, final String valueString) {
    Sink sink = sinkNode.getSink(sinkName);
    
    ValueConverter valueConverter = this.getValueConverter(sink);
    Object value = valueConverter.parse(valueString);
    
    ValueSource valueSource = new ValueSource(sinkNode, value);
    
    valueSource.connect(sink);
    
    this.invalidNodes.add(sinkNode);
    
    this.fireConnected(sink, valueSource);
    
    return valueSource;
  }
  
  public final Node getNode(final String name) {
    return this.nodes.get(name);
  }
  
  public final Collection<Node> getNodes() {
    return this.nodes.values();
  }
  
  public final void removeNode(final String name) {
    this.nodes.remove(name);
  }
  
  public final void removeNode(final Node node) {
    this.nodes.remove(node.getName());
    
    this.fireNodeRemoved(node);
  }
  
  public final void connect(final Node sourceNode, final SinkDescription sinkDescription, final Node sinkNode, final SourceDescription sourceDescription) {
    Sink sink = sinkNode.getSink(sinkDescription);
    Source source = sourceNode.getSource(sourceDescription);
    
    source.connect(sink);
    
    this.fireConnected(sink, source);
  }
  
  public final void connect(final Node sourceNode, final String sourceName, final Node sinkNode, final String sinkName) {
    Sink sink = sinkNode.getSink(sinkName);
    Source source = sourceNode.getSource(sourceName);
    
    if (sink == null) {
      throw new UnknownSinkException(sinkNode, sinkName);
    }
    
    if (source == null) {
      throw new UnknownSourceException(sourceNode, sourceName);
    }
    
    source.connect(sink);
    
    this.fireConnected(sink, source);
  }
  
  public final boolean isValid(final Node node) {
    return !this.invalidNodes.contains(node);
  }
  
  private final void validate(final Node node) {
    Logger.log("VALID NODE: '" + node.getName() + "'");
    Logger.indent();
    
    if (this.isValid(node)) {
      Logger.outdent();
      return;
    }
    
    this.invalidNodes.remove(node);
    Logger.log("VALIDATED");
    
    for (final Sink sink : node.getSinks()) {
      this.validate(sink);
    }
    
    node.process();
    
    Logger.outdent();
  }
  
  private final void validate(final Source source) {
    Logger.log("  VALID SOURCE: '" + source.getDescription().getName() + "'");
    Logger.indent();
    
    this.validate(source.getOwner());
    
    Logger.outdent();
  }
  
  private final void validate(final Sink sink) {
    Logger.log("VALID SINK: '" + sink.getDescription().getName() + "'");
    Logger.indent();
    
    if (sink.getSource() == null) {
      Logger.outdent();
      return;
    }
    
    this.validate(sink.getSource());
    
    sink.setValue(sink.getSource().getValue());
    
    Logger.log("COPY: '" + sink.getOwner().getName() + "'.'" + sink.getDescription().getName() + "' = '" + sink.getSource().getOwner().getName() + "'.'" + sink.getSource().getDescription().getName() + "'");
    Logger.outdent();
  }
  
  private final void invalidate(final Node node) {
    this.invalidate(node, true);
  }
  
  private final void invalidate(final Node node, final boolean self) {
    Logger.log("INVALID NODE: '" + node.getName() + "'");
    Logger.indent();
    
    if (!this.isValid(node)) {
      Logger.outdent();
      return;
    }
    
    if (self) {
      this.invalidNodes.add(node);
      Logger.log("INVALIDATED");
    }
    
    for (Source source : node.getSources()) {
      this.invalidate(source);
    }
    
    Logger.outdent();
  }
  
  private final void invalidate(final Sink sink) {
    Logger.log("INVALID SINK: '" + sink.getDescription().getName() + "'");
    Logger.indent();
    
    this.invalidate(sink.getOwner());
    
    Logger.outdent();
  }
  
  private final void invalidate(final Source source) {
    Logger.log("INVALID SOURCE: '" + source.getDescription().getName() + "'");
    Logger.indent();
    
    for (Sink sink : source.getSinks()) {
      this.invalidate(sink);
    }
    
    Logger.outdent();
  }
  
  public final void revalidate() {
    while (!this.invalidNodes.isEmpty()) {
      Node node = this.invalidNodes.iterator().next();
      this.validate(node);
    }
  }
  
  public final void update(final Node node, final boolean self) {
    // System.err.println(this.thread + " : " + Thread.currentThread());
    
    this.invalidate(node, self);
    this.revalidate();
  }
  
  public final void addListener(final ContextListener listener) {
    this.listeners.add(listener);
  }
  
  public final void removeListener(final ContextListener listener) {
    this.listeners.remove(listener);
  }
  
  private final void fireNodeAdded(final Node node) {
    for (ContextListener listener : this.listeners) {
      listener.nodeAdded(node);
    }
  }
  
  private final void fireNodeRemoved(final Node node) {
    for (ContextListener listener : this.listeners) {
      listener.nodeRemoved(node);
    }
  }
  
  private final void fireConnected(final Sink sink, final Source source) {
    for (ContextListener listener : this.listeners) {
      listener.connected(sink, source);
    }
  }
  
  // private final void fireDisconnected(final Sink sink, final Source source) {
  // for (ContextListener listener : this.listeners) {
  // listener.disconnected(sink, source);
  // }
  // }
}
