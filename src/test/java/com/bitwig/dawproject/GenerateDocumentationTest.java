package com.bitwig.dawproject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.stream.Collectors;

import com.bitwig.dawproject.timeline.*;
import com.bitwig.dawproject.device.*;
import com.github.therapi.runtimejavadoc.ClassJavadoc;
import com.github.therapi.runtimejavadoc.Comment;
import com.github.therapi.runtimejavadoc.CommentFormatter;
import com.github.therapi.runtimejavadoc.FieldJavadoc;
import com.github.therapi.runtimejavadoc.RuntimeJavadoc;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementRef;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlEnumValue;
import jakarta.xml.bind.annotation.XmlIDREF;
import jakarta.xml.bind.annotation.XmlRootElement;
import org.junit.Assert;
import org.junit.Test;

public class GenerateDocumentationTest
{
   private void out(final String text) throws IOException
   {
      if (mFileWriter != null)
      {
         mFileWriter.write(text);
         mFileWriter.write("\n");
      }
      else
         System.out.println(text);
   }
   private static String format(Comment c)
   {
      return formatter.format(c);
   }
   @Test
   public void createMarkdownClassSummary() throws IOException
   {
      final var file = new File("target/Reference.md");
      mFileWriter = new FileWriter(file);

      createMarkdownClassesSummary("Root", new Class[] {
         Project.class,
         MetaData.class,
      });

      createMarkdownClassesSummary("Other", new Class[] {
         Application.class,
         FileReference.class,
         Transport.class,
      });

      createMarkdownClassesSummary("Mixer", new Class[] {
         Track.class,
         Channel.class,
         Send.class,
      });

      createMarkdownClassesSummary("Timeline", new Class[] {
         Arrangement.class,
         Scene.class,
         Lanes.class,
         Clips.class,
         Clip.class,
         Audio.class,
         ClipSlot.class,
         Marker.class,
         Markers.class,
         Note.class,
         Notes.class,
         Timeline.class,
         TimeUnit.class,
         Video.class,
         Warp.class,
         Warps.class
      });

      createMarkdownClassesSummary("Parameters", new Class[] {
         Parameter.class,
         BoolParameter.class,
         EnumParameter.class,
         IntegerParameter.class,
         RealParameter.class,
         TimeSignatureParameter.class,
      });

      createMarkdownClassesSummary("Automation", new Class[] {
         AutomationTarget.class,
         Points.class,
         Point.class,
         RealPoint.class,
         BoolPoint.class,
         EnumPoint.class,
         IntegerPoint.class,
         TimeSignaturePoint.class,
      });

      createMarkdownClassesSummary("Device", new Class[] {
         Device.class,
         AuPlugin.class,
         ClapPlugin.class,
         Plugin.class,
         Vst2Plugin.class,
         Vst3Plugin.class,

         BuiltinDevice.class,
         //Compressor.class,
         Equalizer.class,
         EqBand.class,
         //Limiter.class,
         //NoiseGate.class,
      });

      mFileWriter.close();
      mFileWriter = null;
      Assert.assertTrue(file.exists());
   }

   public void createMarkdownClassesSummary(final String label, final Class[] classes) throws IOException
   {
      out("# " + label + " Elements\n\n");

      for (final var cls : classes)
         createMarkdownClassSummary(cls);

      out("");
      out("---");
      out("");
   }

   public void createMarkdownClassSummary(Class cls) throws IOException
   {
      final var rootElement = cls.getDeclaredAnnotation(XmlRootElement.class);
      var name = cls.getSimpleName();
      if (rootElement instanceof XmlRootElement re)
         name = re.name();

      out("\n## " + name);

      ClassJavadoc classDoc = RuntimeJavadoc.getJavadoc(cls);

      if (!classDoc.isEmpty())
      {
         out(format(classDoc.getComment()));
      }

      var superClass = cls.getSuperclass();

      if (superClass != null)
      {
         out("\n\n(*) Inherited from ");

         while (superClass != Object.class)
         {
            out(superClass.getSimpleName());
            superClass = superClass.getSuperclass();
            if (superClass != Object.class)
               out(", ");
         }
      }

      createMarkdownForAttributes(cls);
      createMarkdownForElements(cls);
   }

