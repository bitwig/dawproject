package com.bitwig.dawproject;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors;

import com.bitwig.dawproject.timeline.*;
import com.bitwig.dawproject.device.*;
import com.github.therapi.runtimejavadoc.ClassJavadoc;
import com.github.therapi.runtimejavadoc.Comment;
import com.github.therapi.runtimejavadoc.CommentFormatter;
import com.github.therapi.runtimejavadoc.FieldJavadoc;
import com.github.therapi.runtimejavadoc.RuntimeJavadoc;
import com.vladsch.flexmark.ast.Heading;
import com.vladsch.flexmark.ast.Paragraph;
import com.vladsch.flexmark.ast.Text;
import com.vladsch.flexmark.ext.autolink.AutolinkExtension;
import com.vladsch.flexmark.ext.gfm.strikethrough.StrikethroughExtension;
import com.vladsch.flexmark.ext.tables.TableBlock;
import com.vladsch.flexmark.ext.tables.TableBody;
import com.vladsch.flexmark.ext.tables.TableCell;
import com.vladsch.flexmark.ext.tables.TableHead;
import com.vladsch.flexmark.ext.tables.TableRow;
import com.vladsch.flexmark.ext.tables.TablesExtension;
import com.vladsch.flexmark.ext.toc.TocExtension;
import com.vladsch.flexmark.formatter.Formatter;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Document;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.data.MutableDataSet;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementRef;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlEnumValue;
import jakarta.xml.bind.annotation.XmlIDREF;
import jakarta.xml.bind.annotation.XmlRootElement;
import org.jetbrains.annotations.NotNull;
import org.junit.Assert;
import org.junit.Test;

public class GenerateDocumentationTest
{
   private static String format(Comment c)
   {
      return formatter.format(c);
   }

   @Test
   public void createClassSummary() throws IOException
   {
      final var htmlFile = new File("target/Reference.html");
      final var markdownFile = new File("target/Reference.md");

      MutableDataSet options = new MutableDataSet();

      // uncomment to convert soft-breaks to hard breaks
      //options.set(HtmlRenderer.SOFT_BREAK, "<br />\n");

      options.set(Parser.EXTENSIONS,
         Arrays.asList(
            TablesExtension.create(),
            AutolinkExtension.create(),
            StrikethroughExtension.create()));

      options.set(Formatter.SETEXT_HEADING_EQUALIZE_MARKER, false);

      options.set(TablesExtension.TRIM_CELL_WHITESPACE, false);
      options.set(TablesExtension.HEADER_SEPARATOR_COLUMN_MATCH, false);

      Parser parser = Parser.builder(options).build();

      final Document document = createDocument(parser);

      final var html = HtmlRenderer.builder(options).build().render(document);
      Files.write(htmlFile.toPath(), Collections.singleton(html), StandardCharsets.UTF_8);

      final var commonMark = Formatter.builder(options).build().render(document);
      Files.write(markdownFile.toPath(), Collections.singleton(commonMark), StandardCharsets.UTF_8);

      Assert.assertTrue(htmlFile.exists());
      Assert.assertTrue(markdownFile.exists());
   }

