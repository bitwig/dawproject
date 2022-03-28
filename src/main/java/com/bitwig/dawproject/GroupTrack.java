package com.bitwig.dawproject;

import java.util.ArrayList;
import java.util.List;

import jakarta.xml.bind.annotation.XmlElementRef;
import jakarta.xml.bind.annotation.XmlRootElement;

/** A hierarchical grouping of tracks (group track).
 * */
@XmlRootElement(name = "group-track")
public class GroupTrack extends Track
{
   @XmlElementRef(name = "tracks")
   public List<TrackOrFolder> tracks = new ArrayList<>();
}
