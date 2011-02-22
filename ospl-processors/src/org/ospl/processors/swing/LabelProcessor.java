package org.ospl.processors.swing;

import java.awt.BorderLayout;

import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import org.ospl.Node;
import org.ospl.swing.AbstractSwingProcessor;

@org.ospl.annotation.Processor(name = { "Label" })
public class LabelProcessor extends AbstractSwingProcessor implements Runnable {
  
  @org.ospl.annotation.Sink
  public Object value;
  
  private JTextField textField;
  
  public LabelProcessor(final Node node) {
    super(node);
  }
  
  @Override
  protected final void initSwing() {
    this.getContentPane().setLayout(new BorderLayout());
    
    this.textField = new JTextField("null");
    this.textField.setEditable(false);
    
    this.getContentPane().add(this.textField, BorderLayout.SOUTH);
  }
  
  @Override
  public final void process() {
    SwingUtilities.invokeLater(this);
  }
  
  @Override
  public void run() {
    this.textField.setText(this.value != null ? ("'" + this.value.toString() + "'") : "null");
  }
}