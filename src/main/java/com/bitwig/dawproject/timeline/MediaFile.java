package com.bitwig.dawproject.timeline;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;

import com.bitwig.dawproject.FileReference;


/**
 * A media file. (audio or video).
 *
 * <p>
 * The duration attribute is intended to be provide the file length (and not be interpreted as a
 * playback parameter, use a Clip or Warps element for that).
 */
public class MediaFile extends Timeline
{
    /** The media file. */
    @XmlElement(name = "File", required = true)
    public FileReference file = new FileReference ();

    /** Duration in seconds of the media file (as stored). */
    @XmlAttribute(required = true)
    public double        duration;
}
