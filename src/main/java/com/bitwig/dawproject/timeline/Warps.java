package com.bitwig.dawproject.timeline;

import java.util.ArrayList;
import java.util.List;

import jakarta.xml.bind.annotation.XmlElementRef;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Warps")
public class Warps extends Timeline
{
   @XmlElementRef(required = true)
   public List<Warp> events = new ArrayList<>();

   @XmlElementRef(name = "Content", required = true)
   public Timeline content;
}
