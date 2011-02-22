package org.ospl.annotation;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.processing.Completion;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.SimpleElementVisitor6;
import javax.tools.Diagnostic.Kind;
import javax.tools.FileObject;
import javax.tools.JavaFileObject;
import javax.tools.StandardLocation;

import org.ospl.generator.GeneratedProcessorDescription;

public class ProcessorAnnotationProcessor implements javax.annotation.processing.Processor {
  
  private static final String PACKAGE_NAME = GeneratedProcessorDescription.class.getPackage().getName();
  private static final String BASE_NAME = GeneratedProcessorDescription.class.getSimpleName();
  
  static class ProcessorElementVisitor extends SimpleElementVisitor6<Void, Void> {
    
    static class Sink {
      String name;
      
      Sink(final org.ospl.annotation.Sink sinkAnnotation, final VariableElement element) {
        this.name = sinkAnnotation.name().equals(org.ospl.annotation.Sink.DEFAULT_NAME) ? element.getSimpleName().toString() : sinkAnnotation.name();
      }
    }
    
    static class Source {
      String name;
      
      Source(final org.ospl.annotation.Source sourceAnnotation, final VariableElement element) {
        this.name = sourceAnnotation.name().equals(org.ospl.annotation.Sink.DEFAULT_NAME) ? element.getSimpleName().toString() : sourceAnnotation.name();
      }
    }
    
    String[] names;
    
    Set<Sink> sinks;
    Set<Source> sources;
    
    @Override
    public final Void visitType(final TypeElement element, final Void tmp) {
      this.sinks = new HashSet<Sink>();
      this.sources = new HashSet<Source>();
      
      org.ospl.annotation.Processor processorAnnotation = element.getAnnotation(org.ospl.annotation.Processor.class);
      this.names = processorAnnotation.name();
      
      for (Element enclosingElement : element.getEnclosedElements()) {
        enclosingElement.accept(this, null);
      }
      
      return null;
    }
    
    @Override
    public final Void visitVariable(final VariableElement element, final Void tmp) {
      
      org.ospl.annotation.Sink sinkAnnotation = element.getAnnotation(org.ospl.annotation.Sink.class);
      org.ospl.annotation.Source sourceAnnotation = element.getAnnotation(org.ospl.annotation.Source.class);
      
      if (sinkAnnotation != null) {
        this.sinks.add(new Sink(sinkAnnotation, element));
      }
      
      if (sourceAnnotation != null) {
        this.sources.add(new Source(sourceAnnotation, element));
      }
      
      return null;
    }
  }
  
  private ProcessingEnvironment processingEnvironment;
  
  @Override
  public final void init(final ProcessingEnvironment processingEnvironment) {
    this.processingEnvironment = processingEnvironment;
  }
  
  @Override
  public final boolean process(final Set<? extends TypeElement> annotations, final RoundEnvironment roundEnvironment) {
    for (TypeElement annotation : annotations) {
      Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(annotation);
      
      Set<String> descriptorClassNames = new HashSet<String>();
      for (Element element : elements) {
        ProcessorElementVisitor visitor = new ProcessorElementVisitor();
        element.accept(visitor, null);
        
        String descriptorClassName = this.writeDescriptionSource(visitor, element);
        
        if (descriptorClassName != null) {
          descriptorClassNames.add(descriptorClassName);
        }
      }
      
      this.writeDescriptionService(annotation, descriptorClassNames);
    }
    return true;
  }
  
