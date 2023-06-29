package com.bitwig.dawproject;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.bitwig.dawproject.timeline.*;
import com.bitwig.dawproject.device.*;
import com.github.therapi.runtimejavadoc.ClassJavadoc;
import com.github.therapi.runtimejavadoc.Comment;
import com.github.therapi.runtimejavadoc.CommentFormatter;
import com.github.therapi.runtimejavadoc.FieldJavadoc;
import com.github.therapi.runtimejavadoc.RuntimeJavadoc;
import j2html.tags.DomContent;
import j2html.tags.specialized.HtmlTag;
import j2html.tags.specialized.SpanTag;
import j2html.tags.specialized.TableTag;
import j2html.tags.specialized.TrTag;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementRef;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlEnumValue;
import jakarta.xml.bind.annotation.XmlIDREF;
import jakarta.xml.bind.annotation.XmlRootElement;
import org.junit.Assert;
import org.junit.Test;
import static j2html.TagCreator.*;
import static j2html.TagCreator.th;

public class GenerateDocumentationTest
{
   private static String format(Comment c)
   {
      return formatter.format(c);
   }

   @Test
   public void createClassSummary() throws IOException
   {
      final var htmlFile = new File("Reference.html");
      final var markdownFile = new File("target/Reference.md");

      final var title = "DAWPROJECT XML Reference";
      final var html = createDocument(title);

      Files.write(htmlFile.toPath(), Collections.singleton(html.render()), StandardCharsets.UTF_8);

      Assert.assertTrue(htmlFile.exists());
   }

   private HtmlTag createDocument(final String title) throws IOException
   {
      return html(
         head(
            title(title),
            link().withRel("stylesheet").withHref("style.css")),
         body(
            h1(title), createClassesSummary("Root", new Class[] {
               Project.class, MetaData.class,
            }), createClassesSummary("Other", new Class[] {
               Application.class, FileReference.class, Transport.class,
            }), createClassesSummary("Mixer", new Class[] {
               Track.class, Channel.class, Send.class,
            }),

            createClassesSummary("Timeline", new Class[] {
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
               Video.class,
               Warp.class,
               Warps.class
            }),

            createClassesSummary("Parameters", new Class[] {
               Parameter.class,
               BoolParameter.class,
               EnumParameter.class,
               IntegerParameter.class,
               RealParameter.class,
               TimeSignatureParameter.class,
            }),

            createClassesSummary("Automation", new Class[] {
               AutomationTarget.class,
               Points.class,
               Point.class,
               RealPoint.class,
               BoolPoint.class,
               EnumPoint.class,
               IntegerPoint.class,
               TimeSignaturePoint.class,
            }),

            createClassesSummary("Device", new Class[] {
               Device.class, AuPlugin.class, ClapPlugin.class, Plugin.class, Vst2Plugin.class, Vst3Plugin.class,

               BuiltinDevice.class,
               //Compressor.class,
               Equalizer.class, EqBand.class,
               //Limiter.class,
               //NoiseGate.class,
            }),

            createClassesSummary("Abstract", new Class[] {
               Nameable.class, Referenceable.class,
            })));
   }

   public DomContent createClassesSummary(final String label, final Class[] classes) throws IOException
   {
      final var content = new ArrayList<DomContent>();

      content.add(h2(label + " Elements"));

      for (final var cls : classes)
         content.add(createClassSummary(cls));

      return new SpanTag().with(content).withClass("elements-block");
   }

   private String getElementNameForClass(Class cls)
   {
      final var rootElement = cls.getDeclaredAnnotation(XmlRootElement.class);
      if (rootElement instanceof XmlRootElement re && !re.name().startsWith("#"))
         return re.name();
      return cls.getSimpleName();
   }

   private DomContent createElementLink(Class cls)
   {
      final var name = getElementNameForClass(cls);
      return a("<" + name + ">").withHref("#"+name).withClass("element-link");
   }

   private String bracketize(final String s)
   {
      return "<" + s + ">";
   }

