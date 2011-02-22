package org.ospl.swing;

import java.awt.Container;

import javax.swing.JInternalFrame;

import org.ospl.AbstractProcessor;
import org.ospl.Node;

public abstract class AbstractSwingProcessor extends AbstractProcessor {
  
  private JInternalFrame frame;
  
  public AbstractSwingProcessor(final Node node) {
    super(node);
    
    this.frame = new JInternalFrame(AbstractSwingProcessor.this.getNode().getName());
    this.frame.setClosable(false);
    this.frame.setIconifiable(true);
    this.frame.setResizable(true);
    this.frame.setMaximizable(true);
    
    this.frame.setFrameIcon(null);
    
    AbstractSwingProcessor.this.initSwing();
    
    AbstractSwingProcessor.this.getSwingContext().getDesktop().add(AbstractSwingProcessor.this.frame);
    
    this.frame.pack();
    this.frame.setVisible(true);
  }
  
  protected abstract void initSwing();
  
  public final Container getContentPane() {
    return this.frame.getContentPane();
  }
  
  public final SwingContext getSwingContext() {
    return SwingContext.getContext(this.getContext());
  }
}
