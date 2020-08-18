package dawproject.importer;

import java.io.File;

import dawproject.Metadata;
import dawproject.Project;

public interface Importer
{
   public Project importProject(final File file);

   public Metadata importMetadata(final File file);
}
