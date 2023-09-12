package com.bitwig.dawproject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

import com.bitwig.dawproject.device.Device;
import com.bitwig.dawproject.device.DeviceRole;
import com.bitwig.dawproject.device.Vst3Plugin;
import com.bitwig.dawproject.timeline.Audio;
import com.bitwig.dawproject.timeline.RealPoint;
import com.bitwig.dawproject.timeline.Clip;
import com.bitwig.dawproject.timeline.Clips;
import com.bitwig.dawproject.timeline.Marker;
import com.bitwig.dawproject.timeline.Markers;
import com.bitwig.dawproject.timeline.Lanes;
import com.bitwig.dawproject.timeline.Note;
import com.bitwig.dawproject.timeline.Notes;
import com.bitwig.dawproject.timeline.Points;
import com.bitwig.dawproject.timeline.TimeUnit;
import com.bitwig.dawproject.timeline.Timeline;
import com.bitwig.dawproject.timeline.Warp;
import com.bitwig.dawproject.timeline.Warps;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

public class DawProjectTest
{
   enum Features
   {
      CUE_MARKERS,
      CLIPS,
      AUDIO,
      NOTES,
      AUTOMATION,
      ALIAS_CLIPS,
      PLUGINS
   }

   EnumSet<Features> simpleFeatures = EnumSet.of(Features.CLIPS, Features.NOTES, Features.AUDIO);

   private Project createEmptyProject()
   {
      Referenceable.resetID();
      final Project project = new Project();

      project.application.name = "Test";
      project.application.version = "1.0";
      return project;
   }

   private Track createTrack(final String name, final EnumSet<ContentType> contentTypes, final MixerRole mixerRole, final double volume, final double pan)
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

   private Audio createAudio(final String relativePath, final int sampleRate, final int channels, final double duration)
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

   private Warp createWarp(final double time, final double contentTime)
   {
      final var warp = new Warp();
      warp.time = time;
      warp.contentTime = contentTime;
      return warp;
   }

   private Clip createClip(final Timeline content, final double time, final double duration)
   {
      final var clip = new Clip();
      clip.content = content;
      clip.time = time;
      clip.duration = duration;
      return clip;
   }

   private Clips createClips(final Clip... clips)
   {
      final var timeline = new Clips();
      Collections.addAll(timeline.clips, clips);

      return timeline;
   }

   private Project createDummyProject(final int numTracks, final EnumSet<Features> features)
   {
      final Project project = createEmptyProject();

      final Track masterTrack = createTrack("Master", EnumSet.noneOf(ContentType.class), MixerRole.master, 1, 0.5);
      project.structure.add(masterTrack);

      if (features.contains(Features.PLUGINS))
      {
         final Device device = new Vst3Plugin();
         device.deviceName = "Limiter";
         //device.id = UUID.randomUUID().toString();
         device.deviceRole = DeviceRole.audioFX;
         device.state = new FileReference();
         device.state.path = "plugin-states/12323545.vstpreset";

         if (masterTrack.channel.devices == null)
            masterTrack.channel.devices = new ArrayList<>();

         masterTrack.channel.devices.add(device);
      }

      project.arrangement = new Arrangement();
      final var arrangementLanes = new Lanes();
      arrangementLanes.timeUnit = TimeUnit.beats;
      project.arrangement.lanes = arrangementLanes;

      if (features.contains(Features.CUE_MARKERS))
      {
         final var cueMarkers = new Markers();
         project.arrangement.markers = cueMarkers;
         cueMarkers.markers.add(createMarker(0, "Verse"));
         cueMarkers.markers.add(createMarker(24, "Chorus"));
      }

      for (int i = 0; i < numTracks; i++)
      {
         final var track = createTrack("Track " + (i+1), EnumSet.of(ContentType.notes), MixerRole.regular,1, 0.5);
         project.structure.add(track);
         track.color = "#" + i + i + i + i + i +i;
         track.channel.destination = masterTrack.channel;

         final var trackLanes = new Lanes();
         trackLanes.track = track;
         arrangementLanes.lanes.add(trackLanes);

         if (features.contains(Features.CLIPS))
         {
            final var clips = new Clips();

            trackLanes.lanes.add(clips);

            final var clip = new Clip();
            clip.name = "Clip " + i;
            clip.time = 8 * i;
            clip.duration = 4;
            clips.clips.add(clip);

            final var notes = new Notes();
            clip.content = notes;

            for (int j = 0; j < 8; j++)
            {
               final var note = new Note();
               note.key = 36 + 12 * (j % (1 + i));
               note.velocity = 0.8;
               note.releaseVelocity = 0.5;
               note.time = 0.5 * j;
               note.duration = 0.5;
               notes.notes.add(note);
            }

            if (features.contains(Features.ALIAS_CLIPS))
            {
               final var clip2 = new Clip();
               clip2.name = "Alias Clip " + i;
               clip2.time = 32 + 8 * i;
               clip2.duration = 4;
               clips.clips.add(clip2);
               clip2.reference = notes;
            }

            if (i == 0 && features.contains(Features.AUTOMATION))
            {
               final var points = new Points();
               points.target.parameter = track.channel.volume;
               trackLanes.lanes.add(points);

               // fade-in over 8 quarter notes
               points.points.add(createPoint(0.0, 0.0, Interpolation.linear));
               points.points.add(createPoint(8.0, 1.0, Interpolation.linear));
            }
         }
      }

      // Route channel 0 to 1
      //project.channels.get(0).destination = project.channels.get(1);

      return project;
   }

