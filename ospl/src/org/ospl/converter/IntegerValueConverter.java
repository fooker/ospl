package org.ospl.converter;

import org.ospl.ValueConverter;

public class IntegerValueConverter extends ValueConverter {
  
  public IntegerValueConverter() {
    super(Integer.TYPE);
  }
  
  @Override
  public Object parse(final String valueString) {
    return Integer.parseInt(valueString);
  }
}
