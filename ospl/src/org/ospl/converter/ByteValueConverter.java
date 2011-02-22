package org.ospl.converter;

import org.ospl.ValueConverter;

public class ByteValueConverter extends ValueConverter {
  
  public ByteValueConverter() {
    super(Byte.TYPE);
  }
  
  @Override
  public Object parse(final String valueString) {
    return Byte.parseByte(valueString);
  }
}
