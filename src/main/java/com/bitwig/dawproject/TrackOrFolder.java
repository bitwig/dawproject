package com.bitwig.dawproject;

import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlSeeAlso;

@XmlRootElement
@XmlSeeAlso({Track.class, TrackFolder.class, Channel.class, GroupTrack.class})
public abstract class TrackOrFolder extends Referenceable
{
}
