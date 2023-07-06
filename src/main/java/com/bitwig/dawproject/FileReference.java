package com.bitwig.dawproject;

import jakarta.xml.bind.annotation.XmlAttribute;

/** References a file either within a DAWPROJECT container or on disk. */

public class FileReference
{
   /** File path. either
    * <li>path within the container</li>
    * <li>relative to .dawproject file (when external = "true")</li>
    * <li>absolute path (when external = "true" and path starts with a slash or windows drive letter)</li>
    * */
   @XmlAttribute(required = true)
   public String path;

   /** When true, the path is relative to the .dawproject file. Default value is false. */
   @XmlAttribute(required = false)
   public Boolean external;
}
