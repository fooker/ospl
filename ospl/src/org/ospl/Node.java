package org.ospl;

import java.util.Collection;

public class Node {
  
  private Context context;
  
  private String name;
  
  private ProcessorImplementation processor;
  
  public Node(final Context context, final String name) {
    this.context = context;
    this.name = name;
  }
  
  public final void process() {
    this.processor.process();
  }
  
  public final Sink getSink(final SinkDescription description) {
    return this.processor.getSinks().get(description);
  }
  
  public final Source getSource(final SourceDescription description) {
    return this.processor.getSources().get(description);
  }
  
  public final Sink getSink(final String name) {
    ProcessorDescription processorDescription = this.processor.getDescription();
    SinkDescription description = processorDescription.getSinkDescriptions().get(name);
    
    return this.getSink(description);
  }
  
  public final Source getSource(final String name) {
    ProcessorDescription processorDescription = this.processor.getDescription();
    SourceDescription description = processorDescription.getSourceDescriptions().get(name);
    
    return this.getSource(description);
  }
  
  public final Context getContext() {
    return this.context;
  }
  
  public final String getName() {
    return this.name;
  }
  
  public final ProcessorImplementation getProcessor() {
    return this.processor;
  }
  
  public final void setProcessor(final ProcessorImplementation processor) {
    this.processor = processor;
  }
  
  public final Collection<Sink> getSinks() {
    return this.processor.getSinks().values();
  }
  
  public final Collection<Source> getSources() {
    return this.processor.getSources().values();
  }
}
