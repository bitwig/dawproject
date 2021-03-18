package dawproject;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import dawproject.timeline.Timebase;

public class Meta
{
   @JacksonXmlProperty(isAttribute = true)
   public String title;

   @JacksonXmlProperty(isAttribute = true)
   public String color;

   @JacksonXmlProperty(isAttribute = true)
   public String comment;

}
