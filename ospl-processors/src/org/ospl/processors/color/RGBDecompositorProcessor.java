package org.ospl.processors.color;

import org.ospl.AbstractProcessor;
import org.ospl.Node;

@org.ospl.annotation.Processor(name = { "RGBDecompositor" })
public class RGBDecompositorProcessor extends AbstractProcessor {
  
  @org.ospl.annotation.Sink
  public RGB rgb;
  
  @org.ospl.annotation.Source
  public double red;
  
  @org.ospl.annotation.Source
  public double green;
  
  @org.ospl.annotation.Source
  public double blue;
  
  public RGBDecompositorProcessor(final Node node) {
    super(node);
  }
  
  @Override
  public void process() {
    this.red = this.rgb.getRed();
    this.green = this.rgb.getGreen();
    this.blue = this.rgb.getBlue();
  }
}