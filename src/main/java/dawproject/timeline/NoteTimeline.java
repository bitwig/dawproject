package dawproject.timeline;

import java.util.ArrayList;
import java.util.List;

import jakarta.xml.bind.annotation.XmlElementRef;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class NoteTimeline extends Timeline
{
   @XmlElementWrapper(name="notes")
   @XmlElementRef
   public List<Note> notes = new ArrayList<>();
}
