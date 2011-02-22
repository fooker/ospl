package org.ospl.swing.graph;

import org.ospl.Node;
import org.ospl.Sink;
import org.ospl.Source;

import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxGeometry;

public class NodeCell extends mxCell {
  private static final long serialVersionUID = 3642800803080014380L;
  
  public NodeCell(final Node node) {
    super(node, new mxGeometry(100, 100, 100, 100), null);
    
    this.setVertex(true);
    
    this.setConnectable(false);
    this.setCollapsed(false);
    
    for (Sink sink : node.getSinks()) {
      SinkCell cell = new SinkCell(sink);
      this.insert(cell);
    }
    
    for (Source source : node.getSources()) {
      SourceCell cell = new SourceCell(source);
      this.insert(cell);
    }
  }
}
