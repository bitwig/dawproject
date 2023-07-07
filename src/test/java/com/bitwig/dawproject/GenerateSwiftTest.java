package com.bitwig.dawproject;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.bitwig.dawproject.device.AuPlugin;
import com.bitwig.dawproject.device.ClapPlugin;
import com.bitwig.dawproject.device.Device;
import com.bitwig.dawproject.device.DeviceRole;
import com.bitwig.dawproject.device.EqBandType;
import com.bitwig.dawproject.device.Plugin;
import com.bitwig.dawproject.device.Vst2Plugin;
import com.bitwig.dawproject.device.Vst3Plugin;
import com.bitwig.dawproject.timeline.Audio;
import com.bitwig.dawproject.timeline.AutomationTarget;
import com.bitwig.dawproject.timeline.BoolPoint;
import com.bitwig.dawproject.timeline.Clip;
import com.bitwig.dawproject.timeline.ClipSlot;
import com.bitwig.dawproject.timeline.Clips;
import com.bitwig.dawproject.timeline.EnumPoint;
import com.bitwig.dawproject.timeline.IntegerPoint;
import com.bitwig.dawproject.timeline.Lanes;
import com.bitwig.dawproject.timeline.Marker;
import com.bitwig.dawproject.timeline.Markers;
import com.bitwig.dawproject.timeline.MediaFile;
import com.bitwig.dawproject.timeline.Note;
import com.bitwig.dawproject.timeline.Notes;
import com.bitwig.dawproject.timeline.Point;
import com.bitwig.dawproject.timeline.Points;
import com.bitwig.dawproject.timeline.RealPoint;
import com.bitwig.dawproject.timeline.TimeSignaturePoint;
import com.bitwig.dawproject.timeline.TimeUnit;
import com.bitwig.dawproject.timeline.Timeline;
import com.bitwig.dawproject.timeline.Video;
import com.bitwig.dawproject.timeline.Warp;
import com.bitwig.dawproject.timeline.Warps;
import com.github.therapi.runtimejavadoc.BaseJavadoc;
import com.github.therapi.runtimejavadoc.CommentFormatter;
import com.github.therapi.runtimejavadoc.FieldJavadoc;
import com.github.therapi.runtimejavadoc.RuntimeJavadoc;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementRef;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlEnumValue;
import jakarta.xml.bind.annotation.XmlIDREF;
import org.junit.Assert;
import org.junit.Test;
import org.reflections.Reflections;
import static j2html.TagCreator.a;
import static j2html.TagCreator.b;
import static j2html.TagCreator.body;
import static j2html.TagCreator.br;
import static j2html.TagCreator.div;
import static j2html.TagCreator.h1;
import static j2html.TagCreator.h2;
import static j2html.TagCreator.h3;
import static j2html.TagCreator.head;
import static j2html.TagCreator.html;
import static j2html.TagCreator.link;
import static j2html.TagCreator.p;
import static j2html.TagCreator.span;
import static j2html.TagCreator.table;
import static j2html.TagCreator.td;
import static j2html.TagCreator.th;
import static j2html.TagCreator.title;
import static j2html.TagCreator.tr;

public class GenerateSwiftTest
{
   final static Class[] classes = new Class[] {

      Nameable.class,
      Referenceable.class,
      MediaFile.class,
      Lane.class,
      Timeline.class,

      Project.class,
      MetaData.class,
      Application.class,
      FileReference.class,
      Transport.class,
      Track.class,
      Channel.class,
      Send.class,
      Arrangement.class,
      Scene.class,
      ClipSlot.class,
      Lanes.class,
      Clips.class,
      Clip.class,
      Notes.class,
      Note.class,
      Audio.class,
      Video.class,
      Warps.class,
      Warp.class,
      Markers.class,
      Marker.class,
      Parameter.class,
      BoolParameter.class,
      EnumParameter.class,
      IntegerParameter.class,
      RealParameter.class,
      TimeSignatureParameter.class,
      Points.class,
      AutomationTarget.class,
      Point.class,
      RealPoint.class,
      BoolPoint.class,
      EnumPoint.class,
      IntegerPoint.class,
      TimeSignaturePoint.class,
      Device.class,
      AuPlugin.class,
      ClapPlugin.class,
      Plugin.class,
      Vst2Plugin.class,
      Vst3Plugin.class,

      //BuiltinDevice.class,
      //Compressor.class,
      //Equalizer.class,
      //EqBand.class,
      //Limiter.class,
      //NoiseGate.class,
   };

   @Test
   public void generateSwiftFile() throws IOException
   {
      final var swiftFile = new File("dawproject.swift");

      final StringBuilder sb = new StringBuilder();
      generateStructs(sb);
      Files.write(swiftFile.toPath(), Collections.singleton(sb.toString()), StandardCharsets.UTF_8);

      Assert.assertTrue(swiftFile.exists());
   }

   public void generateStructs(StringBuilder sb) throws IOException
   {
      generateEnum(sb, TimeUnit.values());
      generateEnum(sb, Interpolation.values());
      generateEnum(sb, Unit.values());
      generateEnum(sb, SendType.values());
      generateEnum(sb, MixerRole.values());
      generateEnum(sb, DeviceRole.values());
      generateEnum(sb, ContentType.values());
      generateEnum(sb, ExpressionType.values());
      generateEnum(sb, EqBandType.values());

      for (final var cls : classes)
      {
         if (Modifier.isAbstract(cls.getModifiers()))
         {
            generateProtocol(sb, cls);
         }
      }

      for (final var cls : classes)
      {
         if (!Modifier.isAbstract(cls.getModifiers()))
         {
            generateClass(sb, cls);
         }
      }
   }

