package dawproject;

import jakarta.xml.bind.annotation.XmlAttribute;

public class BoolParameter extends Parameter
{
   @XmlAttribute
   public boolean value;
}
