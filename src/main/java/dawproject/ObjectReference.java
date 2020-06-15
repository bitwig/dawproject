package dawproject;

/** Immutable object reference which is created by the Project instance. */
public class ObjectReference<T>
{
   /** location within Project instance (which array) */
   public final Location location;

   /** Index into the array */
   public final int index;

   ObjectReference(final Location location, final int index)
   {
      assert location != null;
      assert index >= 0;

      this.location = location;
      this.index = index;
   }
}
