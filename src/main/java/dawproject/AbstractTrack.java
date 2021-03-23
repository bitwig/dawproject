package dawproject;

import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlSeeAlso;

@XmlRootElement
@XmlSeeAlso({Track.class, FolderTrack.class, GroupTrack.class})
public abstract class AbstractTrack extends Referencable
{
}
