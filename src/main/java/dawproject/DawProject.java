package dawproject;

import javax.xml.transform.Result;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.SchemaOutputResolver;

public class DawProject
{
   public static final String FORMAT_NAME = "DAW-project exchange format";
   public static final String FILE_EXTENSION = "dawproject";

   private static final String PROJECT_FILE = "project.xml";
   private static final String METADATA_FILE = "metadata.xml";

   public static void exportSchema(File file, Class cls) throws IOException
   {
      try
      {
         var context = createContext(cls);

         var resolver = new SchemaOutputResolver()
         {
            @Override public Result createOutput (String namespaceUri, String suggestedFileName) throws IOException
            {
               FileOutputStream fileOutputStream = new FileOutputStream(file);
               StreamResult result = new StreamResult(fileOutputStream);
               result.setSystemId(file.getName());
               return result;
            }
         };

         context.generateSchema(resolver);
      }
      catch (JAXBException e)
      {
         throw new IOException(e);
      }
   }

   private static String toXML(Object object) throws IOException
   {
      try
      {
         var context = createContext(object.getClass());

         var marshaller = context.createMarshaller();
         marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

         var sw = new StringWriter();
         marshaller.marshal(object, sw);

         return sw.toString();
      }
      catch (Exception e)
      {
         throw new IOException(e);
      }
   }

   private static JAXBContext createContext(final Class cls) throws JAXBException
   {
      return JAXBContext.newInstance(cls);
   }

   private static <T extends Object> T fromXML(InputStreamReader reader, Class<T> cls) throws IOException
   {
      try
      {
         var jaxbContext = JAXBContext.newInstance(cls);

         final var unmarshaller = jaxbContext.createUnmarshaller();

         final var object = (T)unmarshaller.unmarshal(reader);

         return object;
      }
      catch (JAXBException e)
      {
         throw new IOException(e);
      }
   }

   public static void saveXML(Project project, File file) throws IOException
   {
      String projectXML = toXML(project);
      FileOutputStream fileOutputStream = new FileOutputStream(file);
      fileOutputStream.write(projectXML.getBytes(StandardCharsets.UTF_8));
      fileOutputStream.close();
   }

   public static void save(Project project, Metadata metadata, Map<File, String> embeddedFiles, File file) throws IOException
   {
      String metadataXML = toXML(metadata);
      String projectXML = toXML(project);

      final ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(file));

      addToZip(zos, METADATA_FILE, metadataXML.getBytes(StandardCharsets.UTF_8));
      addToZip(zos, PROJECT_FILE, projectXML.getBytes(StandardCharsets.UTF_8));

      for (Map.Entry<File, String> entry : embeddedFiles.entrySet())
      {
         addToZip(zos, entry.getValue(), entry.getKey());
      }

      zos.close();
   }

   private static void addToZip(
      final ZipOutputStream zos,
      final String path,
      final byte[] data) throws IOException
   {
      final ZipEntry entry = new ZipEntry(path);
      zos.putNextEntry(entry);
      zos.write(data);
      zos.closeEntry();
   }

   private static void addToZip(
      final ZipOutputStream zos,
      final String path,
      final File file) throws IOException
   {
      final ZipEntry entry = new ZipEntry(path);
      zos.putNextEntry(entry);

      FileInputStream fileInputStream = new FileInputStream(file);

      byte[] data = new byte[65536];
      int size = 0;
      while((size = fileInputStream.read(data)) != -1)
         zos.write(data, 0, size);

      zos.flush();
      fileInputStream.close();

      zos.closeEntry();
   }

   public static Project loadProject(final File file) throws IOException
   {
      ZipFile zipFile = new ZipFile(file);

      ZipEntry projectEntry = zipFile.getEntry(PROJECT_FILE);

      Project project = fromXML(new InputStreamReader(zipFile.getInputStream(projectEntry)), Project.class);

      zipFile.close();

      return project;
   }

   public static Metadata loadMetadata(final File file) throws IOException
   {
      ZipFile zipFile = new ZipFile(file);

      ZipEntry entry = zipFile.getEntry(METADATA_FILE);

      Metadata metadata = fromXML(new InputStreamReader(zipFile.getInputStream(entry)), Metadata.class);

      zipFile.close();

      return metadata;
   }

   public static InputStream streamEmbedded(final File file, final String embeddedPath) throws IOException
   {
      ZipFile zipFile = new ZipFile(file);

      ZipEntry entry = zipFile.getEntry(embeddedPath);

      InputStream inputStream = zipFile.getInputStream(entry);

      return inputStream;
   }
}
