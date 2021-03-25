package com.bitwig.dawproject.device;

import com.bitwig.dawproject.BoolParameter;
import com.bitwig.dawproject.FileReference;
import com.bitwig.dawproject.Referencable;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;

import java.util.ArrayList;
import java.util.List;

import com.bitwig.dawproject.Parameter;
import jakarta.xml.bind.annotation.XmlElementRef;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlSeeAlso;

@XmlRootElement
@XmlSeeAlso({Vst2Plugin.class, Vst3Plugin.class, BuiltinDevice.class, AuPlugin.class, Parameter.class})
public class Device extends Referencable
{
   /** this device is enabled (as in not bypassed) */
   @XmlElement
   public BoolParameter enabled;

   @XmlAttribute
   public Boolean loaded = true;

   /** Name of the device/plugin */
   @XmlAttribute
   public String deviceName;

   /** Unique identifier of device/plug-in
    * For standards which use UUID as an identifier use the textual representation of the UUID (VST3)
    * For standards which use an integer as an identifier use the value in decimal form. Base-10 unsigned. (VST2)
    * */
   @XmlAttribute
   public String deviceID;

   /** Vendor name of the device/plugin */
   @XmlAttribute
   public String deviceVendor;

   /** Path to a file representing the device / plug-in state in its native format */
   @XmlElement(required = false)
   public FileReference state;

   /** Parameters for this device, which is required for automated parameters in order to provide an ID. */
   @XmlElementWrapper(name="parameters", required = false)
   @XmlElementRef
   public List<Parameter> automatedParameters = new ArrayList<>();
}
