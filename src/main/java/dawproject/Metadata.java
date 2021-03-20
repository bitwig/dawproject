package dawproject;

import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Metadata")
public class Metadata
{
   public String title;
   public String artist;
   public String originalArtist;
   public String writer;
   public String producer;
   public String year;
   public String genre;
   public String copyright;
   public String comment;
}
