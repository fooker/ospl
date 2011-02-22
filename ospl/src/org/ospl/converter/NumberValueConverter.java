package org.ospl.converter;

import org.ospl.ValueConverter;

public class NumberValueConverter extends ValueConverter {
  
  public NumberValueConverter() {
    super(Number.class);
  }
  
  @Override
  public Object parse(final String valueString) {
    return Double.parseDouble(valueString);
  }
}