   private RealPoint createPoint(final double time, final double value, final Interpolation interpolation)
   {
      final var point = new RealPoint();
      point.time = time;
      point.value = value;
      point.interpolation = interpolation;
      return point;
   }

   public Marker createMarker(final double time, final String name)
   {
      final var markerEvent = new Marker();
      markerEvent.time = time;
      markerEvent.name = name;
      return markerEvent;
   }

   @Test
   public void saveDawProject() throws IOException
   {
      final Project project = createDummyProject(3, simpleFeatures);
      final MetaData metadata = new MetaData();

      final Map<File, String> embeddedFiles = new HashMap<>();
      DawProject.save(project, metadata, embeddedFiles, new File("target/test.dawproject"));
      DawProject.saveXML(project, new File("target/test.dawproject.xml"));
   }

   @Test
   public void validateDawProject() throws IOException
   {
      final Project project = createDummyProject(3, simpleFeatures);
      DawProject.validate(project);
   }

   @Test
   public void validateComplexDawProject() throws IOException
   {
      final Project project = createDummyProject(3, EnumSet.allOf(Features.class));
      DawProject.validate(project);
   }

   @Test
   public void saveAndLoadDawProject() throws IOException
   {
      final Project project = createDummyProject(5, simpleFeatures);
      final MetaData metadata = new MetaData();

      final var file = File.createTempFile("testfile", ".dawproject");
      final Map<File, String> embeddedFiles = new HashMap<>();
      DawProject.save(project, metadata, embeddedFiles, file);

      final var loadedProject = DawProject.loadProject(file);

      Assert.assertEquals(project.structure.size(), loadedProject.structure.size());
      Assert.assertEquals(project.scenes.size(), loadedProject.scenes.size());
   }

   @Test
   public void saveComplexDawProject() throws IOException
   {
      final Project project = createDummyProject(3, EnumSet.allOf(Features.class));
      final MetaData metadata = new MetaData();

      final Map<File, String> embeddedFiles = new HashMap<>();
      DawProject.save(project, metadata, embeddedFiles, new File("target/test-complex.dawproject"));
      DawProject.saveXML(project, new File("target/test-complex.dawproject.xml"));
   }

   @Test
   public void saveAndLoadComplexDawProject() throws IOException
   {
      final Project project = createDummyProject(5, EnumSet.allOf(Features.class));
      final MetaData metadata = new MetaData();

      final Map<File, String> embeddedFiles = new HashMap<>();
      final var file = File.createTempFile("testfile2", ".dawproject");
      DawProject.save(project, metadata, embeddedFiles, file);

      final var loadedProject = DawProject.loadProject(file);

      Assert.assertEquals(project.structure.size(), loadedProject.structure.size());
      Assert.assertEquals(project.scenes.size(), loadedProject.scenes.size());
      Assert.assertEquals(project.arrangement.lanes.getClass(), loadedProject.arrangement.lanes.getClass());
      Assert.assertEquals(project.arrangement.markers.getClass(), loadedProject.arrangement.markers.getClass());
   }

   @Test
   public void writeMetadataSchema() throws IOException
   {
      DawProject.exportSchema(new File("MetaData.xsd"), MetaData.class);
   }

