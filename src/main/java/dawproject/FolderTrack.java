package dawproject;

import java.util.ArrayList;
import java.util.List;

import jakarta.xml.bind.annotation.XmlElementRef;
import jakarta.xml.bind.annotation.XmlRootElement;

/** A hierarchical grouping of tracks (folder track) */
@XmlRootElement(name = "folder")
public class FolderTrack extends AbstractTrack
{
   @XmlElementRef
   public List<AbstractTrack> tracks = new ArrayList<>();
}
