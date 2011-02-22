package org.ospl;

public interface ContextListener {
  
  public abstract void nodeAdded(Node node);
  
  public abstract void nodeRemoved(Node node);
  
  public abstract void connected(Sink sink, Source source);
  
  public abstract void disconnected(Sink sink, Source source);
}
