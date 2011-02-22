package org.ospl.annotation;

public @interface Source {
  public static final String DEFAULT_NAME = "";
  
  String name() default Source.DEFAULT_NAME;
}
