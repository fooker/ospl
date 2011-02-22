package org.ospl.processors.mood;

import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.UnsupportedCommOperationException;

import java.io.IOException;
import java.io.OutputStream;

import org.ospl.AbstractProcessor;
import org.ospl.Node;
import org.ospl.processors.color.RGB;

@org.ospl.annotation.Processor(name = { "Mood" })
public class MoodProcessor extends AbstractProcessor {
  
  @org.ospl.annotation.Sink
  public byte id;
  
  @org.ospl.annotation.Sink
  public RGB rgb;
  
  @org.ospl.annotation.Sink
  public String port;
  
  private SerialPort serialPort;
  
  private CommPortIdentifier serialPortIdentifier;
  
  private OutputStream outputStream;
  
  public MoodProcessor(final Node node) {
    super(node);
  }
  
  @Override
  public void process() {
    if (this.port == null) {
      this.close();
      return;
    }
    
    if (this.serialPortIdentifier == null) {
      this.open();
      
    } else if (!this.serialPortIdentifier.getName().equals(this.port)) {
      this.close();
      this.open();
    }
    
    byte[] pkg = new byte[4];
    pkg[0] = this.id;
    pkg[1] = (byte) (this.rgb.getRed() * 256.0);
    pkg[2] = (byte) (this.rgb.getGreen() * 256.0);
    pkg[3] = (byte) (this.rgb.getBlue() * 256.0);
    
    try {
      this.outputStream.write(pkg);
      
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
  
  private final void open() {
    try {
      this.serialPortIdentifier = CommPortIdentifier.getPortIdentifier(this.port);
      
      this.serialPort = (SerialPort) this.serialPortIdentifier.open("ospl", 1000);
      this.serialPort.setSerialPortParams(9600, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
      
      this.outputStream = this.serialPort.getOutputStream();
      
    } catch (NoSuchPortException e) {
      throw new RuntimeException(e);
    } catch (PortInUseException e) {
      throw new RuntimeException(e);
    } catch (UnsupportedCommOperationException e) {
      throw new RuntimeException(e);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
  
  private final void close() {
    if (this.serialPort != null) {
      this.outputStream = null;
      
      this.serialPort.close();
      this.serialPort = null;
      
      this.serialPortIdentifier = null;
    }
  }
}
