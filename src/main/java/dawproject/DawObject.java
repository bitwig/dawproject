package dawproject;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class DawObject
{
   public Meta meta;

   @JacksonXmlProperty(isAttribute = true)
   public int ID;
}
