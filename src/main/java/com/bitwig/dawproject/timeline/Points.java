package com.bitwig.dawproject.timeline;

import java.util.ArrayList;
import java.util.List;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementRef;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

import com.bitwig.dawproject.Unit;

/**
 * A timeline of points for automation or expression.
 * <p>
 * All the points should be of the same element-type and match the target.
 * </p>
 */
@XmlRootElement(name = "Points")
@XmlType(propOrder = {"target", "points", "unit"})
public class Points extends Timeline {
	/** The parameter or expression this timeline should target. */
	@XmlElement(name = "Target", required = true)
	public AutomationTarget target = new AutomationTarget();

	/**
	 * The contained points. They should all be of the same type and match the
	 * target parameter.
	 */
	@XmlElementRef(required = true)
	public List<Point> points = new ArrayList<>();

	/** A unit should be provided for when used with RealPoint elements. */
	@XmlAttribute(required = false)
	public Unit unit;
}
