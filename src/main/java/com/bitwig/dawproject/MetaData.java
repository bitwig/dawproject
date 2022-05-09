package com.bitwig.dawproject;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "MetaData")
public class MetaData
{
   @XmlElement(name = "Title")
   public String title;

   @XmlElement(name = "Artist")
   public String artist;

   @XmlElement(name = "Album")
   public String album;

   @XmlElement(name = "OriginalArtist")
   public String originalArtist;

   @XmlElement(name = "Composer")
   public String composer;

   @XmlElement(name = "Songwriter")
   public String songwriter;

   @XmlElement(name = "Producer")
   public String producer;

   @XmlElement(name = "Arranger")
   public String arranger;

   @XmlElement(name = "Year")
   public String year;

   @XmlElement(name = "Genre")
   public String genre;

   @XmlElement(name = "Copyright")
   public String copyright;

   @XmlElement(name = "Website")
   public String website;

   @XmlElement(name = "Comment")
   public String comment;
}
