package org.ospl.swing.graph;

import org.ospl.Sink;
import org.ospl.Source;

import com.mxgraph.model.mxCell;

public class ConnectionCell extends mxCell {
  private static final long serialVersionUID = -9140114913916108239L;
  
  private Sink sink;
  private Source source;
  
  public ConnectionCell(final Sink sink, final Source source) {
    super();
    
    this.sink = sink;
    this.source = source;
    
    this.setEdge(true);
  }
}
