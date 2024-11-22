package com.bitwig.dawproject;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import com.bitwig.dawproject.device.AuPlugin;
import com.bitwig.dawproject.device.BuiltinDevice;
import com.bitwig.dawproject.device.ClapPlugin;
import com.bitwig.dawproject.device.Compressor;
import com.bitwig.dawproject.device.Device;
import com.bitwig.dawproject.device.DeviceRole;
import com.bitwig.dawproject.device.EqBand;
import com.bitwig.dawproject.device.EqBandType;
import com.bitwig.dawproject.device.Equalizer;
import com.bitwig.dawproject.device.Limiter;
import com.bitwig.dawproject.device.NoiseGate;
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
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementRef;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlIDREF;
import org.junit.Assert;
import org.junit.Test;

public class GenerateCppTest
{
   private final static Class[] classes = new Class[] {
      // Root
      Project.class, MetaData.class,
      // Other
      Application.class, FileReference.class, Transport.class,
      // Mixer
      Lane.class, Track.class, Channel.class, Send.class,
      // Timeline
      Arrangement.class,
      Scene.class,
      ClipSlot.class,
      Timeline.class,
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
      // Parameters
      Parameter.class,
      BoolParameter.class,
      EnumParameter.class,
      IntegerParameter.class,
      RealParameter.class,
      TimeSignatureParameter.class,
      // Automation
      Points.class,
      AutomationTarget.class,
      Point.class,
      RealPoint.class,
      BoolPoint.class,
      EnumPoint.class,
      IntegerPoint.class,
      TimeSignaturePoint.class,
      // Device
      Device.class, AuPlugin.class, ClapPlugin.class, Plugin.class, Vst2Plugin.class, Vst3Plugin.class,
      BuiltinDevice.class,
      Compressor.class,
      Equalizer.class, EqBand.class,
      Limiter.class,
      NoiseGate.class,
      // Abstract
      Nameable.class,/* Referenceable.class,*/ MediaFile.class,
   };

   private final static Enum<?>[][] enums = new Enum[][] {
      TimeUnit.values(),
      Interpolation.values(),
      Unit.values(),
      SendType.values(),
      MixerRole.values(),
      DeviceRole.values(),
      ContentType.values(),
      ExpressionType.values(),
      EqBandType.values(),
   };

   @Test
   public void generateCppFile() throws IOException
   {
      final var includePath = Paths.get("include");
      if (!Files.exists(includePath))
         Files.createDirectory(includePath);
      Assert.assertTrue(Files.isDirectory(includePath));
      final var targetPathString = includePath.toAbsolutePath() + "/";

      final var sb = new StringBuilder();

      // generate common header
      sb.append("#pragma once\n\n");
      sb.append("#include <optional>\n");
      sb.append("#include <string>\n");
      sb.append("#include <vector>\n");
      sb.append("\n");
      sb.append("namespace DawProject\n{\n");
      sb.append("//constants\n");
      sb.append("static constexpr std::string_view CURRENT_VERSION = \"" + Project.CURRENT_VERSION + "\";\n\n");
      sb.append("//enums\n");
      generateEnums(sb);
      sb.append("} // namespace DawProject\n");
      final var enumHeaderFile = new File(targetPathString + "common.h");
      Files.write(enumHeaderFile.toPath(), Collections.singleton(sb.toString()), StandardCharsets.UTF_8);
      Assert.assertTrue(enumHeaderFile.exists());      

      // generate class files
      for (final var cls : classes)
      {
         sb.setLength(0);
         sb.append("#pragma once\n\n");
         sb.append("#include \"common.h\"\n");
         final var superclass = cls.getSuperclass();
         if(isRelevantSuperClass(superclass))
            sb.append("#include \"" + superclass.getSimpleName() + ".h\"\n");
         var forwardDeclaredClassNames = new ArrayList<String>();
         for (final var otherClass : classes)
         {
            if(otherClass == cls)
               continue; // don't include itself
            if(otherClass == superclass)
               continue; // already handled above
            for(final var field : cls.getDeclaredFields())
            {
               Class<?> fieldType = field.getType();
               if (fieldType.isArray())
                  fieldType = fieldType.getComponentType();
               else if(fieldType == List.class)
                  fieldType = getListGenericType(field);
               if(otherClass == fieldType)
               {
                  if(isIdRef(field))
                  {
                     forwardDeclaredClassNames.add(otherClass.getSimpleName());
                     continue;
                  }
                  else
                     sb.append("#include \"" + otherClass.getSimpleName() + ".h\"\n");
                  break;
               }
            }
         }
         sb.append("\n");
         sb.append("namespace DawProject\n{\n");
         for(final var className : forwardDeclaredClassNames)
         {
            sb.append("struct " + className + ";\n");
         }
         if(!forwardDeclaredClassNames.isEmpty())
            sb.append("\n");
         generateClass(sb, cls);
         sb.append("} // namespace DawProject\n");
         final var classHeaderFile = new File(targetPathString + cls.getSimpleName() + ".h");
         Files.write(classHeaderFile.toPath(), Collections.singleton(sb.toString()), StandardCharsets.UTF_8);
         Assert.assertTrue(classHeaderFile.exists());
      }
   }

