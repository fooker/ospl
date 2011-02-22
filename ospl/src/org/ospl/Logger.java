package org.ospl;

public class Logger {
  
  private static int indent = 0;
  
  public final static void log(final String message) {
    // StringBuilder s = new StringBuilder();
    // for (int i = 0; i < Logger.indent; i++) {
    // s.append("  ");
    // }
    //
    // s.append(message);
    //
    // System.out.println(s);
  }
  
  public final static void indent() {
    Logger.indent++;
  }
  
  public final static void outdent() {
    Logger.indent--;
  }
  
}
