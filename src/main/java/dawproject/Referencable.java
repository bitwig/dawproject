package dawproject;

import jakarta.xml.bind.annotation.XmlAccessOrder;
import jakarta.xml.bind.annotation.XmlAccessorOrder;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlID;

@XmlAccessorOrder(XmlAccessOrder.ALPHABETICAL)
public class Referencable extends Nameable
{
   @XmlAttribute
   @XmlID()
   public String id = "";

   public void setID(int id)
   {
      this.id = Integer.toString(id);
   }
}
