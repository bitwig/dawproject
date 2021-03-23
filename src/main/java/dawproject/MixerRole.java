package dawproject;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;

@XmlEnum
public enum MixerRole
{
   @XmlEnumValue("regular") regular,
   @XmlEnumValue("master") master,
   @XmlEnumValue("return") returnTrack,
   @XmlEnumValue("aux") aux,
   @XmlEnumValue("vca") vca,
}
