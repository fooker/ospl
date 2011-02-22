package org.ospl;

import java.util.Map;

public interface ProcessorImplementation extends Processor {
  
  public abstract ProcessorDescription getDescription();
  
  public abstract Map<SinkDescription, Sink> getSinks();
  
  public abstract Map<SourceDescription, Source> getSources();
}
