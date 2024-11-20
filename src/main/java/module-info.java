module com.bitwig.dawproject {
	requires jakarta.xml.bind;
	requires org.apache.commons.io;

	opens com.bitwig.dawproject to jakarta.xml.bind;
	opens com.bitwig.dawproject.device to jakarta.xml.bind;
	opens com.bitwig.dawproject.timeline to jakarta.xml.bind;

	exports com.bitwig.dawproject;
	exports com.bitwig.dawproject.device;
	exports com.bitwig.dawproject.timeline;
}