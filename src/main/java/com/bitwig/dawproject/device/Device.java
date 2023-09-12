package com.bitwig.dawproject.device;

import com.bitwig.dawproject.BoolParameter;
import com.bitwig.dawproject.FileReference;
import com.bitwig.dawproject.Referenceable;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;

import java.util.ArrayList;
import java.util.List;

import com.bitwig.dawproject.Parameter;
import jakarta.xml.bind.annotation.XmlElementRef;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlSeeAlso;

/** Either a Plug-in or native Device with in a DAW. */
@XmlRootElement(name = "Device")
@XmlSeeAlso({Vst2Plugin.class, Vst3Plugin.class, ClapPlugin.class, BuiltinDevice.class, AuPlugin.class, Parameter.class})
public class Device extends Referenceable
{
   /** This device is enabled (as in not bypassed). */
   @XmlElement(name = "Enabled")
   public BoolParameter enabled;

   /** Role of this device/plug-in. */
   @XmlAttribute(required = true)
   public DeviceRole deviceRole;

   /** If this device/plug-in is loaded/active of not. */
   @XmlAttribute
   public Boolean loaded = true;

   /** Name of the device/plugin */
   @XmlAttribute(required = true)
   public String deviceName;

   /** Unique identifier of device/plug-in.<br/>
    * Standards which use UUID as an identifier use the canonical textual representation of the UUID (8-4-4-4-12 with no braces) (VST3)<br/>
    * Standards which use an integer as an identifier use the value in decimal form. (base-10 unsigned) (VST2)<br/>
    * Text-based identifiers are used as-is. (CLAP) */
   @XmlAttribute
   public String deviceID;

   /** Vendor name of the device/plugin */
   @XmlAttribute
   public String deviceVendor;

   /** Path to a file representing the device / plug-in state in its native format.
    * <p>This file must be embedded inside the container ZIP and have the FileReference configured with (external=false).</p> */
   @XmlElement(name = "State", required = false)
   public FileReference state;

   /** Parameters for this device, which is required for automated parameters in order to provide an ID. <br/>
    * Note: If the automated parameter is already present like the BuiltinDevice parameters, it should not be included here as well. */
   @XmlElementWrapper(name="Parameters", required = false)
   @XmlElementRef
   public List<Parameter> automatedParameters = new ArrayList<>();
}
