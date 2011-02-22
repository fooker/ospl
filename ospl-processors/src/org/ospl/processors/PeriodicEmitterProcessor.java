package org.ospl.processors;

import org.ospl.AbstractProcessor;
import org.ospl.Node;

@org.ospl.annotation.Processor(name = { "Period" })
public class PeriodicEmitterProcessor extends AbstractProcessor implements Runnable {
  
  @org.ospl.annotation.Sink
  public long period = 0;
  
  @org.ospl.annotation.Source
  public Void emit;
  
  private final Thread thread;
  
  public PeriodicEmitterProcessor(final Node node) {
    super(node);
    
    this.thread = new Thread(this);
    this.thread.start();
  }
  
  @Override
  public final synchronized void process() {
    this.notifyAll();
  }
  
  @Override
  public final synchronized void run() {
    while (true) {
      try {
        this.wait(this.period);
        
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
      
      this.update(false);
    }
  }
}