   private void generateEnum(final StringBuilder sb, final Enum[] values)
   {
      final var name = values[0].getClass().getSimpleName();
      sb.append("enum " + name + ": UInt32 {\n");
      for (Enum value : values)
      {
         sb.append("    case ");
         sb.append(value.name());
         sb.append(" = ");
         sb.append(value.ordinal());
         sb.append("\n");
      }

      sb.append("}\n\n");
   }

   private void appendJavaDoc(final StringBuilder sb, final BaseJavadoc javadoc, int indentation)
   {
      if (!javadoc.isEmpty())
      {
         final var indent = " ".repeat(indentation);
         sb.append(indent);
         sb.append("/* ");
         final var comment = javadoc.getComment().toString();
         sb.append(comment.replace("\n", "\n" + indent));
         sb.append(" */\n");
      }
   }

   private void generateProtocol(final StringBuilder sb, final Class cls)
   {
      appendJavaDoc(sb, RuntimeJavadoc.getJavadoc(cls), 0);

      final var name = cls.getSimpleName();
      sb.append("\nprotocol " + name + " {\n");
      for (final var field : cls.getDeclaredFields())
      {
         if (shouldIncludeField(field))
         {
            final var fieldJavadoc = RuntimeJavadoc.getJavadoc(field);
            generateStructField(sb, field, fieldJavadoc, true);
         }
      }
      sb.append("}\n\n");
   }

   private void generateClass(final StringBuilder sb, final Class cls)
   {
      appendJavaDoc(sb, RuntimeJavadoc.getJavadoc(cls), 0);
      final var name = cls.getSimpleName();

      sb.append("\nclass " + name);

      boolean hadSuper = false;
      var superClass = cls.getSuperclass();
      while (superClass != Object.class)
      {
         if (!hadSuper) sb.append(": ");
         else sb.append(", ");

         sb.append(superClass.getSimpleName());
         hadSuper = true;
         superClass = superClass.getSuperclass();
      }

      sb.append(" {\n");

      for (final var field : cls.getFields())
      {
         if (shouldIncludeField(field))
         {
            final var fieldJavadoc = RuntimeJavadoc.getJavadoc(field);
            generateStructField(sb, field, fieldJavadoc, false);
         }
      }

      /*sb.append("    enum CodingKeys : String, CodingKey {\n");
      for (final var field : cls.getFields())
      {
         if (shouldIncludeField(field))
         {
            sb.append("        case ");
            sb.append(getFieldName(field));
            sb.append(" = \"");
            sb.append(getFieldXMLName(field));
            sb.append("\"\n");
         }
      }
      sb.append("    }\n");*/
      sb.append("}\n\n");
   }

   private boolean shouldIncludeField(final Field field)
   {
      if (Modifier.isStatic(field.getModifiers()))
         return false;
      return true;
   }

   private void generateStructField(final StringBuilder sb, final Field field, final FieldJavadoc fieldJavadoc,
      final boolean isProtocol)
   {
      final String commentText = fieldJavadoc != null && !fieldJavadoc.isEmpty() ? fieldJavadoc.getComment().toString() : "";

      final var isCommentLong = commentText.length() > 50 || commentText.contains("\n");

      if (isCommentLong)
         appendJavaDoc(sb, fieldJavadoc, 4);

      var typeString = getTypeString(field) + "?"; //(!isRequired(field) ? "?" : "");
      sb.append("    var " + getFieldName(field) + ": " + typeString);
      if (isProtocol)
         sb.append(" { get }");
      if (!isCommentLong && !commentText.isEmpty())
      {
         sb.append(" // ");
         sb.append(commentText);
      }
      sb.append("\n\n");
   }


   private boolean isRequired(final Field field)
   {
      for (final var annotation : field.getAnnotations())
      {
         if (annotation instanceof XmlElementWrapper e && e.required())
            return true;
         else if (annotation instanceof XmlElement e && e.required())
            return true;
         else if (annotation instanceof XmlElementRef e && e.required())
            return true;
      }
      return false;
   }

   private String getTypeString(final Field field)
   {
      final Class<?> type1 = field.getType();
      if (type1 == List.class)
         return "[" + getListGenericType(field).getSimpleName() + "]";

      final Class<?> type = field.getType();
      if (type == String.class)
         return "String";
      if (type == Integer.class)
         return "Int";
      if (type == Double.class)
         return "Double";
      if (type == Boolean.class)
         return "Bool";
      if (type == double.class)
         return "Float64";
      if (type == int.class)
         return "Int";
      if (type.isArray())
         return "[" + type.getSimpleName().replace("[", "");
      return type.getSimpleName();
   }

   private String getFieldXMLName(final Field field)
   {
      for (final var annotation : field.getAnnotations())
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

   private static String getFieldName(final Field field)
   {
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

      if (type.isArray() && type.getComponentType().isEnum())
         return "Enum,...";

      return type.getSimpleName();
   }


   private boolean isDynamicType(final Field field)
   {
      return field.getAnnotation(XmlElementRef.class) != null;
   }


   Class getListGenericType(final Field field)
   {
      if (field.getGenericType() instanceof ParameterizedType pt && pt.getActualTypeArguments().length == 1)
      {
         final var listType = pt.getActualTypeArguments()[0];
         if (listType instanceof Class cls)
            return cls;
      }
      return null;
   }

   private static final CommentFormatter formatter = new CommentFormatter();
   private Reflections mReflections = new Reflections("com.bitwig.dawproject");
}