   @Test
   public void writeProjectSchema() throws IOException
   {
      DawProject.exportSchema(new File("Project.xsd"), Project.class);
   }

   @Ignore
   @Test
   public void loadEmbeddedFile() throws IOException
   {
      final File file = new File("src/test-data/0.1/bitwig/test3x.dawproject");
      Assert.assertTrue(file.exists());
      Assert.assertTrue(file.isFile());

      // try reading project first
      final Project project = DawProject.loadProject(file);
      Assert.assertNotNull(project);

      final InputStream inputStream = DawProject.streamEmbedded(file, "samples/RC 08 92bpm Break Sp1200.wav");

      final byte[] data = inputStream.readAllBytes();

      Assert.assertEquals(1380652, data.length);
   }

   enum AudioScenario
   {
      Warped,
      RawBeats,
      RawSeconds,
      FileWithAbsolutePath,
      FileWithRelativePath,
   }

   boolean shouldTestOffsetAndFades(final AudioScenario scenario)
   {
      return switch (scenario)
      {
         case Warped -> true;
         case RawBeats -> true;
         case RawSeconds -> true;
         default -> false;
      };
   }

   @Test
   public void createAudioExample() throws IOException
   {
      for (AudioScenario scenario : AudioScenario.values())
      {
         createAudioExample(0, 0, scenario, false);
         if (shouldTestOffsetAndFades(scenario))
         {
            createAudioExample(0, 0, scenario, true);
            createAudioExample(1, 0, scenario, false);
            createAudioExample(0, 1, scenario, false);
         }
      }
   }

   public void createAudioExample(final double playStartOffset, final double clipTime, final AudioScenario scenario, final boolean withFades) throws IOException
   {
      String name = "Audio" + scenario.name();
      if (withFades)
         name += "WithFades";
      if (playStartOffset != 0)
         name += "Offset";
      if (clipTime != 0)
         name += "Clipstart";

      final Project project = createEmptyProject();
      final Track masterTrack = createTrack("Master", EnumSet.noneOf(ContentType.class), MixerRole.master, 1, 0.5);
      final var audioTrack = createTrack("Audio", EnumSet.of(ContentType.audio), MixerRole.regular,1, 0.5);
      audioTrack.channel.destination = masterTrack.channel;

      project.structure.add(masterTrack);
      project.structure.add(audioTrack);

      project.arrangement = new Arrangement();
      project.transport = new Transport();
      project.transport.tempo = new RealParameter();
      project.transport.tempo.unit = Unit.bpm;
      project.transport.tempo.value = 155.0;
      final var arrangementLanes = new Lanes();
      project.arrangement.lanes = arrangementLanes;
      final var arrangementIsInSeconds = scenario == AudioScenario.RawSeconds;
      project.arrangement.lanes.timeUnit = arrangementIsInSeconds ? TimeUnit.seconds : TimeUnit.beats;

      final var sample = "white-glasses.wav";
      Clip audioClip;
      final var sampleDuration = 3.097;
      final var audio = createAudio(sample, 44100, 2, sampleDuration);

      if (scenario == AudioScenario.FileWithAbsolutePath)
      {
         audio.file.external = true;
         audio.file.path = new File("test-data", sample).getAbsolutePath();
      }
      else if (scenario == AudioScenario.FileWithRelativePath)
      {
         audio.file.external = true;
         audio.file.path = "../test-data/" + sample;
      }

      if (scenario == AudioScenario.Warped)
      {
         final var warps = new Warps();
         warps.content = audio;
         warps.contentTimeUnit = TimeUnit.seconds;
         warps.events.add(createWarp(0, 0));
         warps.events.add(createWarp(8, sampleDuration));
         audioClip = createClip(warps, clipTime, 8);
         audioClip.contentTimeUnit = TimeUnit.beats;
         audioClip.playStart = playStartOffset;
         if (withFades)
         {
            audioClip.fadeTimeUnit = TimeUnit.beats;
            audioClip.fadeInTime = 0.25;
            audioClip.fadeOutTime = 0.25;
         }
      }
      else
      {
         audioClip = createClip(audio, clipTime, arrangementIsInSeconds ? sampleDuration : 8);
         audioClip.contentTimeUnit = TimeUnit.seconds;
         audioClip.playStart = playStartOffset;
         audioClip.playStop = sampleDuration;
         if (withFades)
         {
            audioClip.fadeTimeUnit = TimeUnit.seconds;
            audioClip.fadeInTime = 0.1;
            audioClip.fadeOutTime = 0.1;
         }
      }

      final var clips = createClips(audioClip);
      clips.track = audioTrack;
      arrangementLanes.lanes.add(clips);

      saveTestProject(project, name, (meta, files) ->
      {
         files.put(new File("test-data/" + sample), sample);
      });
   }

