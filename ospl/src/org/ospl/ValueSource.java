package org.ospl;

public class ValueSource extends Source {
  
  private static final SourceDescription ANONYMOUS_DESCRIPTION = new SourceDescription("value");
  
  private Object value;
  
  public ValueSource(final Node owner, final Object value) {
    super(owner, ValueSource.ANONYMOUS_DESCRIPTION);
    
    this.value = value;
  }
  
  public void setValue(final Object value) {
    this.value = value;
    
    this.getContext().update(this.getOwner(), true);
  }
  
  @Override
  public Object getValue() {
    return this.value;
  }
}
