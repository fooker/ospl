package org.ospl.processors.color;

import org.ospl.AbstractProcessor;
import org.ospl.Node;

@org.ospl.annotation.Processor(name = { "HSVCompositor" })
public class HSVCompositorProcessor extends AbstractProcessor {
  
  public HSVCompositorProcessor(final Node node) {
    super(node);
  }
  
  @org.ospl.annotation.Sink
  public double hue;
  
  @org.ospl.annotation.Sink
  public double saturation;
  
  @org.ospl.annotation.Sink
  public double value;
  
  @org.ospl.annotation.Source
  public HSV hsv;
  
  @Override
  public void process() {
    this.hsv = new HSV(this.hue, this.saturation, this.value);
  }
}