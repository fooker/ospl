package org.ospl.exception;

import org.ospl.Node;

public class UnknownSourceException extends RuntimeException {
  private static final long serialVersionUID = 5662588852131802026L;
  
  public UnknownSourceException(final Node node, final String sourceName) {
    super(node.getName() + "." + sourceName);
  }
  
}
