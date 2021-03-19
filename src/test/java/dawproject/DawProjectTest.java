package dawproject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import dawproject.device.Device;
import dawproject.device.Vst3Plugin;
import dawproject.timeline.Clip;
import dawproject.timeline.Clips;
import dawproject.timeline.MarkerEvent;
import dawproject.timeline.Note;
import dawproject.timeline.Notes;
import dawproject.timeline.RootTimeline;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

public class DawProjectTest
{
   private Project createDummyProject(int numTracks)
   {
      Project project = new Project();

      int ID = 0;

      Track masterTrack = project.createTrack();
      masterTrack.name = "Master";
      masterTrack.setID(ID++);

      var masterChannel = project.createChannel();
      masterChannel.isTrackChannel = true;
      masterChannel.volume = RealParameter.create(1.0, Unit.linear);
      masterChannel.pan = RealParameter.create(0.0, Unit.linear);
      masterChannel.setID(ID++);
      masterTrack.channel = masterChannel;

      Device device = new Vst3Plugin();
      device.name = "Limiter";
      device.setID(ID++);
      //device.id = UUID.randomUUID().toString();
      device.state = "plugin-states/12323545.vstpreset";
      masterChannel.devices.add(device);

      project.arrangement = new RootTimeline();
      project.arrangement.markers.add(MarkerEvent.create(0, "Verse"));
      project.arrangement.markers.add(MarkerEvent.create(24, "Chorus"));

      for (int i = 0; i < numTracks; i++)
      {
         var track = project.createTrack();
         track.setID(ID++);
         track.name = "Track " + (i+1);
         track.color = "#" + i + i + i + i + i +i;

         final var clips = new Clips();
         clips.setID(ID++);
         clips.track = track;

         final var clip = new Clip();
         clip.name = "Clip " + i;
         clip.time = 8 * i;
         clip.duration = 4;
         clips.clips.add(clip);

         final var notes = new Notes();
         notes.setID(ID++);
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

         project.arrangement.lanes.add(clips);

         var channel = project.createChannel();
         channel.setID(ID++);
         channel.isTrackChannel = true;
         channel.volume = RealParameter.create(1.0, Unit.linear);
         channel.pan = RealParameter.create(0.0, Unit.linear);
         track.channel = channel;
         channel.destination = masterChannel;
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
   public void writeSchema() throws IOException
   {
      DawProject.exportSchema(new File("target/schema.xs"), Metadata.class);
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
}
