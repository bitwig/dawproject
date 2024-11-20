package com.bitwig.dawproject;

import jakarta.xml.bind.annotation.XmlAccessOrder;
import jakarta.xml.bind.annotation.XmlAccessorOrder;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlID;


/** Base class for everything which can be referenced. */
@XmlAccessorOrder(XmlAccessOrder.ALPHABETICAL)
public abstract class Referenceable extends Nameable
{
    private static int     idCounter    = 0;
    private static boolean enableAutoID = false;

    /**
     * Unique string identifier of this element. This is used for referencing this instance from
     * other elements.
     */
    @XmlAttribute
    @XmlID()
    public final String    id;


    /** Constructor. */
    protected Referenceable ()
    {
        this.id = enableAutoID ? "id" + (idCounter++) : null;
    }


    /**
     * Enables automatic creation of XML IDs. Resets the IDs as well to 0.
     *
     * @param enable True to enable automatic ID creation for all instances of
     *            {@link #Referenceable()}
     */
    public static void setAutoID (final boolean enable)
    {
        enableAutoID = enable;
        idCounter = 0;
    }
}
