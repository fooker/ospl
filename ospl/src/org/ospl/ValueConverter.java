package org.ospl;

public abstract class ValueConverter {
  
  private Class<?> valueClass;
  
  public ValueConverter(final Class<?> valueClass) {
    this.valueClass = valueClass;
  }
  
  public abstract Object parse(String valueString);
  
  public Class<?> getValueClass() {
    return this.valueClass;
  }
}
