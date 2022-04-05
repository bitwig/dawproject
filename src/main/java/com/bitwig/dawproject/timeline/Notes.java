package com.bitwig.dawproject.timeline;

import java.util.ArrayList;
import java.util.List;

import jakarta.xml.bind.annotation.XmlElementRef;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Notes")
public class Notes extends Timeline
{
   @XmlElementRef(required = true)
   public List<Note> notes = new ArrayList<>();
}
