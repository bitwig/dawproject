package dawproject;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;

@XmlEnum
public enum ChannelRole
{
   @XmlEnumValue("regular") regular,
   @XmlEnumValue("return") returnTrack,
   @XmlEnumValue("master") master,
}
