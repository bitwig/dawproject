package dawproject.timeline;

import java.util.ArrayList;
import java.util.List;

import jakarta.xml.bind.annotation.XmlElementRef;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Lanes extends Timeline
{
   /** Lanes representing nested content */

   @XmlElementRef
   public List<Timeline> lanes = new ArrayList<>();
}
