package dawproject;

public class Send extends DawObject
{
   public double level;

   /** Target channel this track will play back into. */
   public ObjectReference<Channel> output;

   public SendType type = SendType.post;
}
