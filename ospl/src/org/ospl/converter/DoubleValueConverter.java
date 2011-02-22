package org.ospl.converter;

import org.ospl.ValueConverter;

public class DoubleValueConverter extends ValueConverter {
  
  public DoubleValueConverter() {
    super(Double.TYPE);
  }
  
  @Override
  public Object parse(final String valueString) {
    return Double.parseDouble(valueString);
  }
}
