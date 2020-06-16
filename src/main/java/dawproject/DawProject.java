package dawproject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.jsonSchema.JsonSchema;
import com.fasterxml.jackson.module.jsonSchema.JsonSchemaGenerator;

public class DawProject
{
   public static final String FORMAT_NAME = "DAW-project exchange format";
   public static final String FILE_EXTENSION = "dawproject";

   private static final String PROJECT_FILE = "project.json";
   private static final String METADATA_FILE = "metadata.json";

   public static void exportSchema(File file, Class cls) throws IOException
   {
      ObjectMapper mapper = createObjectMapper();

      JsonSchemaGenerator schemaGen = new JsonSchemaGenerator(mapper);
      JsonSchema schema = schemaGen.generateSchema(cls);

      FileOutputStream fileOutputStream = new FileOutputStream(file);
      fileOutputStream.write(toJson(schema).getBytes(StandardCharsets.UTF_8));
      fileOutputStream.close();
   }

   private static ObjectMapper createObjectMapper()
   {
      ObjectMapper objectMapper = new ObjectMapper();
      return objectMapper;
   }

   private static String toJson(Object object) throws JsonProcessingException
   {
      ObjectMapper objectMapper = createObjectMapper();
      objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
      return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
   }

   private static <T extends Object> T fromJson(InputStreamReader reader, Class<T> cls) throws IOException
   {
      ObjectMapper objectMapper = createObjectMapper();
      return objectMapper.readValue(reader, cls);
   }

   public static void saveJson(Project project, File file) throws IOException
   {
      String projectJson = toJson(project);
      FileOutputStream fileOutputStream = new FileOutputStream(file);
      fileOutputStream.write(projectJson.getBytes(StandardCharsets.UTF_8));
      fileOutputStream.close();
   }

   public static void save(Project project, Metadata metadata, Map<File, String> embeddedFiles, File file) throws IOException
   {
      String metadataJson = toJson(metadata);
      String projectJson = toJson(project);

      final ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(file));

      addToZip(zos, METADATA_FILE, metadataJson.getBytes(StandardCharsets.UTF_8));
      addToZip(zos, PROJECT_FILE, projectJson.getBytes(StandardCharsets.UTF_8));

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

      Project project = fromJson(new InputStreamReader(zipFile.getInputStream(projectEntry)), Project.class);

      zipFile.close();

      return project;
   }

   public static Metadata loadMetadata(final File file) throws IOException
   {
      ZipFile zipFile = new ZipFile(file);

      ZipEntry entry = zipFile.getEntry(METADATA_FILE);

      Metadata metadata = fromJson(new InputStreamReader(zipFile.getInputStream(entry)), Metadata.class);

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
