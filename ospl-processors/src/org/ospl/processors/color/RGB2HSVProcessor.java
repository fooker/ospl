package org.ospl.processors.color;

import org.ospl.AbstractProcessor;
import org.ospl.Node;

@org.ospl.annotation.Processor(name = { "RGB2HSV" })
public class RGB2HSVProcessor extends AbstractProcessor {
  
  @org.ospl.annotation.Sink
  public RGB rgb;
  
  @org.ospl.annotation.Source
  public HSV hsv;
  
  public RGB2HSVProcessor(final Node node) {
    super(node);
  }
  
  @Override
  public void process() {
    double r = this.rgb.getRed() / 256.0;
    double g = this.rgb.getGreen() / 256.0;
    double b = this.rgb.getBlue() / 256.0;
    
    double min = Math.min(Math.min(r, g), b);
    double max = Math.max(Math.max(r, g), b);
    
    if (min == max) {
      this.hsv = new HSV(Double.NaN, 0, max);
      return;
    }
    
    double f = (b == min) ? r - g : ((g == min) ? b - r : g - b);
    double i = (b == min) ? 1 : ((g == min) ? 5 : 3);
    
    this.hsv = new HSV(i - f / (max - min), (max - min) / max, max);
  }
}