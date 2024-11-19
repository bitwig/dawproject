package com.bitwig.dawproject;

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
import static j2html.TagCreator.rawHtml;
import static j2html.TagCreator.span;
import static j2html.TagCreator.table;
import static j2html.TagCreator.td;
import static j2html.TagCreator.text;
import static j2html.TagCreator.th;
import static j2html.TagCreator.title;
import static j2html.TagCreator.tr;

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
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.Assert;
import org.junit.Test;
import org.reflections.Reflections;

import com.bitwig.dawproject.device.AuPlugin;
import com.bitwig.dawproject.device.BuiltinDevice;
import com.bitwig.dawproject.device.ClapPlugin;
import com.bitwig.dawproject.device.Compressor;
import com.bitwig.dawproject.device.Device;
import com.bitwig.dawproject.device.EqBand;
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
import com.bitwig.dawproject.timeline.Timeline;
import com.bitwig.dawproject.timeline.Video;
import com.bitwig.dawproject.timeline.Warp;
import com.bitwig.dawproject.timeline.Warps;
import com.github.therapi.runtimejavadoc.ClassJavadoc;
import com.github.therapi.runtimejavadoc.Comment;
import com.github.therapi.runtimejavadoc.CommentFormatter;
import com.github.therapi.runtimejavadoc.FieldJavadoc;
import com.github.therapi.runtimejavadoc.RuntimeJavadoc;

import j2html.rendering.IndentedHtml;
import j2html.tags.DomContent;
import j2html.tags.specialized.DivTag;
import j2html.tags.specialized.HtmlTag;
import j2html.tags.specialized.SpanTag;
import j2html.tags.specialized.TableTag;
import j2html.tags.specialized.TdTag;
import j2html.tags.specialized.TrTag;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementRef;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlEnumValue;
import jakarta.xml.bind.annotation.XmlIDREF;
import jakarta.xml.bind.annotation.XmlRootElement;


/**
 * Not really a test but generates the HTML documentation.
 */
public class GenerateDocumentationTest
{
    private static final CommentFormatter formatter    = new CommentFormatter ();

    private Reflections                   mReflections = new Reflections ("com.bitwig.dawproject");


    /**
     * Create a HTML class summary.
     * 
     * @throws IOException Could not write the HTML file
     */
    @Test
    public void createClassSummary () throws IOException
    {
        final var htmlFile = new File ("Reference.html");

        final var title = "DAWPROJECT XML Reference";
        final var html = createDocument (title);

        Files.write (htmlFile.toPath (), Collections.singleton (html.render (IndentedHtml.inMemory ())), StandardCharsets.UTF_8);

        Assert.assertTrue (htmlFile.exists ());
    }


    private HtmlTag createDocument (final String title)
    {
        final var toc = div ().withClass ("toc");
        toc.with (b ("Table of Contents"));
        return html (head (title (title), link ().withRel ("stylesheet").withHref ("style.css")), body (h1 (title), div (a (rawHtml ("&uarr;")).withHref ("#top")).withClass ("goto-toc"), toc, createClassesSummary (toc, "Root", new Class []
        {
            Project.class,
            MetaData.class,
        }), createClassesSummary (toc, "Other", new Class []
        {
            Application.class,
            FileReference.class,
            Transport.class,
        }), createClassesSummary (toc, "Mixer", new Class []
        {
            Track.class,
            Channel.class,
            Send.class,
        }),

                createClassesSummary (toc, "Timeline", new Class []
                {
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
                }),

                createClassesSummary (toc, "Parameters", new Class []
                {
                    Parameter.class,
                    BoolParameter.class,
                    EnumParameter.class,
                    IntegerParameter.class,
                    RealParameter.class,
                    TimeSignatureParameter.class,
                }),

                createClassesSummary (toc, "Automation", new Class []
                {
                    Points.class,
                    AutomationTarget.class,
                    Point.class,
                    RealPoint.class,
                    BoolPoint.class,
                    EnumPoint.class,
                    IntegerPoint.class,
                    TimeSignaturePoint.class,
                }),

                createClassesSummary (toc, "Device", new Class []
                {
                    Device.class,
                    AuPlugin.class,
                    ClapPlugin.class,
                    Plugin.class,
                    Vst2Plugin.class,
                    Vst3Plugin.class,

                    BuiltinDevice.class,
                    Compressor.class,
                    Equalizer.class,
                    EqBand.class,
                    Limiter.class,
                    NoiseGate.class,
                }),

                createClassesSummary (toc, "Abstract", new Class []
                {
                    Nameable.class,
                    Referenceable.class,
                    MediaFile.class,
                })));
    }


