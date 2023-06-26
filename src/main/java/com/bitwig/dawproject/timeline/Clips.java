package com.bitwig.dawproject.timeline;

import java.util.ArrayList;
import java.util.List;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Clips")
public class Clips extends Timeline
{
   @XmlElement(name = "Clip")
   public List<Clip> clips = new ArrayList<>();
}
