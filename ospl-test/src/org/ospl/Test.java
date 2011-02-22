package org.ospl;

import java.io.FileReader;

import org.ospl.script.ScriptLoader;

public class Test {
  
  public static void main(final String[] args) throws Throwable {
    
    Context context = new Context();
    
    ScriptLoader scriptLoader = new ScriptLoader(context);
    scriptLoader.load(new FileReader("test.ospl"));
  }
}
