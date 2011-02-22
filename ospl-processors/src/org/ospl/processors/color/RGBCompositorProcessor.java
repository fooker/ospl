package org.ospl.processors.color;

import org.ospl.AbstractProcessor;
import org.ospl.Node;

@org.ospl.annotation.Processor(name = { "RGBCompositor" })
public class RGBCompositorProcessor extends AbstractProcessor {
  
  @org.ospl.annotation.Sink
  public double red;
  
  @org.ospl.annotation.Sink
  public double green;
  
  @org.ospl.annotation.Sink
  public double blue;
  
  @org.ospl.annotation.Source
  public RGB rgb;
  
  public RGBCompositorProcessor(final Node node) {
    super(node);
  }
  
  @Override
  public void process() {
    this.rgb = new RGB(this.red, this.green, this.blue);
  }
}