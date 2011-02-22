package org.ospl.exception;

public class IllegalSyntaxException extends RuntimeException {
  private static final long serialVersionUID = 5662588852131802026L;
  
  public IllegalSyntaxException(final String line) {
    super(line);
  }
  
}
