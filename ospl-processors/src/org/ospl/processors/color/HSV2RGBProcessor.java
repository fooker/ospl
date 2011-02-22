package org.ospl.processors.color;

import org.ospl.AbstractProcessor;
import org.ospl.Node;

@org.ospl.annotation.Processor(name = { "HSV2RGB" })
public class HSV2RGBProcessor extends AbstractProcessor {
  
  @org.ospl.annotation.Sink
  public HSV hsv;
  
  @org.ospl.annotation.Source
  public RGB rgb;
  
  public HSV2RGBProcessor(final Node node) {
    super(node);
  }
  
  @Override
  public void process() {
    double h = this.hsv.getHue();
    double s = this.hsv.getSaturation();
    double v = this.hsv.getValue();
    
    if (Double.isNaN(h)) {
      this.rgb = new RGB(v, v, v);
      return;
    }
    
    int i = ((int) Math.floor(h));
    
    double f = h - i;
    if (i % 2 == 0) {
      f = 1 - f;
    }
    
    double m = v * (1 - s);
    double n = v * (1 - s * f);
    
    switch (i) {
    case 0:
      this.rgb = new RGB(v, n, m);
      break;
    
    case 1:
      this.rgb = new RGB(n, v, m);
      break;
    
    case 2:
      this.rgb = new RGB(m, v, n);
      break;
    
    case 3:
      this.rgb = new RGB(m, n, v);
      break;
    
    case 4:
      this.rgb = new RGB(n, m, v);
      break;
    
    case 5:
      this.rgb = new RGB(v, m, n);
      break;
    }
  }
}