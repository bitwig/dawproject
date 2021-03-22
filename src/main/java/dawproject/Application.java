package dawproject;

import jakarta.xml.bind.annotation.XmlAttribute;

public class Application
{
   @XmlAttribute(required = true)
   public String name;

   @XmlAttribute(required = true)
   public String version;
}
