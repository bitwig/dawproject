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

   @XmlElement(name = "Original Artist")
   public String originalArtist;

   @XmlElement(name = "Writer")
   public String writer;

   @XmlElement(name = "Producer")
   public String producer;

   @XmlElement(name = "Year")
   public String year;

   @XmlElement(name = "Genre")
   public String genre;

   @XmlElement(name = "Copyright")
   public String copyright;

   @XmlElement(name = "Comment")
   public String comment;
}
