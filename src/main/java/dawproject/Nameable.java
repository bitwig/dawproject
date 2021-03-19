package dawproject;

import jakarta.xml.bind.annotation.XmlAttribute;

public class Nameable extends Referencable
{
   @XmlAttribute
   public String name;

   @XmlAttribute
   public String color;

   @XmlAttribute
   public String comment;
}
