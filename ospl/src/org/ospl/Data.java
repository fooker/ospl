package org.ospl;

public class Data<TYPE> {
  
  public static class Converter<TYPE> {
    
    public TYPE to(final String value) {
      return null;
    }
    
    public String from(final TYPE value) {
      return value.toString();
    }
  }
  
  private TYPE value;
  
  public Data(final TYPE value) {
    this.value = value;
  }
  
  public Data(final String value) {
    this.value = this.getConverter().to(value);
  }
  
  public Converter<TYPE> getConverter() {
    return new Converter<TYPE>();
  }
  
  public final TYPE getValue() {
    return this.value;
  }
  
  public final String toString() {
    return this.getConverter().from(this.value);
  }
}
