package org.ospl.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JSplitPane;

import org.ospl.Context;
import org.ospl.swing.graph.Graph;

import com.mxgraph.swing.mxGraphComponent;

public class SwingContext {
  
  private final static Map<Context, SwingContext> swingContexts = new HashMap<Context, SwingContext>();
  
  public final static SwingContext getContext(final Context context) {
    SwingContext swingContext = SwingContext.swingContexts.get(context);
    if (swingContext == null) {
      SwingContext.swingContexts.put(context, swingContext = new SwingContext(context));
    }
    
    return swingContext;
  }
  
  private final Context context;
  
  private final JFrame frame;
  private final JDesktopPane desktopPane;
  private final JSplitPane splitPane;
  
  private final Graph graph;
  private final mxGraphComponent graphComponent;
  
  private SwingContext(final Context context) {
    this.context = context;
    
    this.graph = new Graph(this.context);
    
    this.frame = new JFrame("ospl");
    this.frame.setLayout(new BorderLayout());
    
    this.graphComponent = new mxGraphComponent(this.graph);
    this.graphComponent.setPageVisible(true);
    this.graphComponent.setGridVisible(true);
    this.graphComponent.setToolTips(true);
    this.graphComponent.setBackground(Color.WHITE);
    
    this.desktopPane = new JDesktopPane();
    this.splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, this.graphComponent, this.desktopPane);
    
    this.frame.add(this.splitPane, BorderLayout.CENTER);
    this.frame.setVisible(true);
  }
  
  public JDesktopPane getDesktop() {
    return this.desktopPane;
  }
}
