package org.ospl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public abstract class ProcessorDescription {
  
  private final Set<String> names;
  
  private final Map<String, SinkDescription> sinkDescriptions;
  
  private final Map<String, SourceDescription> sourceDescriptions;
  
  public ProcessorDescription() {
    this.names = new HashSet<String>();
    this.sinkDescriptions = new HashMap<String, SinkDescription>();
    this.sourceDescriptions = new HashMap<String, SourceDescription>();
  }
  
  protected abstract ProcessorImplementation createProcessor(Node node);
  
  public final Node createNode(final Context context, final String name) {
    Node node = new Node(context, name);
    node.setProcessor(this.createProcessor(node));
    
    return node;
  }
  
  public final Map<String, SinkDescription> getSinkDescriptions() {
    return this.sinkDescriptions;
  }
  
  public final Map<String, SourceDescription> getSourceDescriptions() {
    return this.sourceDescriptions;
  }
  
  protected void addName(final String name) {
    this.names.add(name);
  }
  
  protected void addSinkDescription(final String name) {
    this.sinkDescriptions.put(name, new SinkDescription(name));
  }
  
  protected void addSourceDescription(final String name) {
    this.sourceDescriptions.put(name, new SourceDescription(name));
  }
  
  public final Map<SinkDescription, Sink> createSinks(final Node owner) {
    Map<SinkDescription, Sink> sinks = new HashMap<SinkDescription, Sink>();
    for (SinkDescription description : this.sinkDescriptions.values()) {
      sinks.put(description, new Sink(owner, description));
    }
    
    return sinks;
  }
  
  public final Map<SourceDescription, Source> createSources(final Node owner) {
    Map<SourceDescription, Source> sources = new HashMap<SourceDescription, Source>();
    for (SourceDescription description : this.sourceDescriptions.values()) {
      sources.put(description, new NodeSource(owner, description));
    }
    
    return sources;
  }
  
  public final Set<String> getNames() {
    return this.names;
  }
}
