package dawproject;

import java.util.Locale;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;

public class DoubleAdapter extends XmlAdapter<String, Double>
{
   @Override
   public Double unmarshal(String v) throws Exception {
      if (v == null || v.isEmpty() || v.equals("null")) {
         return null;
      }
      return Double.parseDouble(v);
   }

   @Override
   public String marshal(Double v) throws Exception {
      if (v == null) {
         return null;
      }
      return String.format(Locale.US, "%.6f", v);
   }
}
