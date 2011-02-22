package org.ospl.annotation;

public @interface Sink {
  public static final String DEFAULT_NAME = "";
  
  String name() default Source.DEFAULT_NAME;
}
