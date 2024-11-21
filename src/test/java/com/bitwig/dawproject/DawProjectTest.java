package com.bitwig.dawproject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;

import com.bitwig.dawproject.device.Device;
import com.bitwig.dawproject.device.DeviceRole;
import com.bitwig.dawproject.device.Vst3Plugin;
import com.bitwig.dawproject.timeline.Clip;
import com.bitwig.dawproject.timeline.Clips;
import com.bitwig.dawproject.timeline.Lanes;
import com.bitwig.dawproject.timeline.Markers;
import com.bitwig.dawproject.timeline.Note;
import com.bitwig.dawproject.timeline.Notes;
import com.bitwig.dawproject.timeline.Points;
import com.bitwig.dawproject.timeline.TimeUnit;
import com.bitwig.dawproject.timeline.Warps;
import org.junit.Assert;
import org.junit.Test;

/** Several tests for the reading/writing DAWproject files. */
public class DawProjectTest {
	private enum Features {
		CUE_MARKERS, CLIPS, AUDIO, NOTES, AUTOMATION, ALIAS_CLIPS, PLUGINS
	}

	private enum AudioScenario {
		WARPED, RAW_BEATS, RAW_SECONDS, FILE_WITH_ABSOLUTE_PATH, FILE_WITH_RELATIVE_PATH
	}

	private final Set<Features> simpleFeatures = EnumSet.of(Features.CLIPS, Features.NOTES, Features.AUDIO);

	/**
	 * Validate a project.
	 *
	 * @throws IOException
	 *             Could not validate the project
	 */
	@Test
	public void validateDawProject() throws IOException {
		final Project project = createDummyProject(3, this.simpleFeatures);
		DawProject.validate(project);
	}

	/**
	 * Validate a complex project.
	 *
	 * @throws IOException
	 *             Could not validate the project
	 */
	@Test
	public void validateComplexDawProject() throws IOException {
		final Project project = createDummyProject(3, EnumSet.allOf(Features.class));
		DawProject.validate(project);
	}

	/**
	 * Test storing a project.
	 *
	 * @throws IOException
	 *             Could not store the project
	 */
	@Test
	public void saveDawProject() throws IOException {
		final Project project = createDummyProject(3, this.simpleFeatures);
		final MetaData metadata = new MetaData();

		final Map<File, String> embeddedFiles = new HashMap<>();
		final File file = new File("target/test.dawproject");
		DawProject.save(project, metadata, embeddedFiles, file);
		DawProject.saveXML(project, new File("target/test.dawproject.xml"));
		Assert.assertTrue(file.exists());
	}

	/**
	 * Test storing and loading a project.
	 *
	 * @throws IOException
	 *             Could not load the project
	 */
	@Test
	public void saveAndLoadDawProject() throws IOException {
		final Project project = createDummyProject(5, this.simpleFeatures);
		final MetaData metadata = new MetaData();

		final var file = File.createTempFile("testfile", ".dawproject");
		final Map<File, String> embeddedFiles = new HashMap<>();
		DawProject.save(project, metadata, embeddedFiles, file);

		final var loadedProject = DawProject.loadProject(file);

		Assert.assertEquals(project.structure.size(), loadedProject.structure.size());
		Assert.assertEquals(project.scenes.size(), loadedProject.scenes.size());
	}

	/**
	 * Test storing a complex project.
	 *
	 * @throws IOException
	 *             Could not store the project
	 */
	@Test
	public void saveComplexDawProject() throws IOException {
		final Project project = createDummyProject(3, EnumSet.allOf(Features.class));
		final MetaData metadata = new MetaData();

		final Map<File, String> embeddedFiles = new HashMap<>();
		final File file = new File("target/test-complex.dawproject");
		DawProject.save(project, metadata, embeddedFiles, file);
		DawProject.saveXML(project, new File("target/test-complex.dawproject.xml"));
		Assert.assertTrue(file.exists());
	}

	/**
	 * Test storing and loading a complex project.
	 *
	 * @throws IOException
	 *             Could not store or load the project
	 */
	@Test
	public void saveAndLoadComplexDawProject() throws IOException {
		final Project project = createDummyProject(5, EnumSet.allOf(Features.class));
		final MetaData metadata = new MetaData();

		final Map<File, String> embeddedFiles = new HashMap<>();
		final var file = File.createTempFile("testfile2", ".dawproject");
		DawProject.save(project, metadata, embeddedFiles, file);

		final var loadedProject = DawProject.loadProject(file);

		Assert.assertEquals(project.structure.size(), loadedProject.structure.size());
		Assert.assertEquals(project.scenes.size(), loadedProject.scenes.size());
		Assert.assertEquals(project.arrangement.lanes.getClass(), loadedProject.arrangement.lanes.getClass());
		Assert.assertEquals(project.arrangement.markers.getClass(), loadedProject.arrangement.markers.getClass());
	}

