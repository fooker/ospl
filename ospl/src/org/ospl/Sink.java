package org.ospl;

import java.lang.reflect.Field;

public class Sink {
  
  private Node owner;
  
  private SinkDescription description;
  
  private Source source;
  
  public Sink(final Node owner, final SinkDescription description) {
    this.owner = owner;
    this.description = description;
  }
  
  public final void setValue(final Object value) {
    try {
      Field field = this.getField();
      field.set(this.owner.getProcessor(), value);
      
    } catch (IllegalAccessException e) {
      throw new RuntimeException(e);
      
    } catch (NoSuchFieldException e) {
      throw new RuntimeException(e);
    }
  }
  
  private Field getField() throws NoSuchFieldException {
    Class<? extends ProcessorImplementation> processorClass = this.owner.getProcessor().getClass();
    
    Field field = processorClass.getField(this.description.getName());
    field.setAccessible(true);
    
    return field;
  }
  
  public final Class<?> getFieldClass() {
    try {
      Field field = this.getField();
      return field.getType();
      
    } catch (NoSuchFieldException e) {
      throw new RuntimeException(e);
    }
  }
  
  public final Node getOwner() {
    return this.owner;
  }
  
  public final SinkDescription getDescription() {
    return this.description;
  }
  
  public final Source getSource() {
    return this.source;
  }
  
  public void setSource(final Source source) {
    this.source = source;
    Logger.log("CONNECT: '" + source.getOwner().getName() + "'.'" + source.getDescription().getName() + "' -> '" + this.getOwner().getName() + "'.'" + this.getDescription().getName() + "'");
  }
}
