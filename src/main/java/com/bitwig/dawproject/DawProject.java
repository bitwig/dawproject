package com.bitwig.dawproject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import javax.xml.XMLConstants;
import javax.xml.transform.Result;
import javax.xml.transform.stream.StreamResult;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.SchemaOutputResolver;

import org.apache.commons.io.ByteOrderMark;
import org.apache.commons.io.input.BOMInputStream;
import org.xml.sax.SAXException;


/** Several helper functions to deal with DAWproject files. */
public class DawProject
{
    /** The (English) description text of the format. */
    public static final String  FORMAT_NAME    = "DAWproject exchange format";

    /** The file extension for DAWproject files. */
    public static final String  FILE_EXTENSION = "dawproject";

    private static final String PROJECT_FILE   = "project.xml";
    private static final String METADATA_FILE  = "metadata.xml";


    /** Private constructor since this is a helper class. */
    private DawProject ()
    {
        // Intentionally empty
    }


    /**
     * Exports the XML schema of the given Java class.
     *
     * @param file The file into which to store the XML schema
     * @param cls The class from which to create the schema
     * @throws IOException Could not write the file
     */
    public static void exportSchema (final File file, final Class<?> cls) throws IOException
    {
        try (final FileOutputStream fileOutputStream = new FileOutputStream (file))
        {
            final var resolver = new SchemaOutputResolver ()
            {
                @Override
                public Result createOutput (final String namespaceUri, final String suggestedFileName) throws IOException
                {
                    final StreamResult result = new StreamResult (new Writer ()
                    {
                        // Ugly workaround to ensure the same newline on all platforms

                        private final PrintWriter pw = new PrintWriter (new OutputStreamWriter (fileOutputStream, StandardCharsets.UTF_8))
                        {
                            @Override
                            public void println ()
                            {
                                this.write ('\n');
                            }
                        };


                        @Override
                        public void write (final String str) throws IOException
                        {
                            this.pw.write (str);
                        }


                        @Override
                        public void write (final int c) throws IOException
                        {
                            this.pw.write (c);
                        }


                        @Override
                        public void write (final char [] cbuf, final int off, final int len) throws IOException
                        {
                            this.pw.write (cbuf, off, len);
                        }


                        @Override
                        public void flush () throws IOException
                        {
                            this.pw.flush ();
                        }


                        @Override
                        public void close () throws IOException
                        {
                            this.pw.close ();
                        }
                    });

                    result.setSystemId (file.getName ());
                    return result;
                }
            };

            createContext (cls).generateSchema (resolver);
        }
        catch (final JAXBException ex)
        {
            throw new IOException (ex);
        }
    }


    /**
     * Stores the given project to the given file.
     *
     * @param project The project to store
     * @param file The file into which to store the project
     * @throws IOException Could not store the project
     */
    public static void saveXML (final Project project, final File file) throws IOException
    {
        final String projectXML = toXML (project);
        try (final FileOutputStream fileOutputStream = new FileOutputStream (file))
        {
            fileOutputStream.write (projectXML.getBytes (StandardCharsets.UTF_8));
        }
    }


    /**
     * Stores the given project, metadata and embedded files to the given (ZIP) file.
     *
     * @param project The project to store
     * @param metadata The metadata to store
     * @param embeddedFiles The embedded files
     * @param file The file into which to store the project
     * @throws IOException Could not store the files
     */
    public static void save (final Project project, final MetaData metadata, final Map<File, String> embeddedFiles, final File file) throws IOException
    {
        final String metadataXML = toXML (metadata);
        final String projectXML = toXML (project);

        try (final ZipOutputStream zos = new ZipOutputStream (new FileOutputStream (file)))
        {
            addToZip (zos, METADATA_FILE, metadataXML.getBytes (StandardCharsets.UTF_8));
            addToZip (zos, PROJECT_FILE, projectXML.getBytes (StandardCharsets.UTF_8));

            for (final Map.Entry<File, String> entry: embeddedFiles.entrySet ())
                addToZip (zos, entry.getValue (), entry.getKey ());
        }
    }


    /**
     * Loads a project from the given file.
     *
     * @param file The file which contains the project in XML format
     * @return The loaded project
     * @throws IOException Could not load the project
     */
    public static Project loadProject (final File file) throws IOException
    {
        try (final ZipFile zipFile = new ZipFile (file))
        {
            final ZipEntry projectEntry = zipFile.getEntry (PROJECT_FILE);
            try (final InputStreamReader reader = stripBom (zipFile.getInputStream (projectEntry)))
            {
                return fromXML (reader, Project.class);
            }
        }
    }


