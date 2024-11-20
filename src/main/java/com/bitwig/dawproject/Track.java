package com.bitwig.dawproject;

import java.util.ArrayList;
import java.util.List;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlList;
import jakarta.xml.bind.annotation.XmlRootElement;


/** Represents a sequencer track. */
@XmlRootElement(name = "Track")
public class Track extends Lane
{
    /**
     * Role of this track in timelines & arranger. Can be multiple (comma-separated).
     */
    @XmlAttribute(required = false)
    @XmlList()
    public ContentType [] contentType;

    /** If this track is loaded/active of not. */
    @XmlAttribute(required = false)
    public Boolean        loaded;

    /** Mixer channel used for the output of this track. */
    @XmlElement(name = "Channel", required = false)
    public Channel        channel;

    /**
     * Child tracks, typically used to represent group/folder tracks with contentType="tracks".
     */
    @XmlElement(name = "Track")
    public List<Track>    tracks = new ArrayList<> ();
}
