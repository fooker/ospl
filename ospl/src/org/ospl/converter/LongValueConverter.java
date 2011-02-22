package org.ospl.converter;

import org.ospl.ValueConverter;

public class LongValueConverter extends ValueConverter {
  
  public LongValueConverter() {
    super(Long.TYPE);
  }
  
  @Override
  public Object parse(final String valueString) {
    return Long.parseLong(valueString);
  }
}
