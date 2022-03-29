package com.bitwig.dawproject;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class LoadDawProjectTest
{
   public LoadDawProjectTest(final File file, @SuppressWarnings("unused") final Object name)
   {
      mFile = file;
   }

   @Parameterized.Parameters(name = "{1}")
   public static Collection<Object[]> getFiles() throws IOException
   {
      final List<Object[]> result = new ArrayList<>();

      final File testDataDir = new File("src/test-data");

      if (testDataDir.exists () && testDataDir.isDirectory())
      {
         final Path rootPath = testDataDir.toPath();
         Files.walkFileTree(rootPath, new FileVisitor<>()
         {
            @Override
            public FileVisitResult preVisitDirectory(final Path dir, final BasicFileAttributes attrs)
            {
               return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile(final Path path, final BasicFileAttributes attrs)
            {
               final File file = path.toFile();

               if (file.getAbsolutePath().toLowerCase().endsWith("." + DawProject.FILE_EXTENSION))
               {
                  final Object[] args = {file, rootPath.relativize(path).toString()};
                  result.add(args);
               }
               return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFileFailed(final Path file, final IOException exc)
            {
               return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(final Path dir, final IOException exc)
            {
               return FileVisitResult.CONTINUE;
            }
         });
      }

      return result;
   }

   @Test
   public void loadProject() throws IOException
   {
      final Project project = DawProject.loadProject(mFile);
      Assert.assertNotNull(project);
   }

   @Test
   public void loadMetadata() throws IOException
   {
      final Metadata metadata = DawProject.loadMetadata(mFile);
      Assert.assertNotNull(metadata);
   }

   private final File mFile;
}
