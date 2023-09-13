package com.bitwig.dawproject;

import java.util.Collections;
import java.util.EnumSet;

import com.bitwig.dawproject.timeline.Audio;
import com.bitwig.dawproject.timeline.Clip;
import com.bitwig.dawproject.timeline.Clips;
import com.bitwig.dawproject.timeline.TimeUnit;
import com.bitwig.dawproject.timeline.Timeline;
import com.bitwig.dawproject.timeline.Warp;

public class Utility
{
   public static Track createTrack(final String name, final EnumSet<ContentType> contentTypes, final MixerRole mixerRole, final double volume, final double pan)
   {
      final Track track = new Track();
      track.channel = new Channel();
      track.name = name;
      final var volumeParameter = new RealParameter();
      volumeParameter.value = volume;
      volumeParameter.unit = Unit.linear;
      track.channel.volume = volumeParameter;

      final var panParameter = new RealParameter();
      panParameter.value = pan;
      panParameter.unit = Unit.normalized;
      track.channel.pan = panParameter;

      track.contentType = contentTypes.toArray(new ContentType[]{});
      track.channel.role = mixerRole;

      return track;
   }

   public static Audio createAudio(final String relativePath, final int sampleRate, final int channels, final double duration)
   {
      final var audio = new Audio();
      audio.timeUnit = TimeUnit.seconds;
      audio.file = new FileReference();
      audio.file.path = relativePath;
      audio.file.external = false;
      audio.sampleRate = sampleRate;
      audio.channels = channels;
      audio.duration = duration;
      return audio;
   }

   public static Warp createWarp(final double time, final double contentTime)
   {
      final var warp = new Warp();
      warp.time = time;
      warp.contentTime = contentTime;
      return warp;
   }

   public static Clip createClip(final Timeline content, final double time, final double duration)
   {
      final var clip = new Clip();
      clip.content = content;
      clip.time = time;
      clip.duration = duration;
      return clip;
   }

   public static Clips createClips(final Clip... clips)
   {
      final var timeline = new Clips();
      Collections.addAll(timeline.clips, clips);

      return timeline;
   }
}
