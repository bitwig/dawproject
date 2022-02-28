package com.bitwig.dawproject;

import jakarta.xml.bind.annotation.XmlAccessOrder;
import jakarta.xml.bind.annotation.XmlAccessorOrder;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlID;

@XmlAccessorOrder(XmlAccessOrder.ALPHABETICAL)
public class Referenceable extends Nameable
{
   @XmlAttribute
   @XmlID()
   public final String id;

   public Referenceable()
   {
      this.id = "id" + (ID++);
   }

   public static int ID = 0;

   /** call before export */
   public static void resetID()
   {
      ID = 0;
   }
}
