package dawproject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import dawproject.device.Device;
import dawproject.device.Vst3Plugin;
import dawproject.timeline.Clip;
import dawproject.timeline.Clips;
import dawproject.timeline.Marker;
import dawproject.timeline.Markers;
import dawproject.timeline.Lanes;
import dawproject.timeline.Note;
import dawproject.timeline.Notes;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

public class DawProjectTest
{
   private Project createDummyProject(int numTracks)
   {
      Project project = new Project();

      int ID = 0;

      Track masterTrack = new Track();
      project.tracks.add(masterTrack);
      masterTrack.name = "Master";
      assignID(masterTrack);

      var masterChannel = new Channel();
      masterChannel.belongsToTrack = true;
      final var p3 = new RealParameter();
      p3.value = 1.0;
      p3.unit = Unit.linear;
      masterChannel.volume = p3;
      final var p2 = new RealParameter();
      p2.value = 0.0;
      p2.unit = Unit.linear;
      masterChannel.pan = p2;
      assignID(masterChannel);
      masterChannel.role = ChannelRole.master;
      masterTrack.channel = masterChannel;

      Device device = new Vst3Plugin();
      device.name = "Limiter";
      assignID(device);
      //device.id = UUID.randomUUID().toString();
      device.state = "plugin-states/12323545.vstpreset";
      masterChannel.devices.add(device);

      final var arrangement = new Lanes();
      project.arrangement = arrangement;
      final var cueMarkers = new Markers();
      arrangement.lanes.add(cueMarkers);
      cueMarkers.markers.add(Marker.create(0, "Verse"));
      cueMarkers.markers.add(Marker.create(24, "Chorus"));

      for (int i = 0; i < numTracks; i++)
      {
         var track = new Track();
         project.tracks.add(track);
         assignID(track);
         track.name = "Track " + (i+1);
         track.color = "#" + i + i + i + i + i +i;
         track.contentType = new ContentType[]{ContentType.notes, ContentType.audio};

         final var clips = new Clips();
         assignID(clips);
         clips.track = track;

         final var clip = new Clip();
         clip.name = "Clip " + i;
         clip.time = 8 * i;
         clip.duration = 4;
         clips.clips.add(clip);

         final var notes = new Notes();
         assignID(notes);
         clip.content = notes;

         for(int j=0; j<8; j++)
         {
            final var note = new Note();
            note.key = 36 + 12 * (j % (1+i));
            note.velocity = 0.8;
            note.releaseVelocity = 0.5;
            note.time = 0.5 * j;
            note.duration = 0.5;
            notes.notes.add(note);
         }

         final var clip2 = new Clip();
         clip2.name = "Alias Clip " + i;
         clip2.time = 32 + 8 * i;
         clip2.duration = 4;
         clips.clips.add(clip2);
         clip2.reference = notes;

         arrangement.lanes.add(clips);

         var channel = new Channel();
         project.channels.add(channel);
         assignID(channel);
         channel.belongsToTrack = true;
         final var p1 = new RealParameter();
         p1.value = 1.0;
         p1.unit = Unit.linear;
         channel.volume = p1;
         final var p = new RealParameter();
         p.value = 0.0;
         p.unit = Unit.linear;
         channel.pan = p;
         track.channel = channel;
         channel.destination = masterChannel;
         channel.role = ChannelRole.regular;
      }

      // Route channel 0 to 1
      project.channels.get(0).destination = project.channels.get(1);

      return project;
   }

   @Test
   public void testSaveDawProject() throws IOException
   {
      Project project = createDummyProject(3);
      Metadata metadata = new Metadata();

      DawProject.save(project, metadata, new HashMap(), new File("target/test.dawproject"));
      DawProject.saveXML(project, new File("target/test.dawproject.xml"));
   }

   @Test
   public void testSaveAndLoadDawProject() throws IOException
   {
      Project project = createDummyProject(5);
      Metadata metadata = new Metadata();

      final var file = File.createTempFile("testfile", ".dawproject");
      DawProject.save(project, metadata, new HashMap(), file);

      final var loadedProject = DawProject.loadProject(file);

      Assert.assertEquals(project.tracks.size(), loadedProject.tracks.size());
      Assert.assertEquals(project.channels.size(), loadedProject.channels.size());
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
      File file = new File("src/test-data/0.1/bitwig/test3x.dawproject");
      Assert.assertTrue(file.exists());
      Assert.assertTrue(file.isFile());

      // try reading project first
      Project project = DawProject.loadProject(file);
      Assert.assertNotNull(project);

      InputStream inputStream = DawProject.streamEmbedded(file, "samples/RC 08 92bpm Break Sp1200.wav");

      byte[] data = inputStream.readAllBytes();

      Assert.assertEquals(1380652, data.length);
   }

   private void assignID(final Referencable r)
   {
      r.id = Integer.toString(ID++);
   }

   int ID = 1;
}
