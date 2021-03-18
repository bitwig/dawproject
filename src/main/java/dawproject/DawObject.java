package dawproject;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlID;

public class DawObject
{
   @XmlAttribute
   @XmlID
   public String id = "";

   @XmlAttribute
   public String title;

   @XmlAttribute
   public String color;

   @XmlAttribute
   public String comment;

   public DawObject()
   {
      id = Integer.toString(IDCOUNT++); // HACK
   }

   private static int IDCOUNT = 0;
}