	/**
	 * Test creating the metadata XML schema.
	 *
	 * @throws IOException
	 *             Could not create the XML schema
	 */
	@Test
	public void writeMetadataSchema() throws IOException {
		final File file = new File("MetaData.xsd");
		DawProject.exportSchema(file, MetaData.class);
		Assert.assertTrue(file.exists());
	}

	/**
	 * Test creating the project XML schema.
	 *
	 * @throws IOException
	 *             Could not create the XML schema
	 */
	@Test
	public void writeProjectSchema() throws IOException {
		final File file = new File("Project.xsd");
		DawProject.exportSchema(file, Project.class);
		Assert.assertTrue(file.exists());
	}

	/**
	 * Test audio clips with offsets and fades.
	 *
	 * @throws IOException
	 *             Could not create
	 */
	@Test
	public void createAudioExample() throws IOException {
		for (AudioScenario scenario : AudioScenario.values()) {
			createAudioExample(0, 0, scenario, false);
			if (shouldTestOffsetAndFades(scenario)) {
				createAudioExample(0, 0, scenario, true);
				createAudioExample(1, 0, scenario, false);
				createAudioExample(0, 1, scenario, false);
			}
		}
	}

	/**
	 * Test MIDI automation envelopes.
	 *
	 * @throws IOException
	 *             Could not create
	 */
	@Test
	public void createMIDIAutomationInClipsExample() throws IOException {
		createMIDIAutomationExample("MIDI-CC1-AutomationOnTrack", false, false);
		createMIDIAutomationExample("MIDI-CC1-AutomationInClips", true, false);
		createMIDIAutomationExample("MIDI-PitchBend-AutomationOnTrack", false, true);
		createMIDIAutomationExample("MIDI-PitchBend-AutomationInClips", true, true);
	}

	/**
	 * Test the double adapter for infinity constants.
	 *
	 * @throws Exception
	 *             Could not parse the infinity constants
	 */
	@Test
	public void testDoubleAdapter() throws Exception {
		final var adapter = new DoubleAdapter();
		Assert.assertEquals(adapter.unmarshal("-inf").doubleValue(), Double.NEGATIVE_INFINITY, 0);
		Assert.assertEquals(adapter.unmarshal("inf").doubleValue(), Double.POSITIVE_INFINITY, 0);
		Assert.assertEquals("inf", adapter.marshal(Double.valueOf(Double.POSITIVE_INFINITY)));
		Assert.assertEquals("-inf", adapter.marshal(Double.valueOf(Double.NEGATIVE_INFINITY)));
	}

	private static boolean shouldTestOffsetAndFades(final AudioScenario scenario) {
		return switch (scenario) {
			case WARPED -> true;
			case RAW_BEATS -> true;
			case RAW_SECONDS -> true;
			default -> false;
		};
	}

