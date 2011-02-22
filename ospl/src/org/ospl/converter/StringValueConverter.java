package org.ospl.converter;

import org.ospl.ValueConverter;

public class StringValueConverter extends ValueConverter {
  
  public StringValueConverter() {
    super(String.class);
  }
  
  @Override
  public Object parse(final String valueString) {
    return valueString;
  }
}