    /**
     * Loads metadata from the given file.
     *
     * @param file The file which contains the metadata in XML format
     * @return The loaded metadata
     * @throws IOException Could not load the metadata
     */
    public static MetaData loadMetadata (final File file) throws IOException
    {
        try (final ZipFile zipFile = new ZipFile (file))
        {
            final ZipEntry entry = zipFile.getEntry (METADATA_FILE);
            try (final InputStreamReader reader = stripBom (zipFile.getInputStream (entry)))
            {
                return fromXML (reader, MetaData.class);
            }
        }
    }


    /**
     * Loads the data of an embedded file.
     *
     * @param file The ZIP archive which contains the embedded file
     * @param embeddedPath The path of the embedded file in the ZIP archive
     * @return The loaded data
     * @throws IOException Could not load the metadata
     */
    public static byte [] loadEmbedded (final File file, final String embeddedPath) throws IOException
    {
        try (final ZipFile zipFile = new ZipFile (file))
        {
            final ZipEntry entry = zipFile.getEntry (embeddedPath);
            try (final InputStream zipInputStream = zipFile.getInputStream (entry))
            {
                return zipInputStream.readAllBytes ();
            }
        }
    }


    /**
     * Validates the given project by serializing it to XML and validating the XML code to the
     * project XML schema.
     *
     * @param project The project to validate
     * @throws IOException Error reading/writing
     */
    public static void validate (final Project project) throws IOException
    {
        final String projectXML = toXML (project);
        try
        {
            final var context = createContext (Project.class);

            final var schemaFile = File.createTempFile ("schema", ".xml");
            exportSchema (schemaFile, Project.class);

            final SchemaFactory sf = SchemaFactory.newInstance (XMLConstants.W3C_XML_SCHEMA_NS_URI);
            final Schema schema = sf.newSchema (schemaFile);

            final var unmarshaller = context.createUnmarshaller ();
            unmarshaller.setSchema (schema);
            unmarshaller.unmarshal (new StringReader (projectXML));
        }
        catch (final JAXBException | SAXException ex)
        {
            throw new IOException (ex);
        }
    }


    private static String toXML (final Object object) throws IOException
    {
        try
        {
            final var context = createContext (object.getClass ());

            final var marshaller = context.createMarshaller ();
            marshaller.setProperty (Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            final var sw = new StringWriter ();
            marshaller.marshal (object, sw);

            return sw.toString ();
        }
        catch (final Exception e)
        {
            throw new IOException (e);
        }
    }


    private static JAXBContext createContext (final Class<?> cls) throws JAXBException
    {
        return JAXBContext.newInstance (cls);
    }


    private static <T extends Object> T fromXML (final InputStreamReader reader, final Class<T> cls) throws IOException
    {
        try
        {
            final var jaxbContext = JAXBContext.newInstance (cls);
            final var unmarshaller = jaxbContext.createUnmarshaller ();
            return cls.cast (unmarshaller.unmarshal (reader));
        }
        catch (final JAXBException e)
        {
            throw new IOException (e);
        }
    }


    private static void addToZip (final ZipOutputStream zos, final String path, final byte [] data) throws IOException
    {
        final ZipEntry entry = new ZipEntry (path);
        zos.putNextEntry (entry);
        zos.write (data);
        zos.closeEntry ();
    }


    private static void addToZip (final ZipOutputStream zos, final String path, final File file) throws IOException
    {
        final ZipEntry entry = new ZipEntry (path);
        zos.putNextEntry (entry);

        try (final FileInputStream fileInputStream = new FileInputStream (file))
        {
            final byte [] data = new byte [65536];
            int size = 0;
            while ((size = fileInputStream.read (data)) != -1)
                zos.write (data, 0, size);
            zos.flush ();
        }

        zos.closeEntry ();
    }


    private static InputStreamReader stripBom (final InputStream inputStream) throws IOException
    {
        final BOMInputStream bomInputStream = BOMInputStream.builder ().setInputStream (inputStream).setByteOrderMarks (ByteOrderMark.UTF_8, ByteOrderMark.UTF_16LE, ByteOrderMark.UTF_16BE, ByteOrderMark.UTF_32LE, ByteOrderMark.UTF_32BE).get ();
        Charset charset;
        if (!bomInputStream.hasBOM ())
            charset = StandardCharsets.UTF_8;
        else if (bomInputStream.hasBOM (ByteOrderMark.UTF_8))
            charset = StandardCharsets.UTF_8;
        else if (bomInputStream.hasBOM (ByteOrderMark.UTF_16LE))
            charset = StandardCharsets.UTF_16LE;
        else if (bomInputStream.hasBOM (ByteOrderMark.UTF_16BE))
            charset = StandardCharsets.UTF_16BE;
        else
            throw new IOException ("The charset is not supported.");

        return new InputStreamReader (bomInputStream, charset);
    }
}
