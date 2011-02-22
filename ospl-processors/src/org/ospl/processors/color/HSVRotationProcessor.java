package org.ospl.processors.color;

import org.ospl.AbstractProcessor;
import org.ospl.Node;

@org.ospl.annotation.Processor(name = { "HSVRotation" })
public class HSVRotationProcessor extends AbstractProcessor {
  
  @org.ospl.annotation.Sink
  public double rotation;
  
  @org.ospl.annotation.Sink
  public HSV input;
  
  @org.ospl.annotation.Source
  public HSV output;
  
  public HSVRotationProcessor(final Node node) {
    super(node);
  }
  
  @Override
  public void process() {
    double hue = (this.input.getHue() + (this.rotation / 60.0)) % 6.0;
    double saturation = this.input.getSaturation();
    double value = this.input.getValue();
    
    this.output = new HSV(hue, saturation, value);
  }
}