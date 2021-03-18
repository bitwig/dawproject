package dawproject.device;

import dawproject.DawObject;

public class Device extends DawObject
{

   /** this device is enabled (as in not bypassed) */
   public boolean enabled = true;

   public boolean loaded = true;

   /** Name of the device/plugin */
   public String deviceName;

   /** Vendor name of the device/plugin */
   public String deviceVendor;

   /** ID */
   public String id;

   /** Plug-in format used by this plug-in (or builtin) */
   public DeviceFormat deviceFormat;

   /** Relative path to a file representing the device / plug-in state in its native format */
   public String stateFile;
}
