package org.ospl.processors.audio;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Line;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;

import org.ospl.AbstractProcessor;
import org.ospl.Node;

@org.ospl.annotation.Processor(name = "AudioSource")
public class AudioSourceProcessor extends AbstractProcessor implements Runnable {
  
  @org.ospl.annotation.Sink
  public float sampleRate = 8000;
  
  @org.ospl.annotation.Sink
  public int sampleCount = 512;
  
  @org.ospl.annotation.Source
  public double[] data;
  
  @org.ospl.annotation.Source
  public double amplitude;
  
  private Thread thread;
  
  private TargetDataLine line;
  
  public AudioSourceProcessor(final Node node) {
    super(node);
  }
  
  @Override
  public void process() {
    this.close();
    this.open();
  }
  
  private final void close() {
    if (this.line != null) {
      this.line.stop();
      this.line.close();
      
      this.line = null;
      
      try {
        this.thread.join();
        
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
    }
  }
  
  private final void open() {
    try {
      if ((this.sampleRate != 0)) {
        this.line = (TargetDataLine) AudioSystem.getLine(new Line.Info(TargetDataLine.class));
        
        AudioFormat format = new AudioFormat(this.sampleRate, 8, 1, false, false);
        
        this.line.open(format);
        this.line.start();
        
        this.data = new double[this.sampleCount];
        
        this.thread = new Thread(this);
        this.thread.start();
      }
      
    } catch (LineUnavailableException e) {
      throw new RuntimeException(e);
    }
  }
  
  @Override
  public void run() {
    byte[] buffer = new byte[this.sampleCount];
    
    while (this.line != null) {
      this.line.read(buffer, 0, buffer.length);
      
      this.amplitude = 0.0;
      for (int i = 0; i < buffer.length; i++) {
        this.data[i] = ((buffer[i] & 0xFF) - 128) / 128.0;
        
        this.amplitude += Math.abs(this.data[i]);
      }
      this.amplitude /= buffer.length;
      
      this.update(false);
    }
    
    System.out.println("doh!");
  }
}