	private static void createAudioExample(final double playStartOffset, final double clipTime,
			final AudioScenario scenario, final boolean withFades) throws IOException {
		String name = "Audio" + scenario.name();
		if (withFades)
			name += "WithFades";
		if (playStartOffset != 0)
			name += "Offset";
		if (clipTime != 0)
			name += "Clipstart";

		final Project project = createEmptyProject();
		final Track masterTrack = Utility.createTrack("Master", EnumSet.noneOf(ContentType.class), MixerRole.MASTER, 1,
				0.5);
		final var audioTrack = Utility.createTrack("Audio", EnumSet.of(ContentType.AUDIO), MixerRole.REGULAR, 1, 0.5);
		audioTrack.channel.destination = masterTrack.channel;

		project.structure.add(masterTrack);
		project.structure.add(audioTrack);

		project.arrangement = new Arrangement();
		project.transport = new Transport();
		project.transport.tempo = Utility.createRealParameter(Unit.BPM, 155.0);
		final var arrangementLanes = new Lanes();
		project.arrangement.lanes = arrangementLanes;
		final var arrangementIsInSeconds = scenario == AudioScenario.RAW_SECONDS;
		project.arrangement.lanes.timeUnit = arrangementIsInSeconds ? TimeUnit.SECONDS : TimeUnit.BEATS;

		final var sample = "white-glasses.wav";
		Clip audioClip;
		final var sampleDuration = 3.097;
		final var audio = Utility.createAudio(sample, 44100, 2, sampleDuration);

		if (scenario == AudioScenario.FILE_WITH_ABSOLUTE_PATH) {
			audio.file.external = Boolean.TRUE;
			audio.file.path = new File("test-data", sample).getAbsolutePath();
		} else if (scenario == AudioScenario.FILE_WITH_RELATIVE_PATH) {
			audio.file.external = Boolean.TRUE;
			audio.file.path = "../test-data/" + sample;
		}

		if (scenario == AudioScenario.WARPED) {
			final var warps = new Warps();
			warps.content = audio;
			warps.contentTimeUnit = TimeUnit.SECONDS;
			warps.events.add(Utility.createWarp(0, 0));
			warps.events.add(Utility.createWarp(8, sampleDuration));
			audioClip = Utility.createClip(warps, clipTime, 8);
			audioClip.contentTimeUnit = TimeUnit.BEATS;
			audioClip.playStart = Double.valueOf(playStartOffset);
			if (withFades) {
				audioClip.fadeTimeUnit = TimeUnit.BEATS;
				audioClip.fadeInTime = Double.valueOf(0.25);
				audioClip.fadeOutTime = Double.valueOf(0.25);
			}
		} else {
			audioClip = Utility.createClip(audio, clipTime, arrangementIsInSeconds ? sampleDuration : 8);
			audioClip.contentTimeUnit = TimeUnit.SECONDS;
			audioClip.playStart = Double.valueOf(playStartOffset);
			audioClip.playStop = Double.valueOf(sampleDuration);
			if (withFades) {
				audioClip.fadeTimeUnit = TimeUnit.SECONDS;
				audioClip.fadeInTime = Double.valueOf(0.1);
				audioClip.fadeOutTime = Double.valueOf(0.1);
			}
		}

		final var clips = Utility.createClips(audioClip);
		clips.track = audioTrack;
		arrangementLanes.lanes.add(clips);

		saveTestProject(project, name, (meta, files) -> files.put(new File("test-data/" + sample), sample));
	}

	private static void createMIDIAutomationExample(final String name, final boolean inClips, final boolean isPitchBend)
			throws IOException {
		final Project project = createEmptyProject();
		final Track masterTrack = Utility.createTrack("Master", EnumSet.noneOf(ContentType.class), MixerRole.MASTER, 1,
				0.5);
		final var instrumentTrack = Utility.createTrack("Notes", EnumSet.of(ContentType.NOTES), MixerRole.REGULAR, 1,
				0.5);
		instrumentTrack.channel.destination = masterTrack.channel;

		project.structure.add(masterTrack);
		project.structure.add(instrumentTrack);

		project.arrangement = new Arrangement();
		project.transport = new Transport();
		project.transport.tempo = Utility.createRealParameter(Unit.BPM, 123.0);
		final var arrangementLanes = new Lanes();
		project.arrangement.lanes = arrangementLanes;
		project.arrangement.lanes.timeUnit = TimeUnit.BEATS;

		// Create some mod-wheel or pitch-bend automation
		final var automation = new Points();
		automation.unit = Unit.NORMALIZED;
		if (isPitchBend) {
			automation.target.expression = ExpressionType.PITCH_BEND;
			automation.target.channel = Integer.valueOf(0);
		} else {
			automation.target.expression = ExpressionType.CHANNEL_CONTROLLER;
			automation.target.channel = Integer.valueOf(0);
			automation.target.controller = Integer.valueOf(1);
		}
		automation.points.add(Utility.createPoint(0, 0.0, Interpolation.LINEAR));
		automation.points.add(Utility.createPoint(1, 0.0, Interpolation.LINEAR));
		automation.points.add(Utility.createPoint(2, 0.5, Interpolation.LINEAR));
		automation.points.add(Utility.createPoint(3, 0.5, Interpolation.LINEAR));
		automation.points.add(Utility.createPoint(4, 1.0, Interpolation.LINEAR));
		automation.points.add(Utility.createPoint(5, 1.0, Interpolation.LINEAR));
		automation.points.add(Utility.createPoint(6, 0.5, Interpolation.LINEAR));
		automation.points.add(Utility.createPoint(7, 1, Interpolation.HOLD));
		automation.points.add(Utility.createPoint(8, 0.5, Interpolation.HOLD));

		if (inClips) {
			final var noteClip = Utility.createClip(automation, 0, 8);
			final var clips = Utility.createClips(noteClip);
			clips.track = instrumentTrack;
			arrangementLanes.lanes.add(clips);
		} else {
			automation.track = instrumentTrack;
			arrangementLanes.lanes.add(automation);
		}

		saveTestProject(project, name, null);
	}

