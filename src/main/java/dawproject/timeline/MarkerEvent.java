package dawproject.timeline;

import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "cue-marker")
public class MarkerEvent extends Event
{
   public static MarkerEvent create(double time, String name)
   {
      final var markerEvent = new MarkerEvent();
      markerEvent.time = time;
      markerEvent.name = name;
      return markerEvent;
   }
}
