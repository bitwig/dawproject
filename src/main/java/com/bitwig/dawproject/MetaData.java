package com.bitwig.dawproject;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

/**
 * Metadata root element of the DAWPROJECT format. This is stored in the file
 * metadata.xml file inside the container.
 */

@XmlRootElement(name = "MetaData")
public class MetaData {
	/** Title of the song/project. */
	@XmlElement(name = "Title")
	public String title;

	/** Recording Artist. */
	@XmlElement(name = "Artist")
	public String artist;

	/** Album. */
	@XmlElement(name = "Album")
	public String album;

	/** Original Artist. */
	@XmlElement(name = "OriginalArtist")
	public String originalArtist;

	/** Composer. */
	@XmlElement(name = "Composer")
	public String composer;

	/** Songwriter. */
	@XmlElement(name = "Songwriter")
	public String songwriter;

	/** Producer. */
	@XmlElement(name = "Producer")
	public String producer;

	/** Arranger. */
	@XmlElement(name = "Arranger")
	public String arranger;

	/** Year this project/song was recorded. */
	@XmlElement(name = "Year")
	public String year;

	/** Genre/style */
	@XmlElement(name = "Genre")
	public String genre;

	/** Copyright notice. */
	@XmlElement(name = "Copyright")
	public String copyright;

	/** URL to website related to this project. */
	@XmlElement(name = "Website")
	public String website;

	/** General comment or description. */
	@XmlElement(name = "Comment")
	public String comment;
}
