package dawproject;

import jakarta.xml.bind.annotation.XmlAttribute;

public class BoolParameter extends Parameter
{
   public static BoolParameter create(final boolean v)
   {
      final var p = new BoolParameter();
      p.value = v;
      return p;
   }

   @XmlAttribute
   public boolean value;
}
