package com.bitwig.dawproject.timeline;

import java.util.ArrayList;
import java.util.List;

import jakarta.xml.bind.annotation.XmlElementRef;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Clips")
public class Clips extends Timeline
{
   @XmlElementRef(name = "Clip")
   public List<Clip> clips = new ArrayList<>();
}
