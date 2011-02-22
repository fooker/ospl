package org.ospl.exception;

public class UnknownValueConverterException extends RuntimeException {
  private static final long serialVersionUID = 5662588852131802026L;
  
  public UnknownValueConverterException(final Class<?> valueClass) {
    super(valueClass.getName());
  }
  
}
