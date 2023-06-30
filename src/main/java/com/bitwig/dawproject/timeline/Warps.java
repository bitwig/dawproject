package com.bitwig.dawproject.timeline;

import java.util.ArrayList;
import java.util.List;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementRef;
import jakarta.xml.bind.annotation.XmlRootElement;

/**
 * <p>Warps the time of nested content as defined by a list of <i>Warp</i> points.</p>
 *
 * <p>A typical use case would be to warp an audio-file (contentTimeUnit = seconds) onto a timeline defined in beats
 * (timeUnit = beats) as defined by a set of Warp events.</p>
 *
 * <p>At least two Warp events need to present in order to define a usable beats/seconds conversion. For a plain
 * fixed-speed mapping, provide two event: One at (0,0) and a second event with the desired beat-time length along
 * with the length of the contained Audio file in seconds.</p>
 *
 * <pre>{@code
 *   <!-- example of a simple audio clip with beats-to-seconds warping -->
 *   <Clip time="0" duration="8">
 *     <Warps contentTimeUnit="seconds" timeUnit="beats">
 *       <Audio channels="1" duration="4.657" sampleRate="44100">
 *          <File path="samples/dummy.wav"/>
 *       </Audio>
 *       <Warp time="0" contentTime="0"/>
 *       <Warp time="8" contentTime="4.657"/>
 *     </Warps>
 *   </Clip>
 * }</pre>
 * */

@XmlRootElement(name = "Warps")
public class Warps extends Timeline
{
   /** Warp events defining the transformation. (minimum 2) */
   @XmlElement(required = true, name = "Warp")
   public List<Warp> events = new ArrayList<>();

   /**
    * Content timeline to be warped.
    */
   @XmlElementRef(name = "Content", required = true)
   public Timeline content;

   /**
    * The TimeUnit used by the content (nested) timeline and the contentTime attribute of the Warp events
    * */
   @XmlAttribute(required = true)
   public TimeUnit contentTimeUnit;
}
