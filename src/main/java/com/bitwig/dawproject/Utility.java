package com.bitwig.dawproject;

import java.util.Collections;
import java.util.Set;

import com.bitwig.dawproject.timeline.Audio;
import com.bitwig.dawproject.timeline.Clip;
import com.bitwig.dawproject.timeline.Clips;
import com.bitwig.dawproject.timeline.Marker;
import com.bitwig.dawproject.timeline.RealPoint;
import com.bitwig.dawproject.timeline.TimeUnit;
import com.bitwig.dawproject.timeline.Timeline;
import com.bitwig.dawproject.timeline.Warp;


/**
 * Helper class to create different DAWproject objects.
 */
public class Utility
{
    /**
     * Private constructor due to utility class.
     */
    private Utility ()
    {
        // Intentionally empty
    }


    /**
     * Creates a track.
     * 
     * @param name The name of the track
     * @param contentTypes The content types that can be placed on the track
     * @param mixerRole The mixer role of the track
     * @param volume The volume setting of the track
     * @param pan The panorama setting of the track
     * @return The track instance
     */
    public static Track createTrack (final String name, final Set<ContentType> contentTypes, final MixerRole mixerRole, final double volume, final double pan)
    {
        final Track track = new Track ();
        track.channel = new Channel ();
        track.name = name;
        track.channel.volume = createRealParameter (Unit.LINEAR, volume);
        track.channel.pan = createRealParameter (Unit.NORMALIZED, pan);
        track.contentType = contentTypes.toArray (new ContentType [] {});
        track.channel.role = mixerRole;
        return track;
    }


    /**
     * Creates an audio media file.
     * 
     * @param relativePath The relative path to the audio file
     * @param sampleRate The sample rate
     * @param channels The number of channels
     * @param duration The duration in seconds
     * @return The audio instance
     */
    public static Audio createAudio (final String relativePath, final int sampleRate, final int channels, final double duration)
    {
        final var audio = new Audio ();
        audio.timeUnit = TimeUnit.SECONDS;
        audio.file = new FileReference ();
        audio.file.path = relativePath;
        audio.file.external = Boolean.FALSE;
        audio.sampleRate = sampleRate;
        audio.channels = channels;
        audio.duration = duration;
        return audio;
    }


    /**
     * Creates a warp.
     * 
     * @param time The warped time
     * @param contentTime The time of the content
     * @return The warp instance
     */
    public static Warp createWarp (final double time, final double contentTime)
    {
        final var warp = new Warp ();
        warp.time = time;
        warp.contentTime = contentTime;
        return warp;
    }


    /**
     * Creates a clip.
     * 
     * @param content The content of the clip
     * @param time The time position of the clip
     * @param duration The duration of the clip
     * @return The clip instance
     */
    public static Clip createClip (final Timeline content, final double time, final double duration)
    {
        final var clip = new Clip ();
        clip.content = content;
        clip.time = time;
        clip.duration = Double.valueOf (duration);
        return clip;
    }


    /**
     * Creates a Clips object from several clips.
     * 
     * @param clips The clips to wrap
     * @return The Clips instance
     */
    public static Clips createClips (final Clip... clips)
    {
        final var timeline = new Clips ();
        Collections.addAll (timeline.clips, clips);
        return timeline;
    }


    /**
     * Creates a marker.
     * 
     * @param time The time position of the marker
     * @param name The name of the marker
     * @return The marker instance
     */
    public static Marker createMarker (final double time, final String name)
    {
        final var markerEvent = new Marker ();
        markerEvent.time = time;
        markerEvent.name = name;
        return markerEvent;
    }


    /**
     * Creates a point for an automation envelope.
     * 
     * @param time The time position of the point
     * @param value The value of the point
     * @param interpolation The interpolation to use
     * @return The point instance
     */
    public static RealPoint createPoint (final double time, final double value, final Interpolation interpolation)
    {
        final var point = new RealPoint ();
        point.time = Double.valueOf (time);
        point.value = Double.valueOf (value);
        point.interpolation = interpolation;
        return point;
    }


    /**
     * Create a real parameter instance.
     *
     * @param unit The unit of the parameter
     * @param value The value of the parameter
     * @return The parameter
     */
    public static RealParameter createRealParameter (final Unit unit, final double value)
    {
        final RealParameter param = new RealParameter ();
        param.unit = unit;
        param.value = Double.valueOf (value);
        return param;
    }


    /**
     * Create a real parameter instance.
     *
     * @param unit The unit of the parameter
     * @param min The minimum value of the parameter
     * @param max The maximum value of the parameter
     * @param value The value of the parameter
     * @return The parameter
     */
    public static RealParameter createRealParameter (final Unit unit, final double min, final double max, final double value)
    {
        final RealParameter param = createRealParameter (unit, value);
        param.min = Double.valueOf (min);
        param.max = Double.valueOf (max);
        return param;
    }
}
