package com.bitwig.dawproject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

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
      final var file = new File("target/classes.md");
      mFileWriter = new FileWriter(file);

      final Class[] rootClasses = {
         Project.class,
         MetaData.class,

         Application.class,
         Arrangement.class,
         BoolParameter.class,
         Channel.class,
         ContentType.class,
         DoubleAdapter.class,
         EnumParameter.class,
         ExpressionType.class,
         FileReference.class,
         IntegerParameter.class,
         Interpolation.class,
         Lane.class,
         MixerRole.class,
         Nameable.class,
         Parameter.class,
         RealParameter.class,
         Referenceable.class,
         Scene.class,
         Send.class,
         SendType.class,
         TimeSignatureParameter.class,
         Track.class,
         Transport.class,
         Unit.class
      };

      final Class[] timelineClasses = {
         Audio.class,
         AutomationTarget.class,
         BoolPoint.class,
         Clip.class,
         Clips.class,
         ClipSlot.class,
         EnumPoint.class,
         IntegerPoint.class,
         Lanes.class,
         Marker.class,
         Markers.class,
         Note.class,
         Notes.class,
         Point.class,
         Points.class,
         RealPoint.class,
         Timeline.class,
         TimeSignaturePoint.class,
         TimeUnit.class,
         Video.class,
         Warp.class,
         Warps.class
      };

      final Class[] deviceClasses = {
         Device.class,
         AuPlugin.class,
         ClapPlugin.class,
         Plugin.class,
         Vst2Plugin.class,
         Vst3Plugin.class,

         BuiltinDevice.class,
         Compressor.class,
         DeviceRole.class,
         EqBand.class,
         EqBandType.class,
         Equalizer.class,
         Limiter.class,
         NoiseGate.class,
      };

      out("# Elements\n\n");

      for (final var cls : rootClasses)
         createMarkdownClassSummary(cls);

      out("# Timeline Elements\n\n");

      for (final var cls : timelineClasses)
         createMarkdownClassSummary(cls);

      out("# Device Elements\n\n");

      for (final var cls : deviceClasses)
         createMarkdownClassSummary(cls);

      mFileWriter.close();
      mFileWriter = null;
      Assert.assertTrue(file.exists());
   }

   public void createMarkdownClassSummary(Class cls) throws IOException
   {
      final var rootElement = cls.getDeclaredAnnotation(XmlRootElement.class);
      if (rootElement instanceof XmlRootElement re)
      {
         out("\n## " + re.name());

         ClassJavadoc classDoc = RuntimeJavadoc.getJavadoc(cls);

         if (!classDoc.isEmpty())
         {
            out(format(classDoc.getComment()));
         }

         createMarkdownForAttributes(cls);
         createMarkdownForElements(cls);
      }
   }

   private void createMarkdownForAttributes(final Class cls) throws IOException
   {
      final var sb = new StringBuilder();

      for (final var field : cls.getFields())
      {
         final var fieldJavadoc = RuntimeJavadoc.getJavadoc(field);
         createMarkdownForAttribute(sb, field, fieldJavadoc);
      }

      if (!sb.isEmpty())
      {
         out("");
         out("| Attribute | Description | Required |");
         out("| --------- | ----------- | -------- |");
         out(sb.toString());
      }
   }

   private void createMarkdownForAttribute(StringBuilder sb, final Field field, final FieldJavadoc javadoc) throws IOException
   {
      for (Annotation annotation : field.getAnnotations())
      {
         if (annotation instanceof XmlAttribute attribute)
         {
            final var comment = javadoc != null ? format(javadoc.getComment()).replace("\n", "") : "";
            var name = attribute.name();
            if (name.startsWith("#"))
               name = field.getName();

            sb.append("| " + name + " | " + comment + " | " + (attribute.required() ? "yes" : "no") + " | ");
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
         createMarkdownForElement(sb, field, fieldJavadoc);
      }

      if (!sb.isEmpty())
      {
         out("");
         out("| Element | Description | Required |");
         out("| ------- | ----------- | -------- |");
         out(sb.toString());
      }
   }

   private void createMarkdownForElement(StringBuilder sb, final Field field, final FieldJavadoc javadoc) throws IOException
   {
      for (Annotation annotation : field.getAnnotations())
      {
         if (annotation instanceof XmlElementWrapper wrapper)
         {
            final var comment = javadoc != null ? format(javadoc.getComment()).replace("\n", "") : "";
            var name = wrapper.name();
            if (name.startsWith("#"))
               name = field.getName();

            sb.append("| &lt;" + name + "/&gt; | " + comment + " | " + (wrapper.required() ? "yes" : "no") + " | ");
            sb.append("\n");
            return;
         }
         else if (annotation instanceof XmlElement element)
         {
            final var comment = javadoc != null ? format(javadoc.getComment()).replace("\n", "") : "";
            var name = element.name();
            if (name.startsWith("#"))
               name = field.getName();

            sb.append("| &lt;" + name + "/&gt; | " + comment + " | " + (element.required() ? "yes" : "no") + " | ");
            sb.append("\n");
         }
         else if (annotation instanceof XmlElementRef element)
         {
            final var comment = javadoc != null ? format(javadoc.getComment()).replace("\n", "") : "";
            var name = element.name();
            if (name.startsWith("#"))
               name = field.getClass().getSimpleName();

            sb.append("| " + name + " | " + comment + " | " + (element.required() ? "yes" : "no") + " | ");
            sb.append("\n");
         }
      }
   }

   private static final CommentFormatter formatter = new CommentFormatter();
   private FileWriter mFileWriter;
}
