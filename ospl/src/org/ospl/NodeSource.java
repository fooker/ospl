package org.ospl;

import java.lang.reflect.Field;

public class NodeSource extends Source {
  
  public NodeSource(final Node owner, final SourceDescription description) {
    super(owner, description);
  }
  
  public final Object getValue() {
    try {
      Class<? extends ProcessorImplementation> processorClass = this.getOwner().getProcessor().getClass();
      
      String fieldName = this.getDescription().getName();
      
      Field field = processorClass.getField(fieldName);
      
      field.setAccessible(true);
      return field.get(this.getOwner().getProcessor());
      
    } catch (IllegalAccessException e) {
      throw new RuntimeException(e);
      
    } catch (NoSuchFieldException e) {
      throw new RuntimeException(e);
    }
  }
}