   private boolean isRelevantSuperClass(Class superclass)
   {
      return superclass != Object.class && superclass != Referenceable.class;
   }

   private void generateEnums(StringBuilder sb)
   {
      boolean isFirst = true;
      for(final var values : enums)
      {
         if(isFirst)
            isFirst = false;
         else
            sb.append("\n");
         generateEnum(sb, values);
      }
   }

   private void generateEnumEntryName(StringBuilder sb, String enumName, String entryName)
   {
      for (int i = 0; i < enumName.length(); i++)
      {
         final var c = enumName.charAt(i);
         if(Character.isUpperCase(c) && i != 0)
         {
            sb.append("_");
         }
         sb.append(Character.toString(c).toUpperCase());
      }

      sb.append("_");
      sb.append(entryName.toUpperCase());
   }

   private void generateEnum(final StringBuilder sb, final Enum[] values)
   {
      final var name = values[0].getClass().getSimpleName();
      sb.append("enum " + name + ": int\n{\n");
      for (Enum value : values)
      {
         sb.append("    ");
         generateEnumEntryName(sb, name, value.name());
         sb.append(" = ");
         sb.append(value.ordinal());
         sb.append(",\n");
      }

      sb.append("};\n");
   }

   private void appendJavaDoc(final StringBuilder sb, final BaseJavadoc javadoc, int indentation)
   {
      if (javadoc.isEmpty())
         return;
      final var comment = formatter.format(javadoc.getComment());
      if (comment.isEmpty())
         return;
      final var indent = " ".repeat(indentation);
      sb.append(indent + "/* ");
      sb.append(comment.replace("\n", "\n" + indent));
      sb.append(" */\n");
   }

   private void generateClass(final StringBuilder sb, final Class<?> cls)
   {
      appendJavaDoc(sb, RuntimeJavadoc.getJavadoc(cls), 0);

      final var name = cls.getSimpleName();
      sb.append("struct " + name + "\n");

      var superClass = cls.getSuperclass();
      if (isRelevantSuperClass(superClass))
         sb.append("    : public " + superClass.getSimpleName() + "\n");

      sb.append("{\n");

      for (final var field : cls.getDeclaredFields())
      {
         if(Modifier.isStatic(field.getModifiers()))
            continue;
         final var fieldJavadoc = RuntimeJavadoc.getJavadoc(field);
         generateStructField(sb, field, fieldJavadoc);
      }

      sb.append("};\n");
   }

   private void generateStructField(final StringBuilder sb, final Field field, final FieldJavadoc fieldJavadoc)
   {
      final String commentText = fieldJavadoc != null && !fieldJavadoc.isEmpty() ? fieldJavadoc.getComment().toString() : "";

      final var isCommentLong = commentText.length() > 50 || commentText.contains("\n");

      if (isCommentLong)
         appendJavaDoc(sb, fieldJavadoc, 4);

      var typeString = getTypeString(field);
      if(isIdRef(field))
         typeString = typeString + "*";
      else if(!isRequired(field) && !isListOrArray(field))
         typeString = "std::optional<" + typeString + ">";
      sb.append("    ");
      sb.append(typeString + " " + getFieldName(field));
      sb.append(";");

      if (!isCommentLong && !commentText.isEmpty())
      {
         sb.append(" // ");
         sb.append(commentText);
      }
      sb.append("\n");
   }

   private boolean isListOrArray(final Field field)
   {
      final var type = field.getType();
      return type.isArray() || type == List.class;
   }

   private boolean isIdRef(final Field field)
   {
      return field.getAnnotation(XmlIDREF.class) != null;
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
         else if (annotation instanceof XmlAttribute e && e.required())
            return true;
      }
      return false;
   }

   private String getTypeString(final Class<?> type)
   {
      if (type == String.class)
         return "std::string";
      if (type == Integer.class)
         return "int";
      if (type == Double.class)
         return "double";
      if (type == Boolean.class)
         return "bool";
      if (type == double.class)
         return "double";
      if (type == int.class)
         return "int";
      if (type.isArray())
         return "std::vector<" + getTypeString(type.getComponentType()) + ">"; // recursive call for getTypeString!
      return type.getSimpleName();
   }

   private String getTypeString(final Field field)
   {
      final Class<?> type1 = field.getType();
      if (type1 == List.class)
         return "std::vector<" + getListGenericType(field).getSimpleName() + ">";

      return getTypeString(field.getType());
   }

   private static String getFieldName(final Field field)
   {
      return field.getName();
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
}
