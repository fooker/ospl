package org.ospl.processors.swing;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import org.ospl.Node;
import org.ospl.swing.AbstractSwingProcessor;

@org.ospl.annotation.Processor(name = { "Bang" })
public class BangEmitterProcessor extends AbstractSwingProcessor implements ActionListener {
  
  @org.ospl.annotation.Source
  public Void emit;
  
  private JButton button;
  
  public BangEmitterProcessor(final Node node) {
    super(node);
  }
  
  @Override
  protected void initSwing() {
    this.getContentPane().setLayout(new BorderLayout());
    
    this.button = new JButton("Bang");
    this.button.addActionListener(this);
    
    this.getContentPane().add(this.button, BorderLayout.CENTER);
  }
  
  @Override
  public final void process() {
    // Empty
  }
  
  @Override
  public void actionPerformed(final ActionEvent e) {
    this.update(false);
  }
}