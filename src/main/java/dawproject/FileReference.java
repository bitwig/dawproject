package dawproject;

import jakarta.xml.bind.annotation.XmlAttribute;

public class FileReference
{
   /**
    * Relative path. either
    * 1) within the container
    * 2) relative to .dawproject file (when isExternal = "true")
    * */
   @XmlAttribute(required = true)
   public String path;

   @XmlAttribute(required = false)
   public Boolean isExternal;
}
