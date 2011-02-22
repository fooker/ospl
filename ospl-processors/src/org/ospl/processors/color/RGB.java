package org.ospl.processors.color;

public class RGB {
  
  private final double red;
  private final double green;
  private final double blue;
  
  public RGB(final double red, final double green, final double blue) {
    this.red = red;
    this.green = green;
    this.blue = blue;
  }
  
  public final double getRed() {
    return this.red;
  }
  
  public final double getGreen() {
    return this.green;
  }
  
  public final double getBlue() {
    return this.blue;
  }
}
