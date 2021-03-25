package com.bitwig.dawproject.timeline;

import java.util.ArrayList;
import java.util.List;

import jakarta.xml.bind.annotation.XmlElementRef;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Clips extends Timeline
{
   @XmlElementRef(name = "clip")
   public List<Clip> clips = new ArrayList<>();
}
