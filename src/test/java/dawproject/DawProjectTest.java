package dawproject;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

import org.junit.Test;

public class DawProjectTest
{
   private Project createDummyProject(int numTracks)
   {
      Project project = new Project();

      Track masterTrack = project.createTrack();
      masterTrack.meta = new Meta();
      masterTrack.meta.title = "Master";

      var masterChannel = project.createChannel();
      masterChannel.isTrackChannel = true;
      masterTrack.output = project.createReference(masterChannel);

      Device device = project.createDevice();
      device.deviceName = "Limiter";
      device.id = UUID.randomUUID().toString();
      device.stateFile = "plugin-states/12323545.fxb";
      masterChannel.devices.add(project.createReference(device));

      for (int i = 0; i < numTracks; i++)
      {
         var track = project.createTrack();
         track.meta = new Meta();
         track.meta.title = "Track " + (i+1);
         track.meta.color = "#" + i + i + i + i + i +i;

         var channel = project.createChannel();
         channel.isTrackChannel = true;
         track.output = project.createReference(channel);
         channel.output = project.createReference(masterChannel);
      }

      return project;
   }

    @Test
    public void testSaveDawProject() throws IOException
    {
       Project project = createDummyProject(5);
       Metadata metadata = new Metadata();

       DawProject.save(project, metadata, new HashMap(), new File("target/test.dawproject"));
       DawProject.saveJson(project, new File("target/test.dawproject.json"));
    }

    @Test
    public void writeMetadataSchema() throws IOException
    {
       DawProject.exportSchema(new File("target/metadata.schema.json"), Metadata.class);
    }

    @Test
    public void writeProjectSchema() throws IOException
    {
       DawProject.exportSchema(new File("target/project.schema.json"), Project.class);
    }
}
