package dawproject.timeline;

import java.util.ArrayList;
import java.util.List;

import jakarta.xml.bind.annotation.XmlElementRef;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Warps extends Timeline
{
   @XmlElementRef(required = true)
   public List<Warp> events = new ArrayList<>();

   @XmlElementRef(required = true)
   public Timeline content;
}
