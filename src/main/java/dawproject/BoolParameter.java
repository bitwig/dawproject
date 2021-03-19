package dawproject;

import jakarta.xml.bind.annotation.XmlAttribute;

public class BoolParameter extends Parameter
{
   @XmlAttribute
   public boolean value;

   public BoolParameter(final boolean v)
   {
      value = value;
   }

   public BoolParameter()
   {
      value = false;
   }
}
