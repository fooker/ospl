package org.ospl.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
public @interface Processor {
  
  public abstract String[] name();
  
}
