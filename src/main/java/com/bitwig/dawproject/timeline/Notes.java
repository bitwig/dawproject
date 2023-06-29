package com.bitwig.dawproject.timeline;

import java.util.ArrayList;
import java.util.List;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementRef;
import jakarta.xml.bind.annotation.XmlRootElement;

/** Timeline containing Notes (MIDI-style) */

@XmlRootElement(name = "Notes")
public class Notes extends Timeline
{
   /** Contained notes. */
   @XmlElement(required = true, name = "Note")
   public List<Note> notes = new ArrayList<>();
}
