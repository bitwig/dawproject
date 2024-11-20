package com.bitwig.dawproject;

import jakarta.xml.bind.annotation.XmlAccessOrder;
import jakarta.xml.bind.annotation.XmlAccessorOrder;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlID;

@XmlAccessorOrder(XmlAccessOrder.ALPHABETICAL)
public abstract class Referenceable extends Nameable {
	/**
	 * Unique string identifier of this element. This is used for referencing this
	 * instance from other elements.
	 */
	@XmlAttribute
	@XmlID()
	public final String id;

	public Referenceable() {
		this.id = "id" + (ID++);
	}

	public static int ID = 0;

	/** call before export */
	public static void resetID() {
		ID = 0;
	}
}
