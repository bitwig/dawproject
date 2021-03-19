package dawproject.device;

import dawproject.Referencable;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;

import java.util.List;

import dawproject.Parameter;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlSeeAlso;

@XmlRootElement
@XmlSeeAlso({Vst2Plugin.class, Vst3Plugin.class, BuiltinDevice.class, AuPlugin.class})
public class Device extends Referencable
{
   /** this device is enabled (as in not bypassed) */
   @XmlAttribute
   public boolean enabled = true;

   @XmlAttribute
   public boolean loaded = true;

   /** Name of the device/plugin */
   @XmlAttribute
   public String deviceName;

   /** Vendor name of the device/plugin */
   @XmlAttribute
   public String deviceVendor;

   /** Relative path to a file representing the device / plug-in state in its native format */
   @XmlAttribute
   public String state;

   /** Parameters for this device, which is required for automated parameters in order to provide an ID. */
   @XmlElementWrapper(name="parameters")
   @XmlElement
   public List<Parameter> automatedParameters;
}
