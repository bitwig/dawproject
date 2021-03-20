package dawproject.timeline;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Marker
{
   @XmlAttribute
   public double time;

   @XmlAttribute
   public String name;

   @XmlAttribute
   public String color;

   public static Marker create(double time, String name)
   {
      final var markerEvent = new Marker();
      markerEvent.time = time;
      markerEvent.name = name;
      return markerEvent;
   }
}