   private void createMarkdownForAttributes(final Class cls) throws IOException
   {
      final var sb = new StringBuilder();

      for (final var field : cls.getFields())
      {
         final var fieldJavadoc = RuntimeJavadoc.getJavadoc(field);
         createMarkdownForAttribute(sb, field, fieldJavadoc, field.getDeclaringClass() == cls);
      }

      if (!sb.isEmpty())
      {
         out("");
         out("| Attribute | Description | Type | Required |");
         out("| --------- | ----------- | ---- | -------- |");
         out(sb.toString());
      }
   }

   private void createMarkdownForAttribute(StringBuilder sb, final Field field, final FieldJavadoc javadoc,
      final boolean isDeclaredInThisClass) throws IOException
   {
      for (Annotation annotation : field.getAnnotations())
      {
         if (annotation instanceof XmlAttribute attribute)
         {
            final var comment = getComment(field, javadoc);
            var name = attribute.name();
            if (name.startsWith("#"))
               name = field.getName();

            if (!isDeclaredInThisClass)
               name += "*";

            sb.append("| " + name + " | " + comment + " | " + getType(field, javadoc) + " | " + (attribute.required() ? "yes" : "no") + " | ");
            sb.append("\n");
         }
      }
   }

   private void createMarkdownForElements(final Class cls) throws IOException
   {
      final var sb = new StringBuilder();

      for (final var field : cls.getFields())
      {
         final var fieldJavadoc = RuntimeJavadoc.getJavadoc(field);
         createMarkdownForElement(sb, field, fieldJavadoc, field.getDeclaringClass() == cls);
      }

      if (!sb.isEmpty())
      {
         out("");
         out("| Element | Description | Type | Required |");
         out("| ------- | ----------- | ---- | -------- |");
         out(sb.toString());
      }
   }

   private void createMarkdownForElement(StringBuilder sb, final Field field, final FieldJavadoc javadoc,
      final boolean isDeclaredInThisClass) throws IOException
   {
      final var comment = getComment(field, javadoc);
      var name = getFieldName(field);

      if (!isDeclaredInThisClass)
         name += "*";

      for (Annotation annotation : field.getAnnotations())
      {
         if (annotation instanceof XmlElementWrapper wrapper)
         {
            sb.append("| &lt;" + name + "/&gt; | " + comment + " | " + getType(field, javadoc) + " | " + (wrapper.required() ? "yes" : "no") + " | ");
            sb.append("\n");
            return;
         }
         else if (annotation instanceof XmlElement element)
         {
            sb.append("| &lt;" + name + "/&gt; | " + comment + " | " + getType(field, javadoc) + " | " + (element.required() ? "yes" : "no") + " | ");
            sb.append("\n");
         }
         else if (annotation instanceof XmlElementRef element)
         {
            sb.append("| " + name + " | " + comment + " | " + getType(field, javadoc) + " | " + (element.required() ? "yes" : "no") + " | ");
            sb.append("\n");
         }
      }
   }

   private static String getComment(final Field field, final FieldJavadoc javadoc)
   {
      var comment = javadoc != null ? format(javadoc.getComment()).replace("\n", "") : "";

      final Class<?> type = field.getType();

      if (type.isEnum())
      {
         comment += " ("
            + Arrays.stream(type.getFields()).map(f -> getFieldName(f)).collect(Collectors.joining("/"))
            + ")";
      }

      return comment;
   }

   private static String getFieldName(final Field field)
   {
      for (Annotation annotation : field.getAnnotations())
      {
         if (annotation instanceof XmlElementWrapper wrapper)
         {
            if (!wrapper.name().startsWith("#"))
               return wrapper.name();
         }
         else if (annotation instanceof XmlElement element)
         {
            if (!element.name().startsWith("#"))
               return element.name();
         }
         else if (annotation instanceof XmlElementRef element)
         {
            if (!element.name().startsWith("#"))
               return element.name();
         }
         else if (annotation instanceof XmlEnumValue element)
         {
            if (!element.value().startsWith("#"))
               return element.value();
         }
      }

      return field.getName();
   }

   private static String getType(final Field field, final FieldJavadoc javadoc)
   {
      final var xmlIDREF = field.getAnnotation(XmlIDREF.class);

      if (xmlIDREF != null)
      {
         return "ID";
      }

      final Class<?> type = field.getType();

      if (type.isEnum())
         return "Enum";

      return type.getSimpleName();
   }

   private static final CommentFormatter formatter = new CommentFormatter();
   private FileWriter mFileWriter;
}
