package dawproject.device;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

import java.util.List;

import dawproject.DawObject;
import dawproject.Parameter;

public class Device extends DawObject
{
   /** this device is enabled (as in not bypassed) */
   @XmlAttribute
   public boolean enabled = true;

   @XmlAttribute
   public boolean loaded = true;

   /** Name of the device/plugin */
   @XmlAttribute
   public String name;

   /** Vendor name of the device/plugin */
   @XmlAttribute
   public String deviceVendor;

   /** Plug-in format used by this plug-in (or builtin) */
   @XmlAttribute
   public DeviceFormat deviceFormat;

   /** Relative path to a file representing the device / plug-in state in its native format */
   @XmlAttribute
   public String stateFile;

   /** Parameters for this device, which is required for automated parameters in order to provide an ID. */
   @XmlElement()
   public List<Parameter> parameters;
}