   public DomContent createClassSummary(final Class cls) throws IOException
   {
      final var content = span().withClass("element-block");
      final var elementName = getElementNameForClass(cls);
      content.with(h3(bracketize(elementName)).withId(elementName).withClass("element-title"));

      ClassJavadoc classDoc = RuntimeJavadoc.getJavadoc(cls);

      if (!classDoc.isEmpty())
      {
         content.with(p(format(classDoc.getComment())));
      }

      var superClass = cls.getSuperclass();

      if (superClass != Object.class)
      {
         final var p = p("Inherits from");

         while (superClass != Object.class)
         {
            p.with(createElementLink(superClass));
            superClass = superClass.getSuperclass();
         }
         content.with(p);
      }

      if (Modifier.isAbstract(cls.getModifiers()))
      {
         content.with(p("\nThis element is abstract in the DOM and cannot be used as an XML element directly."));
      }

      createAttributeTable(cls).ifPresent(content::with);
      createElementsTable(cls).ifPresent(content::with);

      return content;
   }

   private Optional<TableTag> createAttributeTable(final Class cls)
   {
      final var content = new ArrayList<DomContent>();

      content.add(tr(th("Attribute"), th("Description"), th("Type"), th("Required")));

      for (final var field : cls.getFields())
      {
         final var fieldJavadoc = RuntimeJavadoc.getJavadoc(field);
         createAttributeTableRow(field, fieldJavadoc, field.getDeclaringClass() == cls).ifPresent(content::add);
      }

      if (content.size() > 1)
         return Optional.of(table().with(content));
      return Optional.empty();
   }

   private Optional<TrTag> createAttributeTableRow(
      final Field field, final FieldJavadoc javadoc,
      final boolean isDeclaredInThisClass)
   {
      for (Annotation annotation : field.getAnnotations())
      {
         if (annotation instanceof XmlAttribute attribute)
         {
            final var comment = getComment(field, javadoc);
            var name = attribute.name();
            if (name.startsWith("#"))
               name = field.getName();

            var tr =
               tr(td(name), td(comment), td(getType(field, javadoc)), td(attribute.required() ? "yes" : "no"));

            if (!isDeclaredInThisClass)
               tr = tr.withClass("inherited");

            return Optional.of(tr);
         }
      }
      return Optional.empty();
   }

   private Optional<TableTag> createElementsTable(final Class cls) throws IOException
   {
      final var content = new ArrayList<DomContent>();

      content.add(tr(th("Element name"), th("Description"), th("Element Type"), th("Required")));

      for (final var field : cls.getFields())
      {
         final var fieldJavadoc = RuntimeJavadoc.getJavadoc(field);
         createElementTableRow(field, fieldJavadoc, field.getDeclaringClass() == cls).ifPresent(content::add);
      }

      if (content.size() > 1)
         return Optional.of(table().with(content));
      return Optional.empty();
   }

   private boolean isDynamicType(final Field field)
   {
      return field.getAnnotation(XmlElementRef.class) != null;
   }

   private boolean isRequired(final Field field)
   {
      for (Annotation annotation : field.getAnnotations())
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

   private boolean isElement(final Field field)
   {
      for (Annotation annotation : field.getAnnotations())
      {
         if (annotation instanceof XmlElementWrapper)
            return true;
         else if (annotation instanceof XmlElement)
            return true;
         else if (annotation instanceof XmlElementRef)
            return true;
      }
      return false;
   }

   boolean isFieldList(final Field field)
   {
      final Class<?> type = field.getType();
      return type == List.class;
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

   private Optional<TrTag> createElementTableRow(
      final Field field, final FieldJavadoc javadoc,
      final boolean isDeclaredInThisClass) throws IOException
   {
      if (!isElement(field))
         return Optional.empty();

      final var comment = getComment(field, javadoc);
      var name = getFieldName(field);

      final var isRequired = isRequired(field);
      final boolean isList = isFieldList(field);

      if (isDynamicType(field))
      {
         var typeDescription = isList ? "<Type>..." : "<Type>";
         var typeCell = td(text("instance of "), createElementLink(field.getType()));

         if (isList && field.getGenericType() instanceof ParameterizedType pt && pt.getActualTypeArguments().length == 1)
         {
            typeCell = td(text("instance of "), createElementLink(getListGenericType(field)));
         }

         var tr = tr(td(typeDescription), td(comment), typeCell, td(isRequired ? "yes" : "no"));
         if (!isDeclaredInThisClass) tr = tr.withClass("inherited");

         return Optional.of(tr);
      }
      else
      {
         var typeCell = td(createElementLink(isList ? getListGenericType(field) : field.getType()));
         final var typeDescription = isList ? "<" + name + ">..." : "<" + name + ">";

         var tr = tr(td(typeDescription), td(comment), typeCell, td(isRequired ? "yes" : "no"));
         if (!isDeclaredInThisClass) tr = tr.withClass("inherited");

         return Optional.of(tr);
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
}
