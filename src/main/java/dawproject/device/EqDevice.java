package dawproject.device;

import java.util.List;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class EqDevice extends BuiltinDevice
{
   @XmlElementWrapper(name="bands")
   @XmlElement(name="band")
   public List<EqBand> bands;
}
