package dawproject.timeline;

import java.util.ArrayList;
import java.util.List;

import dawproject.Interpolation;
import dawproject.Unit;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementRef;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType(propOrder={"target","points","unit", "interpolation"})
public class Points extends Timeline
{
   @XmlElement(required = true)
   public AutomationTarget target = new AutomationTarget();

   @XmlElementRef(required = true)
   public List<Point> points = new ArrayList<>();

   @XmlAttribute(required = false)
   public Unit unit;

   @XmlAttribute(required = false)
   public Interpolation interpolation;
}
