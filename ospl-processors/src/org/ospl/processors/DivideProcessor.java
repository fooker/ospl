package org.ospl.processors;

import org.ospl.AbstractProcessor;
import org.ospl.Node;

@org.ospl.annotation.Processor(name = { "Divide", "/" })
public class DivideProcessor extends AbstractProcessor {
  
  @org.ospl.annotation.Sink
  public double value1;
  
  @org.ospl.annotation.Sink
  public double value2;
  
  @org.ospl.annotation.Source
  public double result;
  
  public DivideProcessor(final Node node) {
    super(node);
  }
  
  @Override
  public void process() {
    double value1 = this.value1;
    double value2 = this.value2;
    
    this.result = value1 / value2;
  }
}