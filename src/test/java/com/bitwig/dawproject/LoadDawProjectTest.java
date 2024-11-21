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

/** Parameterized class to test loading a metadata and project files. */
@RunWith(Parameterized.class)
public class LoadDawProjectTest {
	private final File mFile;

	/**
	 * Constructor.
	 *
	 * @param file
	 *            The file to load
	 * @param name
	 *            Unused
	 */
	public LoadDawProjectTest(final File file, @SuppressWarnings("unused") final Object name) {
		this.mFile = file;
	}

	/**
	 * Get all files.
	 *
	 * @return The files
	 * @throws IOException
	 *             Could not load the files
	 */
	@Parameterized.Parameters(name = "{1}")
	public static Collection<Object[]> getFiles() throws IOException {
		final List<Object[]> result = new ArrayList<>();

		final File testDataDir = new File("src/test-data");

		if (testDataDir.exists() && testDataDir.isDirectory()) {
			final Path rootPath = testDataDir.toPath();
			Files.walkFileTree(rootPath, new FileVisitor<>() {
				@Override
				public FileVisitResult preVisitDirectory(final Path dir, final BasicFileAttributes attrs) {
					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult visitFile(final Path path, final BasicFileAttributes attrs) {
					final File file = path.toFile();

					if (file.getAbsolutePath().toLowerCase().endsWith("." + DawProject.FILE_EXTENSION)) {
						final Object[] args = {file, rootPath.relativize(path).toString()};
						result.add(args);
					}
					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult visitFileFailed(final Path file, final IOException exc) {
					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult postVisitDirectory(final Path dir, final IOException exc) {
					return FileVisitResult.CONTINUE;
				}
			});
		}

		return result;
	}

	/**
	 * Load the project.
	 *
	 * @throws IOException
	 *             Could not load the project
	 */
	@Test
	public void loadProject() throws IOException {
		final Project project = DawProject.loadProject(this.mFile);
		Assert.assertNotNull(project);
	}

	/**
	 * Load the metadata.
	 *
	 * @throws IOException
	 *             Could not load the metadata
	 */
	@Test
	public void loadMetadata() throws IOException {
		final MetaData metadata = DawProject.loadMetadata(this.mFile);
		Assert.assertNotNull(metadata);
	}
}