    private DomContent createClassesSummary (final DivTag toc, final String label, final Class<?> [] classes)
    {
        final var content = new ArrayList<DomContent> ();

        content.add (h2 (label + " Elements"));

        final var tocP = p (label + " Elements");
        final var tocDiv = div ();
        toc.with (tocP, tocDiv);

        for (final var cls: classes)
        {
            content.add (createClassSummary (cls));
            tocDiv.with (createElementLink (cls));
        }

        return new SpanTag ().with (content).withClass ("elements-block");
    }


    private DomContent createClassSummary (final Class<?> cls)
    {
        final var content = span ().withClass ("element-block");
        final var elementName = getElementNameForClass (cls);
        content.with (h3 (bracketize (elementName)).withId (elementName).withClass ("element-title"));

        ClassJavadoc classDoc = RuntimeJavadoc.getJavadoc (cls);

        if (!classDoc.isEmpty ())
        {
            content.with (p (formatJavadocComment (classDoc.getComment ())));
        }

        var superClass = cls.getSuperclass ();

        if (superClass != Object.class)
        {
            final var p = p ("Inherits from").withClass ("bubble");

            while (superClass != Object.class)
            {
                p.with (createElementLink (superClass));
                superClass = superClass.getSuperclass ();
            }
            content.with (p);
        }

        final var subTypes = this.mReflections.getSubTypesOf (cls);

        if (!subTypes.isEmpty ())
        {
            final var p = p ("Implementations").withClass ("bubble");

            subTypes.stream ().sorted (Comparator.comparing (Class::getName)).forEach (c -> p.with (createElementLink (c)));

            content.with (p);
        }

        if (Modifier.isAbstract (cls.getModifiers ()))
        {
            content.with (p ("\nThis element is abstract in the DOM and cannot be used as an XML element directly."));
        }

        final var table = table ().withClass ("detail-table");

        content.with (table);
        createAttributeTable (table, cls);
        createElementsTable (table, cls);

        return content;
    }


    private static void createAttributeTable (final TableTag table, final Class<?> cls)
    {
        final var content = new ArrayList<DomContent> ();

        content.add (tr (th ("Attribute name").withStyle ("text-align:center;"), th ("Description"), th ("Type").withStyle ("text-align:center;"), th ("Required").withStyle ("text-align:center;")));

        for (final var field: cls.getFields ())
        {
            final var fieldJavadoc = RuntimeJavadoc.getJavadoc (field);
            createAttributeTableRow (field, fieldJavadoc, field.getDeclaringClass () == cls).ifPresent (content::add);
        }

        if (content.size () > 1)
            table.with (content);
    }


    private static Optional<TrTag> createAttributeTableRow (final Field field, final FieldJavadoc javadoc, final boolean isDeclaredInThisClass)
    {
        for (Annotation annotation: field.getAnnotations ())
        {
            if (annotation instanceof XmlAttribute attribute)
            {
                final var comment = getComment (field, javadoc);
                var name = attribute.name ();
                if (name.startsWith ("#"))
                    name = field.getName ();

                var tr = tr (td (name).withStyle ("text-align:center;"), td (comment), td (getType (field)).withStyle ("text-align:center;"), td (attribute.required () ? "yes" : "no").withStyle ("text-align:center;"));

                if (!isDeclaredInThisClass)
                    tr = tr.withClass ("inherited");

                return Optional.of (tr);
            }
        }
        return Optional.empty ();
    }


    private void createElementsTable (final TableTag table, final Class<?> cls)
    {
        final var content = new ArrayList<DomContent> ();

        content.add (tr (th ("Element name").withStyle ("text-align:center;"), th ("Description"), th ("Element Type").withStyle ("text-align:center;"), th ("Required").withStyle ("text-align:center;")));

        for (final var field: cls.getFields ())
        {
            final var fieldJavadoc = RuntimeJavadoc.getJavadoc (field);
            createElementTableRow (field, fieldJavadoc, field.getDeclaringClass () == cls).ifPresent (content::add);
        }

        if (content.size () > 1)
            table.with (content);
    }


    private static boolean isDynamicType (final Field field)
    {
        return field.getAnnotation (XmlElementRef.class) != null;
    }


    private static boolean isRequired (final Field field)
    {
        for (Annotation annotation: field.getAnnotations ())
        {
            if (annotation instanceof XmlElementWrapper e && e.required ())
                return true;
            if (annotation instanceof XmlElement e && e.required ())
                return true;
            if (annotation instanceof XmlElementRef e && e.required ())
                return true;
        }
        return false;
    }


    private static boolean isElement (final Field field)
    {
        for (Annotation annotation: field.getAnnotations ())
        {
            if (annotation instanceof XmlElementWrapper)
                return true;
            if (annotation instanceof XmlElement)
                return true;
            if (annotation instanceof XmlElementRef)
                return true;
        }
        return false;
    }


    boolean isFieldList (final Field field)
    {
        final Class<?> type = field.getType ();
        return type == List.class;
    }


