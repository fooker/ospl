package org.ospl.processors.color;

public class HSV {
  
  private final double hue;
  private final double saturation;
  private final double value;
  
  public HSV(final double hue, final double saturation, final double value) {
    this.hue = hue;
    this.saturation = saturation;
    this.value = value;
  }
  
  public final double getHue() {
    return this.hue;
  }
  
  public final double getSaturation() {
    return this.saturation;
  }
  
  public final double getValue() {
    return this.value;
  }
}
