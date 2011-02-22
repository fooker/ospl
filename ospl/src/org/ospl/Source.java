package org.ospl;

import java.util.HashSet;
import java.util.Set;

public abstract class Source {
  
  private Node owner;
  
  private SourceDescription description;
  
  private Set<Sink> sinks;
  
  public Source(final Node owner, final SourceDescription description) {
    this.owner = owner;
    this.description = description;
    
    this.sinks = new HashSet<Sink>();
  }
  
  public abstract Object getValue();
  
  public final void connect(final Sink sink) {
    this.sinks.add(sink);
    sink.setSource(this);
  }
  
  public final void disconnect(final Sink sink) {
    this.sinks.remove(sink);
    sink.setSource(null);
  }
  
  public final Node getOwner() {
    return this.owner;
  }
  
  public final SourceDescription getDescription() {
    return this.description;
  }
  
  public final Set<Sink> getSinks() {
    return this.sinks;
  }
  
  public final Context getContext() {
    return this.owner.getContext();
  }
}
