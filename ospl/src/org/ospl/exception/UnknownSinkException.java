package org.ospl.exception;

import org.ospl.Node;

public class UnknownSinkException extends RuntimeException {
  private static final long serialVersionUID = 5662588852131802026L;
  
  public UnknownSinkException(final Node node, final String sinkName) {
    super(node.getName() + "." + sinkName);
  }
  
}
