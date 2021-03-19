package dawproject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.UUID;

import dawproject.device.Device;
import dawproject.device.Vst3Plugin;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

public class DawProjectTest
{
   private Project createDummyProject(int numTracks)
   {
      Project project = new Project();

      Track masterTrack = project.createTrack();
      masterTrack.title = "Master";

      var masterChannel = project.createChannel();
      masterChannel.isTrackChannel = true;
      masterChannel.volume = RealParameter.create(1.0, Unit.linear);
      masterChannel.pan = RealParameter.create(0.0, Unit.linear);
      masterTrack.channel = masterChannel;

      Device device = new Vst3Plugin();
      device.name = "Limiter";
      device.id = UUID.randomUUID().toString();
      device.stateFile = "plugin-states/12323545.vstpreset";
      masterChannel.devices.add(device);

      for (int i = 0; i < numTracks; i++)
      {
         var track = project.createTrack();
         track.title = "Track " + (i+1);
         track.color = "#" + i + i + i + i + i +i;

         var channel = project.createChannel();
         channel.isTrackChannel = true;
         channel.volume = RealParameter.create(1.0, Unit.linear);
         channel.pan = RealParameter.create(0.0, Unit.linear);
         track.channel = channel;
         channel.destination = masterChannel;
      }

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