  private final String writeDescriptionSource(final ProcessorElementVisitor visitor, final Element element) {
    try {
      PackageElement processorPackage = this.processingEnvironment.getElementUtils().getPackageOf(element);
      
      String processorPackageName = processorPackage.getQualifiedName().toString();
      String processorDescriptionPackageName = ProcessorAnnotationProcessor.PACKAGE_NAME + "." + processorPackageName;
      
      String processorClassName = element.getSimpleName().toString();
      String processorDescriptionClassName = processorClassName + "Description";
      
      JavaFileObject file = this.processingEnvironment.getFiler().createSourceFile(processorDescriptionPackageName + "." + processorDescriptionClassName + "");
      PrintWriter writer = new PrintWriter(file.openWriter());
      
      writer.println("// This file is automatically generated. Do not modify. Any changes will be erased.");
      writer.println("package " + processorDescriptionPackageName + ";");
      writer.println("");
      writer.println("import java.util.Map;");
      writer.println("");
      writer.println("import org.ospl.Node;");
      writer.println("import org.ospl.ProcessorDescription;");
      writer.println("import org.ospl.ProcessorImplementation;");
      writer.println("import org.ospl.Sink;");
      writer.println("import org.ospl.SinkDescription;");
      writer.println("import org.ospl.Source;");
      writer.println("import org.ospl.SourceDescription;");
      writer.println("");
      writer.println("import " + ProcessorAnnotationProcessor.PACKAGE_NAME + "." + ProcessorAnnotationProcessor.BASE_NAME + ";");
      writer.println("");
      writer.println("import " + processorPackageName + "." + processorClassName + ";");
      writer.println("");
      writer.println("public class " + processorDescriptionClassName + " extends GeneratedProcessorDescription {");
      writer.println("  ");
      writer.println("  public class Implementation extends " + processorClassName + " implements ProcessorImplementation {");
      writer.println("    ");
      writer.println("    private final ProcessorDescription processorDescription;");
      writer.println("    ");
      writer.println("    private final Map<SinkDescription, Sink> sinks;");
      writer.println("    private final Map<SourceDescription, Source> sources;");
      writer.println("    ");
      writer.println("    public Implementation(final ProcessorDescription processorDescription, final Node node) {");
      writer.println("        super(node);");
      writer.println("        ");
      writer.println("        this.processorDescription = processorDescription;");
      writer.println("        ");
      writer.println("        this.sinks = " + processorDescriptionClassName + ".this.createSinks(node);");
      writer.println("        this.sources = " + processorDescriptionClassName + ".this.createSources(node);");
      writer.println("    }");
      writer.println("    ");
      writer.println("    @Override");
      writer.println("    public ProcessorDescription getDescription() {");
      writer.println("      return this.processorDescription;");
      writer.println("    }");
      writer.println("    ");
      writer.println("    @Override");
      writer.println("    public final Map<SinkDescription, Sink> getSinks() {");
      writer.println("      return this.sinks;");
      writer.println("    }");
      writer.println("    ");
      writer.println("    @Override");
      writer.println("    public final Map<SourceDescription, Source> getSources() {");
      writer.println("      return this.sources;");
      writer.println("    }");
      writer.println("  }");
      writer.println("  ");
      writer.println("  public " + processorDescriptionClassName + "() {");
      
      for (String name : visitor.names) {
        writer.println("    this.addName(\"" + name + "\");");
      }
      
      writer.println("    ");
      
      for (ProcessorElementVisitor.Sink sink : visitor.sinks) {
        writer.println("    this.addSinkDescription(\"" + sink.name + "\");");
      }
      
      writer.println("    ");
      
      for (ProcessorElementVisitor.Source source : visitor.sources) {
        writer.println("    this.addSourceDescription(\"" + source.name + "\");");
      }
      
      writer.println("  }");
      writer.println("  ");
      writer.println("  @Override");
      writer.println("  protected ProcessorImplementation createProcessor(final Node node) {");
      writer.println("    return new " + processorDescriptionClassName + ".Implementation(this, node);");
      writer.println("  }");
      writer.println("}");
      
      writer.close();
      
      return processorDescriptionPackageName + "." + processorDescriptionClassName;
      
    } catch (IOException e) {
      this.processingEnvironment.getMessager().printMessage(Kind.ERROR, e.getMessage(), element);
      
      return null;
    }
  }
  
  private final void writeDescriptionService(final TypeElement annotation, final Set<String> descriptorClassNames) {
    try {
      FileObject file = this.processingEnvironment.getFiler().createResource(StandardLocation.CLASS_OUTPUT, "", "META-INF/services/" + ProcessorAnnotationProcessor.PACKAGE_NAME + "." + ProcessorAnnotationProcessor.BASE_NAME);
      PrintWriter writer = new PrintWriter(file.openWriter());
      
      for (String descriptorClassName : descriptorClassNames) {
        writer.println(descriptorClassName);
      }
      
      writer.close();
      
    } catch (IOException e) {
      this.processingEnvironment.getMessager().printMessage(Kind.ERROR, e.getMessage(), annotation);
    }
  }
  
  @Override
  public final Iterable<? extends Completion> getCompletions(final Element element, final AnnotationMirror annotation, final ExecutableElement member, final String userText) {
    return Collections.emptySet();
  }
  
  @Override
  public final Set<String> getSupportedOptions() {
    return Collections.emptySet();
  }
  
  @Override
  public final Set<String> getSupportedAnnotationTypes() {
    return Collections.singleton(Processor.class.getName());
  }
  
  @Override
  public final SourceVersion getSupportedSourceVersion() {
    return SourceVersion.RELEASE_6;
  }
}
