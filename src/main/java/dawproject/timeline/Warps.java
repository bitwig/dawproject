package dawproject.timeline;

import java.util.ArrayList;
import java.util.List;

import jakarta.xml.bind.annotation.XmlElementRef;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Warps extends Timeline
{
   @XmlElementRef
   public List<Warp> events = new ArrayList<>();
}
