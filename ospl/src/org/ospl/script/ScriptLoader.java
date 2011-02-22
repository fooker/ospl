package org.ospl.script;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.ospl.Context;
import org.ospl.Node;
import org.ospl.exception.IllegalSyntaxException;

public class ScriptLoader {
  
  private final static Pattern PATTERN_DECLARE = Pattern.compile("^\\s*(\\S+)\\s*(\\S+)\\s*$");
  private final static Pattern PATTERN_CONNECT = Pattern.compile("^\\s*(\\S+)\\s*\\.\\s*(\\S+)\\s*->\\s*(\\S+)\\s*\\.\\s*(\\S+)\\s*$");
  private final static Pattern PATTERN_ASSIGN = Pattern.compile("^\\s*(\\S+)\\s*\\.\\s*(\\S+)\\s*=\\s*\"([^\"]*)\"\\s*$");
  
  private final Context context;
  
  public ScriptLoader(final Context context) {
    this.context = context;
  }
  
  public void load(final Reader in) throws IOException {
    BufferedReader reader = new BufferedReader(in);
    
    String line;
    while ((line = reader.readLine()) != null) {
      
      int commentIndex = line.indexOf('#');
      if (commentIndex != -1) {
        line = line.substring(0, commentIndex);
      }
      
      line = line.trim();
      
      if (!line.isEmpty()) {
        Matcher declareMatcher = ScriptLoader.PATTERN_DECLARE.matcher(line);
        Matcher connectMatcher = ScriptLoader.PATTERN_CONNECT.matcher(line);
        Matcher assignMatcher = ScriptLoader.PATTERN_ASSIGN.matcher(line);
        
        if (declareMatcher.matches()) {
          String descriptionName = declareMatcher.group(1);
          String nodeName = declareMatcher.group(2);
          
          this.context.createNode(descriptionName, nodeName);
          
        } else if (connectMatcher.matches()) {
          String sourceNodeName = connectMatcher.group(1);
          String sourceName = connectMatcher.group(2);
          
          String sinkNodeName = connectMatcher.group(3);
          String sinkName = connectMatcher.group(4);
          
          Node sourceNode = this.context.getNode(sourceNodeName);
          Node sinkNode = this.context.getNode(sinkNodeName);
          
          this.context.connect(sourceNode, sourceName, sinkNode, sinkName);
          
        } else if (assignMatcher.matches()) {
          String sinkNodeName = assignMatcher.group(1);
          String sinkName = assignMatcher.group(2);
          
          String valueString = assignMatcher.group(3);
          
          Node sinkNode = this.context.getNode(sinkNodeName);
          
          this.context.createValueSource(sinkNode, sinkName, valueString);
          
        } else {
          throw new IllegalSyntaxException(line);
        }
      }
    }
    
    System.err.println("Sript loaded...");
    this.context.revalidate();
  }
  
  public final Context getContext() {
    return this.context;
  }
}
