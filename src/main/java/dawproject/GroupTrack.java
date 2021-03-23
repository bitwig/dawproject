package dawproject;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

/** A folder track which has a dedicated master track which represents the group on the outside. */
@XmlRootElement(name = "group")
public class GroupTrack extends FolderTrack
{
   /** Master track of this group. */
   @XmlElement(required = true)
   public Track master;
}