   @NotNull
   private Document createDocument(final Parser parser) throws IOException
   {
      final var document = parser.parse("");
      document.appendChild(createHeading("XML Element Reference", 1));

      createClassesSummary(document, "Root", new Class[] {
         Project.class,
         MetaData.class,
      });

      createClassesSummary(document, "Other", new Class[] {
         Application.class,
         FileReference.class,
         Transport.class,
      });

      createClassesSummary(document, "Mixer", new Class[] {
         Track.class,
         Channel.class,
         Send.class,
      });

      createClassesSummary(document, "Timeline", new Class[] {
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

      createClassesSummary(document, "Parameters", new Class[] {
         Parameter.class,
         BoolParameter.class,
         EnumParameter.class,
         IntegerParameter.class,
         RealParameter.class,
         TimeSignatureParameter.class,
      });

      createClassesSummary(document, "Automation", new Class[] {
         AutomationTarget.class,
         Points.class,
         Point.class,
         RealPoint.class,
         BoolPoint.class,
         EnumPoint.class,
         IntegerPoint.class,
         TimeSignaturePoint.class,
      });

      createClassesSummary(document, "Device", new Class[] {
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

      createClassesSummary(document, "Abstract", new Class[] {
         Nameable.class,
         Referenceable.class,
      });
      return document;
   }

   public void createClassesSummary(final Document document, final String label, final Class[] classes) throws IOException
   {
      document.appendChild(createHeading(label + " Elements", 2));

      for (final var cls : classes)
         createClassSummary(document, cls);
   }

   private String getElementNameForClass(Class cls)
   {
      final var rootElement = cls.getDeclaredAnnotation(XmlRootElement.class);
      if (rootElement instanceof XmlRootElement re && !re.name().startsWith("#"))
         return re.name();
      return cls.getSimpleName();
   }

   public void createClassSummary(final Document document, final Class cls) throws IOException
   {
      final var elementName = getElementNameForClass(cls);
      document.appendChild(createHeading(elementName, 3));

      ClassJavadoc classDoc = RuntimeJavadoc.getJavadoc(cls);

      if (!classDoc.isEmpty())
      {
         document.appendChild(createParagraph(format(classDoc.getComment())));
      }

      var superClass = cls.getSuperclass();

      if (superClass != Object.class)
      {
         StringBuilder sb = new StringBuilder();
         sb.append("\n\nInherits from ");

         while (superClass != Object.class)
         {
            sb.append(getElementNameForClass(superClass));
            superClass = superClass.getSuperclass();
            if (superClass != Object.class)
               sb.append(", ");
         }
         sb.append(" (marked with *)");
         document.appendChild(createParagraph(sb.toString()));
      }

      if (Modifier.isAbstract(cls.getModifiers()))
      {
         document.appendChild(createParagraph("\nThis element is abstract in the DOM and cannot be used as an XML element directly."));
      }

      processAttributes(cls, document);
      processChildElements(cls, document);
   }

   private void processAttributes(final Class cls, final Document document)
   {
      final var table = new TableBlock();
      final var tableHead = new TableHead();
      tableHead.appendChild(createTableRow("Attribute", "Description", "Type", "Required"));
      final var tableBody = new TableBody();
      table.appendChain(tableHead);
      table.appendChild(tableBody);

      boolean shouldAdd = false;

      for (final var field : cls.getFields())
      {
         final var fieldJavadoc = RuntimeJavadoc.getJavadoc(field);
         processAttribute(tableBody, field, fieldJavadoc, field.getDeclaringClass() == cls);
         shouldAdd = true;
      }

      if (shouldAdd)
         document.appendChild(table);
   }

   private void processAttribute(
      final TableBody tableBody, final Field field, final FieldJavadoc javadoc,
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

            if (!isDeclaredInThisClass)
               name += "*";

            tableBody.appendChild(createTableRow(name, comment, getType(field, javadoc), (attribute.required() ? "yes" : "no")));
         }
      }
   }

   private void processChildElements(final Class cls, final Document document) throws IOException
   {
      final var table = new TableBlock();
      final var tableHead = new TableHead();
      tableHead.appendChild(createTableRow("Attribute", "Description", "Type", "Required"));
      final var tableBody = new TableBody();
      table.appendChain(tableHead);
      table.appendChild(tableBody);

      boolean shouldAdd = false;

      for (final var field : cls.getFields())
      {
         final var fieldJavadoc = RuntimeJavadoc.getJavadoc(field);
         processChildElement(tableBody, field, fieldJavadoc, field.getDeclaringClass() == cls);
         shouldAdd = true;
      }

      if (shouldAdd)
         document.appendChild(table);
   }

   private void processChildElement(
      TableBody tableBody, final Field field, final FieldJavadoc javadoc,
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
            tableBody.appendChild(createTableRow(name, comment, getType(field, javadoc), (wrapper.required() ? "yes" : "no")));
            return;
         }
         else if (annotation instanceof XmlElement element)
         {
            tableBody.appendChild(createTableRow(name, comment, getType(field, javadoc), (element.required() ? "yes" : "no")));
         }
         else if (annotation instanceof XmlElementRef element)
         {
            tableBody.appendChild(createTableRow(name, comment, getType(field, javadoc), (element.required() ? "yes" : "no")));
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

   private Heading createHeading(final String text, int level)
   {
      final var heading = new Heading();
      heading.setLevel(level);
      heading.appendChild(new Text(text));
      heading.setOpeningMarker(BasedSequence.repeatOf("#", level));
      return heading;
   }

   private Paragraph createParagraph(final String text)
   {
      final var paragraph = new Paragraph();
      paragraph.appendChild(new Text(text));
      return paragraph;
   }

   private Node createTableRow(final String... texts)
   {
      final var headerRow = new TableRow();
      for (final String text : texts)
      {
         final var tableCell = new TableCell();
         tableCell.appendChild(new Text(text));
         headerRow.appendChild(tableCell);
      }

      return headerRow;
   }

   private static final CommentFormatter formatter = new CommentFormatter();
}
