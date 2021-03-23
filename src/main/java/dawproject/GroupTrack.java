package dawproject;

import jakarta.xml.bind.annotation.XmlElementRef;
import jakarta.xml.bind.annotation.XmlRootElement;

/** A folder track which has a dedicated master track which represents the group on the outside. */
@XmlRootElement
public class GroupTrack extends FolderTrack
{
   /** Master track of this group. */
   @XmlElementRef(required = true)
   public Track master;
}
