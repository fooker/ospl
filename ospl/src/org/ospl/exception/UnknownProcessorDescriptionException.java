package org.ospl.exception;

public class UnknownProcessorDescriptionException extends RuntimeException {
  private static final long serialVersionUID = 5662588852131802026L;
  
  public UnknownProcessorDescriptionException(final String processorDescriptionName) {
    super(processorDescriptionName);
  }
  
}
