package com.bitwig.dawproject;

import java.util.ArrayList;
import java.util.List;

import jakarta.xml.bind.annotation.XmlElementRef;
import jakarta.xml.bind.annotation.XmlRootElement;

/** A hierarchical grouping of tracks (folder/group track).
 * */
@XmlRootElement(name = "folder")
public class TrackFolder extends TrackOrFolder
{
   @XmlElementRef
   public List<TrackOrFolder> tracks = new ArrayList<>();
}
