package com.bitwig.dawproject;

import java.util.ArrayList;
import java.util.List;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementRef;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlSeeAlso;

import com.bitwig.dawproject.device.Device;
import com.bitwig.dawproject.timeline.Timeline;

/**
 * The main root element of the DAWPROJECT format. This is stored in the file
 * project.xml file inside the container.
 */
@XmlRootElement(name = "Project")
@XmlSeeAlso({Device.class, Timeline.class})
public class Project {
	/** The version of the format. */
	public static final String CURRENT_VERSION = "1.0";

	/** Version of DAWPROJECT format this file was saved as. */
	@XmlAttribute(required = true)
	public String version = CURRENT_VERSION;

	/** Metadata (name/version) about the application that saved this file. */
	@XmlElement(name = "Application", required = true)
	public Application application = new Application();

	/**
	 * Transport element containing playback parameters such as Tempo and
	 * Time-signature.
	 */
	@XmlElement(name = "Transport")
	public Transport transport;

	/** Track/Channel structure of this file. */
	@XmlElementWrapper(name = "Structure")
	@XmlElementRef
	public List<Lane> structure = new ArrayList<>();

	/** The main Arrangement timeline of this file. */
	@XmlElement(name = "Arrangement", type = Arrangement.class, required = false)
	public Arrangement arrangement;

	/** Clip Launcher scenes of this file. */
	@XmlElementWrapper(name = "Scenes")
	@XmlElement(name = "Scene")
	public List<Scene> scenes = new ArrayList<>();
}