	private static void saveTestProject(final Project project, final String name,
			final BiConsumer<MetaData, Map<File, String>> configurer) throws IOException {
		final MetaData metadata = new MetaData();
		final Map<File, String> embeddedFiles = new HashMap<>();

		if (configurer != null)
			configurer.accept(metadata, embeddedFiles);

		DawProject.save(project, metadata, embeddedFiles, new File("target/" + name + ".dawproject"));
		DawProject.saveXML(project, new File("target/" + name + ".xml"));
		DawProject.validate(project);
	}

	private static Project createDummyProject(final int numTracks, final Set<Features> features) {
		final Project project = createEmptyProject();

		final Track masterTrack = Utility.createTrack("Master", EnumSet.noneOf(ContentType.class), MixerRole.MASTER, 1,
				0.5);
		project.structure.add(masterTrack);

		if (features.contains(Features.PLUGINS)) {
			final Device device = new Vst3Plugin();
			device.deviceName = "Limiter";
			device.deviceRole = DeviceRole.AUDIO_FX;
			device.state = new FileReference();
			device.state.path = "plugin-states/12323545.vstpreset";

			if (masterTrack.channel.devices == null)
				masterTrack.channel.devices = new ArrayList<>();

			masterTrack.channel.devices.add(device);
		}

		project.arrangement = new Arrangement();
		final var arrangementLanes = new Lanes();
		arrangementLanes.timeUnit = TimeUnit.BEATS;
		project.arrangement.lanes = arrangementLanes;

		if (features.contains(Features.CUE_MARKERS)) {
			final var cueMarkers = new Markers();
			project.arrangement.markers = cueMarkers;
			cueMarkers.markers.add(Utility.createMarker(0, "Verse"));
			cueMarkers.markers.add(Utility.createMarker(24, "Chorus"));
		}

		for (int i = 0; i < numTracks; i++) {
			final var track = Utility.createTrack("Track " + (i + 1), EnumSet.of(ContentType.NOTES), MixerRole.REGULAR,
					1, 0.5);
			project.structure.add(track);
			track.color = "#" + i + i + i + i + i + i;
			track.channel.destination = masterTrack.channel;

			final var trackLanes = new Lanes();
			trackLanes.track = track;
			arrangementLanes.lanes.add(trackLanes);

			if (features.contains(Features.CLIPS)) {
				final var clips = new Clips();

				trackLanes.lanes.add(clips);

				final var clip = new Clip();
				clip.name = "Clip " + i;
				clip.time = 8 * i;
				clip.duration = Double.valueOf(4.0);
				clips.clips.add(clip);

				final var notes = new Notes();
				clip.content = notes;

				for (int j = 0; j < 8; j++) {
					final var note = new Note();
					note.key = 36 + 12 * (j % (1 + i));
					note.velocity = Double.valueOf(0.8);
					note.releaseVelocity = Double.valueOf(0.5);
					note.time = Double.valueOf(0.5 * j);
					note.duration = Double.valueOf(0.5);
					notes.notes.add(note);
				}

				if (features.contains(Features.ALIAS_CLIPS)) {
					final var clip2 = new Clip();
					clip2.name = "Alias Clip " + i;
					clip2.time = 32 + 8 * i;
					clip2.duration = Double.valueOf(4.0);
					clips.clips.add(clip2);
					clip2.reference = notes;
				}

				if (i == 0 && features.contains(Features.AUTOMATION)) {
					final var points = new Points();
					points.target.parameter = track.channel.volume;
					trackLanes.lanes.add(points);

					// fade-in over 8 quarter notes
					points.points.add(Utility.createPoint(0.0, 0.0, Interpolation.LINEAR));
					points.points.add(Utility.createPoint(8.0, 1.0, Interpolation.LINEAR));
				}
			}
		}

		return project;
	}

	private static Project createEmptyProject() {
		Referenceable.setAutoID(true);

		final Project project = new Project();
		project.application.name = "Test";
		project.application.version = "1.0";
		return project;
	}
}
