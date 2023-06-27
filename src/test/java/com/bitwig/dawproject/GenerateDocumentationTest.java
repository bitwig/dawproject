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
         MetaData.class,
         MixerRole.class,
         Nameable.class,
         Parameter.class,
         Project.class,
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
         AuPlugin.class,
         BuiltinDevice.class,
         ClapPlugin.class,
         Compressor.class,
         Device.class,
         DeviceRole.class,
         EqBand.class,
         EqBandType.class,
         Equalizer.class,
         Limiter.class,
         NoiseGate.class,
         Plugin.class,
         Vst2Plugin.class,
         Vst3Plugin.class
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

            out("");
            out("| Attribute | Description | Required |");
            out("| --------- | ----------- | -------- |");

            for (final var field : cls.getDeclaredFields())
            {
               final var fieldJavadoc = RuntimeJavadoc.getJavadoc(field);
               createMarkdownForAttribute(field, fieldJavadoc);
            }
         }
      }
   }

   private void createMarkdownForAttribute(final Field field, final FieldJavadoc javadoc) throws IOException
   {
      for (Annotation annotation : field.getAnnotations())
      {
         if (annotation instanceof XmlAttribute attribute)
         {
            final var comment = javadoc != null ? format(javadoc.getComment()).replace("\n", "") : "";
            var name = attribute.name();
            if (name.startsWith("#"))
               name = field.getName();

            out("| " + name + " | " + comment + " | " + (attribute.required() ? "yes" : "no") + " | ");
         }
      }
   }

   private static final CommentFormatter formatter = new CommentFormatter();
   private FileWriter mFileWriter;
}
