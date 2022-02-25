package com.bitwig.dawproject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import com.bitwig.dawproject.device.Device;
import com.bitwig.dawproject.device.Vst3Plugin;
import com.bitwig.dawproject.timeline.RealPoint;
import com.bitwig.dawproject.timeline.Clip;
import com.bitwig.dawproject.timeline.Clips;
import com.bitwig.dawproject.timeline.Marker;
import com.bitwig.dawproject.timeline.Markers;
import com.bitwig.dawproject.timeline.Lanes;
import com.bitwig.dawproject.timeline.Note;
import com.bitwig.dawproject.timeline.Notes;
import com.bitwig.dawproject.timeline.Points;
import com.bitwig.dawproject.timeline.Timebase;
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

   private Project createDummyProject(final int numTracks, final EnumSet<Features> features)
   {
      Referenceable.resetID();
      final Project project = new Project();

      project.application.name = "Test";
      project.application.version = "1.0";

      final Track masterTrack = new Track();
      project.tracks.add(masterTrack);
      masterTrack.name = "Master";

      final var p3 = new RealParameter();
      p3.value = 1.0;
      p3.unit = Unit.linear;
      masterTrack.volume = p3;
      final var p2 = new RealParameter();
      p2.value = 0.0;
      p2.unit = Unit.linear;
      masterTrack.pan = p2;
      masterTrack.mixerRole = MixerRole.master;

      if (features.contains(Features.PLUGINS))
      {
         final Device device = new Vst3Plugin();
         device.deviceName = "Limiter";
         //device.id = UUID.randomUUID().toString();
         device.state = new FileReference();
         device.state.path = "plugin-states/12323545.vstpreset";

         if (masterTrack.devices == null)
            masterTrack.devices = new ArrayList<>();

         masterTrack.devices.add(device);
      }

      project.arrangement = new Arrangement();
      final var arrangementLanes = new Lanes();
      arrangementLanes.timebase = Timebase.beats;
      project.arrangement.content = arrangementLanes;

      if (features.contains(Features.CUE_MARKERS))
      {
         final var cueMarkers = new Markers();
         project.arrangement.markers = cueMarkers;
         cueMarkers.markers.add(createMarker(0, "Verse"));
         cueMarkers.markers.add(createMarker(24, "Chorus"));
      }

      for (int i = 0; i < numTracks; i++)
      {
         final var track = new Track();
         project.tracks.add(track);
         track.name = "Track " + (i+1);
         track.color = "#" + i + i + i + i + i +i;
         track.timelineRole = new TimelineRole[]{TimelineRole.notes, TimelineRole.audio};

         final var channel = track;
         final var p1 = new RealParameter();
         p1.value = 1.0;
         p1.unit = Unit.linear;
         channel.volume = p1;
         final var p = new RealParameter();
         p.value = 0.0;
         p.unit = Unit.linear;
         channel.pan = p;
         channel.destination = masterTrack;
         channel.mixerRole = MixerRole.regular;

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
               points.target.parameter = channel.volume;
               trackLanes.lanes.add(points);

               // fade-in over 8 quarter notes
               points.points.add(createPoint(0.0, 0.0));
               points.points.add(createPoint(8.0, 1.0));
            }
         }
      }

      // Route channel 0 to 1
      //project.channels.get(0).destination = project.channels.get(1);

      return project;
   }

   private RealPoint createPoint(final double time, final double value)
   {
      final var point = new RealPoint();
      point.time = time;
      point.value = value;
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
      final Metadata metadata = new Metadata();

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
      final Metadata metadata = new Metadata();

      final var file = File.createTempFile("testfile", ".dawproject");
      final Map<File, String> embeddedFiles = new HashMap<>();
      DawProject.save(project, metadata, embeddedFiles, file);

      final var loadedProject = DawProject.loadProject(file);

      Assert.assertEquals(project.tracks.size(), loadedProject.tracks.size());
      Assert.assertEquals(project.scenes.size(), loadedProject.scenes.size());
   }

   @Test
   public void saveComplexDawProject() throws IOException
   {
      final Project project = createDummyProject(3, EnumSet.allOf(Features.class));
      final Metadata metadata = new Metadata();

      final Map<File, String> embeddedFiles = new HashMap<>();
      DawProject.save(project, metadata, embeddedFiles, new File("target/test-complex.dawproject"));
      DawProject.saveXML(project, new File("target/test-complex.dawproject.xml"));
   }

   @Test
   public void saveAndLoadComplexDawProject() throws IOException
   {
      final Project project = createDummyProject(5, EnumSet.allOf(Features.class));
      final Metadata metadata = new Metadata();

      final Map<File, String> embeddedFiles = new HashMap<>();
      final var file = File.createTempFile("testfile2", ".dawproject");
      DawProject.save(project, metadata, embeddedFiles, file);

      final var loadedProject = DawProject.loadProject(file);

      Assert.assertEquals(project.tracks.size(), loadedProject.tracks.size());
      Assert.assertEquals(project.scenes.size(), loadedProject.scenes.size());
   }

   @Test
   public void writeMetadataSchema() throws IOException
   {
      DawProject.exportSchema(new File("target/metadata.xs"), Metadata.class);
   }

   @Test
   public void writeProjectSchema() throws IOException
   {
      DawProject.exportSchema(new File("target/project.xs"), Project.class);
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

}
