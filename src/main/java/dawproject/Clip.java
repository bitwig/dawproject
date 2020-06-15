package dawproject;

public class Clip extends EnduringEvent
{
   /** Time inside the target timeline where teh clip starts playing. */
   public double playStart;

   /** Duration of fade-in */
   public double fadeInTime;

   /** Duration of fade-out */
   public double fadeOutTime;

   public Loop loop = null;

   public ObjectReference<Timeline> content;
}
