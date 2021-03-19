package dawproject.timeline;

import java.util.ArrayList;
import java.util.List;

import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ClipTimeline extends Timeline
{
   public List<Clip> clips = new ArrayList<>();

}
