package org.ospl.processors;

import org.ospl.AbstractProcessor;
import org.ospl.Node;

@org.ospl.annotation.Processor(name = { "Increment", "++" })
public class IncrementProcessor extends AbstractProcessor {
  
  @org.ospl.annotation.Sink
  public Void emit;
  
  @org.ospl.annotation.Sink
  public double step;
  
  @org.ospl.annotation.Source
  public double value;
  
  public IncrementProcessor(final Node node) {
    super(node);
  }
  
  @Override
  public void process() {
    this.value += this.step;
  }
}