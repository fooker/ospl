package org.ospl.processors.swing;

import java.awt.BorderLayout;

import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.ospl.Node;
import org.ospl.swing.AbstractSwingProcessor;

@org.ospl.annotation.Processor(name = { "Slider" })
public class SliderProcessor extends AbstractSwingProcessor implements ChangeListener {
  
  @org.ospl.annotation.Sink
  public int minimum = 0;
  
  @org.ospl.annotation.Sink
  public int maximum = 100;
  
  @org.ospl.annotation.Sink
  public int majorTickSpacing;
  
  @org.ospl.annotation.Sink
  public int minorTickSpacing;
  
  @org.ospl.annotation.Source
  public int value = 0;
  
  private JSlider slider;
  
  private JTextField textField;
  
  private boolean updating = false;
  
  public SliderProcessor(final Node node) {
    super(node);
  }
  
  @Override
  protected final void initSwing() {
    this.getContentPane().setLayout(new BorderLayout());
    
    this.slider = new JSlider(SwingConstants.VERTICAL);
    this.slider.setPaintLabels(true);
    this.slider.setPaintTicks(true);
    this.slider.setPaintTrack(true);
    
    this.slider.setValue(this.value);
    
    this.slider.addChangeListener(this);
    
    this.textField = new JTextField(Integer.toString(this.slider.getValue()));
    this.textField.setEditable(false);
    
    this.getContentPane().add(this.slider, BorderLayout.CENTER);
    this.getContentPane().add(this.textField, BorderLayout.SOUTH);
  }
  
  @Override
  public final void process() {
    this.updating = true;
    
    this.slider.setMinimum(this.minimum);
    this.slider.setMaximum(this.maximum);
    
    this.slider.setMajorTickSpacing(this.majorTickSpacing);
    this.slider.setMinorTickSpacing(this.minorTickSpacing);
    
    this.updating = false;
  }
  
  @Override
  public final void stateChanged(final ChangeEvent e) {
    if (this.updating) {
      return;
    }
    
    this.value = this.slider.getValue();
    
    this.textField.setText(Integer.toString(this.value));
    
    this.update(false);
  }
}