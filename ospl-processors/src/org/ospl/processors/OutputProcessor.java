package org.ospl.processors;

import org.ospl.AbstractProcessor;
import org.ospl.Node;

@org.ospl.annotation.Processor(name = { "Output" })
public class OutputProcessor extends AbstractProcessor {
  
  @org.ospl.annotation.Sink
  public String prefix;
  
  @org.ospl.annotation.Sink
  public Object value;
  
  public OutputProcessor(final Node node) {
    super(node);
  }
  
  @Override
  public void process() {
    StringBuilder s = new StringBuilder();
    
    if (this.prefix != null) {
      s.append(this.prefix);
      s.append(": ");
    }
    
    s.append(this.value);
    
    System.out.println(s);
  }
}