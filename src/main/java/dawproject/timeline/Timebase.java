package dawproject.timeline;

import jakarta.xml.bind.annotation.XmlEnum;

@XmlEnum
public enum Timebase
{
   beats, // quarter-notes
   seconds;
}
