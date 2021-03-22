package dawproject;

import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlSeeAlso;

@XmlRootElement
@XmlSeeAlso({RealParameter.class, BoolParameter.class, IntegerParameter.class, EnumParameter.class, TimeSignatureParameter.class})
public class Parameter extends Referencable
{
}
