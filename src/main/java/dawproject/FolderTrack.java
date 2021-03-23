package dawproject;

import java.util.ArrayList;
import java.util.List;

import jakarta.xml.bind.annotation.XmlElementRef;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlRootElement;

/** A hierarchical grouping of tracks (folder track) */
@XmlRootElement
public class FolderTrack extends AbstractTrack
{
   @XmlElementWrapper(name="tracks")
   @XmlElementRef
   public List<AbstractTrack> tracks = new ArrayList<>();
}
