package com.bitwig.dawproject;

import java.util.ArrayList;
import java.util.List;

import jakarta.xml.bind.annotation.XmlElementRef;
import jakarta.xml.bind.annotation.XmlRootElement;

/** A hierarchical grouping of tracks (folder/group track).
 *  If there is a designated master track for the folder structure,
 *  it should have it's mixerRole set to master.
 * */
@XmlRootElement(name = "folder")
public class FolderTrack extends TrackOrFolder
{
   @XmlElementRef
   public List<TrackOrFolder> tracks = new ArrayList<>();
}
