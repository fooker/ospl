package org.ospl.processors.color;

import org.ospl.AbstractProcessor;
import org.ospl.Node;

@org.ospl.annotation.Processor(name = { "HSVDecompositor" })
public class HSVDecompositorProcessor extends AbstractProcessor {
  
  @org.ospl.annotation.Sink
  public HSV hsv;
  
  @org.ospl.annotation.Source
  public double hue;
  
  @org.ospl.annotation.Source
  public double saturation;
  
  @org.ospl.annotation.Source
  public double value;
  
  public HSVDecompositorProcessor(final Node node) {
    super(node);
  }
  
  @Override
  public void process() {
    this.hue = this.hsv.getHue();
    this.saturation = this.hsv.getSaturation();
    this.value = this.hsv.getValue();
  }
}