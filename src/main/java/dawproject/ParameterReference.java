package dawproject;

public class ParameterReference extends ObjectReference
{
   /** field name of the automated parameter. */
   public final String parameter;

   ParameterReference(final Location location, final int index, final String parameter)
   {
      super(location, index);
      this.parameter = parameter;
   }
}
