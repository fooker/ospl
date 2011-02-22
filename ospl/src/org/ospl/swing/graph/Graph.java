package org.ospl.swing.graph;

import java.util.HashMap;
import java.util.Map;

import org.ospl.Context;
import org.ospl.ContextListener;
import org.ospl.Node;
import org.ospl.Sink;
import org.ospl.Source;

import com.mxgraph.view.mxGraph;

public class Graph extends mxGraph implements ContextListener {
  
  private final Context context;
  
  private final Map<Node, NodeCell> cells;
  
  public Graph(final Context contex) {
    this.context = contex;
    this.context.addListener(this);
    
    this.setHtmlLabels(true);
    
    this.cells = new HashMap<Node, NodeCell>();
  }
  
  @Override
  public String getToolTipForCell(final Object cell) {
    StringBuilder s = new StringBuilder();
    s.append("<html>");
    s.append(cell);
    s.append("</html>");
    
    return s.toString();
  }
  
  @Override
  public String convertValueToString(final Object cell) {
    StringBuilder s = new StringBuilder();
    s.append("<html>");
    
    if (cell instanceof NodeCell) {
      NodeCell nodeCell = (NodeCell) cell;
      Node node = (Node) nodeCell.getValue();
      
      s.append("<i>");
      s.append(node.getProcessor().getDescription().getNames().iterator().next());
      s.append("</i>");
      s.append("<br />");
      s.append(node.getName());
    }
    
    s.append("</html>");
    return s.toString();
  }
  
  @Override
  public boolean isCellFoldable(final Object cell, final boolean collapse) {
    return false;
  }
  
  @Override
  public void nodeAdded(final Node node) {
    this.getModel().beginUpdate();
    
    NodeCell cell = new NodeCell(node);
    this.addCell(cell, this.getDefaultParent());
    
    this.cells.put(node, cell);
    
    this.getModel().endUpdate();
  }
  
  @Override
  public void nodeRemoved(final Node node) {
  }
  
  @Override
  public void connected(final Sink sink, final Source source) {
    NodeCell sinkNodeCell = this.cells.get(sink.getOwner());
    NodeCell sourceNodeCell = this.cells.get(source.getOwner());
    
    this.getModel().beginUpdate();
    
    this.insertEdge(this.getDefaultParent(), null, source.getDescription().getName() + " -> " + sink.getDescription().getName(), sourceNodeCell, sinkNodeCell);
    
    this.getModel().endUpdate();
  }
  
  @Override
  public void disconnected(final Sink sink, final Source source) {
  }
}