   @Test
   public void createMIDIAutomationInClipsExample() throws IOException
   {
      createMIDIAutomationExample("MIDI-CC1-AutomationOnTrack", false, false);
      createMIDIAutomationExample("MIDI-CC1-AutomationInClips", true, false);
      createMIDIAutomationExample("MIDI-PitchBend-AutomationOnTrack", false, true);
      createMIDIAutomationExample("MIDI-PitchBend-AutomationInClips", true, true);
   }

   public void createMIDIAutomationExample(final String name, final boolean inClips, final boolean isPitchBend) throws IOException
   {
      final Project project = createEmptyProject();
      final Track masterTrack = createTrack("Master", EnumSet.noneOf(ContentType.class), MixerRole.master, 1, 0.5);
      final var instrumentTrack = createTrack("Notes", EnumSet.of(ContentType.notes), MixerRole.regular,1, 0.5);
      instrumentTrack.channel.destination = masterTrack.channel;

      project.structure.add(masterTrack);
      project.structure.add(instrumentTrack);

      project.arrangement = new Arrangement();
      project.transport = new Transport();
      project.transport.tempo = new RealParameter();
      project.transport.tempo.unit = Unit.bpm;
      project.transport.tempo.value = 123.0;
      final var arrangementLanes = new Lanes();
      project.arrangement.lanes = arrangementLanes;
      project.arrangement.lanes.timeUnit = TimeUnit.beats;

      // Create some mod-wheel or pitch-bend automation
      final var automation = new Points();
      automation.unit = Unit.normalized;
      if (isPitchBend)
      {
         automation.target.expression = ExpressionType.pitchBend;
         automation.target.channel = 0;
      }
      else
      {
         automation.target.expression = ExpressionType.channelController;
         automation.target.channel = 0;
         automation.target.controller = 1;
      }
      automation.points.add(createPoint(0, 0.0, Interpolation.linear));
      automation.points.add(createPoint(1, 0.0, Interpolation.linear));
      automation.points.add(createPoint(2, 0.5, Interpolation.linear));
      automation.points.add(createPoint(3, 0.5, Interpolation.linear));
      automation.points.add(createPoint(4, 1.0, Interpolation.linear));
      automation.points.add(createPoint(5, 1.0, Interpolation.linear));
      automation.points.add(createPoint(6, 0.5, Interpolation.linear));
      automation.points.add(createPoint(7, 1, Interpolation.hold));
      automation.points.add(createPoint(8, 0.5, Interpolation.hold));

      if (inClips)
      {
         final var noteClip = createClip(automation, 0, 8);
         final var clips = createClips(noteClip);
         clips.track = instrumentTrack;
         arrangementLanes.lanes.add(clips);
      }
      else
      {
         automation.track = instrumentTrack;
         arrangementLanes.lanes.add(automation);
      }

      saveTestProject(project, name, null);
   }

   @Test
   public void testDoubleAdapter() throws Exception
   {
      final var adapter = new DoubleAdapter();
      Assert.assertEquals(adapter.unmarshal("-inf").doubleValue(), Double.NEGATIVE_INFINITY, 0);
      Assert.assertEquals(adapter.unmarshal("inf").doubleValue(), Double.POSITIVE_INFINITY, 0);
      Assert.assertEquals("inf", adapter.marshal(Double.POSITIVE_INFINITY));
      Assert.assertEquals("-inf", adapter.marshal(Double.NEGATIVE_INFINITY));
   }

   private static void saveTestProject(final Project project, final String name, final BiConsumer<MetaData, Map<File, String>> configurer) throws IOException
   {
      final MetaData metadata = new MetaData();
      final Map<File, String> embeddedFiles = new HashMap<>();

      if (configurer != null)
         configurer.accept(metadata, embeddedFiles);

      DawProject.save(project, metadata, embeddedFiles, new File("target/" + name + ".dawproject"));
      DawProject.saveXML(project, new File("target/" + name + ".xml"));
      DawProject.validate(project);
   }
}
