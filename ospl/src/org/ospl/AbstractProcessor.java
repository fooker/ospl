package org.ospl;

public abstract class AbstractProcessor implements Processor {
  
  private Node node;
  
  public AbstractProcessor(final Node node) {
    this.node = node;
  }
  
  public final void update(final boolean self) {
    this.getContext().update(this.node, self);
  }
  
  public final Node getNode() {
    return this.node;
  }
  
  public final Context getContext() {
    return this.node.getContext();
  }
}