    Class<?> getListGenericType (final Field field)
    {
        if (field.getGenericType () instanceof ParameterizedType pt && pt.getActualTypeArguments ().length == 1)
        {
            final var listType = pt.getActualTypeArguments ()[0];
            if (listType instanceof Class cls)
                return cls;
        }
        return null;
    }


    private static DomContent formatJavadocComment (Comment comment)
    {
        return rawHtml (formatter.format (comment));
    }


    private Optional<TrTag> createElementTableRow (final Field field, final FieldJavadoc javadoc, final boolean isDeclaredInThisClass)
    {
        if (!isElement (field))
            return Optional.empty ();

        final var comment = getComment (field, javadoc);
        var name = getFieldName (field);

        final var isRequired = isRequired (field);
        final boolean isList = isFieldList (field);

        if (isDynamicType (field))
        {
            final var elementName = td (text ("from Type"));
            if (isList)
                elementName.with (br (), text ("(multiple)"));
            TdTag typeCell = createElementLinksToSubclasses (field);

            var tr = tr (elementName.withStyle ("text-align:center;"), td (comment), typeCell.withStyle ("text-align:center;"), td (isRequired ? "yes" : "no").withStyle ("text-align:center;"));
            if (!isDeclaredInThisClass)
                tr = tr.withClass ("inherited");

            return Optional.of (tr);
        }

        var typeCell = td (createElementLink (isList ? getListGenericType (field) : field.getType ()));
        final var elementName = td ("<" + name + ">");
        if (isList)
            elementName.with (br (), text ("(multiple)"));

        var tr = tr (elementName.withStyle ("text-align:center;"), td (comment), typeCell.withStyle ("text-align:center;"), td (isRequired ? "yes" : "no").withStyle ("text-align:center;"));
        if (!isDeclaredInThisClass)
            tr = tr.withClass ("inherited");

        return Optional.of (tr);
    }


    private TdTag createElementLinksToSubclasses (final Field field)
    {
        final var td = td ();
        final boolean isList = isFieldList (field);

        final var isCollection = isList && field.getGenericType () instanceof final ParameterizedType pt && pt.getActualTypeArguments ().length == 1;

        final Class<?> type = isCollection ? getListGenericType (field) : field.getType ();
        this.mReflections.getSubTypesOf (type).stream ().sorted (Comparator.comparing (Class::getName)).forEach (t -> {
            td.with (createElementLink (t));
            td.with (br ());
        });

        return td;
    }


    private static DomContent getComment (final Field field, final FieldJavadoc javadoc)
    {
        final var comment = javadoc != null ? formatJavadocComment (javadoc.getComment ()) : text ("");

        final Class<?> type = field.getType ();

        final var enumClass = type.isEnum () ? type : type.isArray () ? type.getComponentType ().isEnum () ? type.getComponentType () : null : null;

        if (enumClass != null)
        {
            final var choices = "Possible values: " + Arrays.stream (enumClass.getFields ()).map (f -> getFieldName (f)).collect (Collectors.joining (", "));
            return span (comment, p (choices));
        }

        return comment;
    }


    private static String getFieldName (final Field field)
    {
        for (Annotation annotation: field.getAnnotations ())
        {
            if (annotation instanceof XmlElementWrapper wrapper)
            {
                if (!wrapper.name ().startsWith ("#"))
                    return wrapper.name ();
            }
            else if (annotation instanceof XmlElement element)
            {
                if (!element.name ().startsWith ("#"))
                    return element.name ();
            }
            else if (annotation instanceof XmlElementRef element)
            {
                if (!element.name ().startsWith ("#"))
                    return element.name ();
            }
            else if (annotation instanceof XmlEnumValue element)
            {
                if (!element.value ().startsWith ("#"))
                    return element.value ();
            }
        }

        return field.getName ();
    }


    private static String getType (final Field field)
    {
        final var xmlIDREF = field.getAnnotation (XmlIDREF.class);

        if (xmlIDREF != null)
            return "ID";

        final Class<?> type = field.getType ();

        if (type.isEnum ())
            return "Enum";

        if (type.isArray () && type.getComponentType ().isEnum ())
            return "Enum,...";

        return type.getSimpleName ();
    }


    private static String getElementNameForClass (Class<?> cls)
    {
        final var rootElement = cls.getDeclaredAnnotation (XmlRootElement.class);
        if (rootElement instanceof XmlRootElement re && !re.name ().startsWith ("#"))
            return re.name ();
        return cls.getSimpleName ();
    }


    private static DomContent createElementLink (Class<?> cls)
    {
        if (cls == String.class)
            return text ("text");

        if (cls.isPrimitive ())
            return text (cls.getSimpleName ());

        final var name = getElementNameForClass (cls);
        return a ("<" + name + ">").withHref ("#" + name).withClass ("element-link");
    }


    private static String bracketize (final String s)
    {
        return "<" + s + ">";
    }
}
