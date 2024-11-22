package com.bitwig.dawproject;

import java.util.Locale;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;

/** Adapter for a double value which can handle Infinity constants. */
public class DoubleAdapter extends XmlAdapter<String, Double> {
	/** {@inheritDoc} */
	@Override
	public Double unmarshal(final String v) throws Exception {
		if (v == null || v.isEmpty() || v.equals("null"))
			return null;
		return Double.valueOf(v.replace("inf", "Infinity"));
	}

	/** {@inheritDoc} */
	@Override
	public String marshal(final Double v) throws Exception {
		if (v == null)
			return null;
		return String.format(Locale.US, "%.6f", v).replace("Infinity", "inf");
	}
}
