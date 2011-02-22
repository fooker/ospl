package org.ospl.converter;

import org.ospl.ValueConverter;

public class FloatValueConverter extends ValueConverter {
  
  public FloatValueConverter() {
    super(Float.TYPE);
  }
  
  @Override
  public Object parse(final String valueString) {
    return Float.parseFloat(valueString);
  }
}
