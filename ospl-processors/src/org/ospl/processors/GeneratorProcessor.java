package org.ospl.processors;

import org.ospl.AbstractProcessor;
import org.ospl.Node;

@org.ospl.annotation.Processor(name = { "Generator" })
public class GeneratorProcessor extends AbstractProcessor {
  
  @org.ospl.annotation.Sink
  public Void emit;
  
  @org.ospl.annotation.Source
  public Double value;
  
  public GeneratorProcessor(final Node node) {
    super(node);
  }
  
  @Override
  public void process() {
    this.value = Math.random() * 256;
  }
}