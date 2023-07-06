enum TimeUnit: UInt32 {
    case beats = 0
    case seconds = 1
}

enum Interpolation: UInt32 {
    case hold = 0
    case linear = 1
}

enum Unit: UInt32 {
    case linear = 0
    case normalized = 1
    case percent = 2
    case decibel = 3
    case hertz = 4
    case semitones = 5
    case seconds = 6
    case beats = 7
    case bpm = 8
}

enum SendType: UInt32 {
    case pre = 0
    case post = 1
}

enum MixerRole: UInt32 {
    case regular = 0
    case master = 1
    case effectTrack = 2
    case subMix = 3
    case vca = 4
}

enum ContentType: UInt32 {
    case audio = 0
    case automation = 1
    case notes = 2
    case video = 3
    case markers = 4
    case tracks = 5
}

enum EqBandType: UInt32 {
    case highPass = 0
    case lowPass = 1
    case bandPass = 2
    case highShelf = 3
    case lowShelf = 4
    case bell = 5
    case notch = 6
}

/* The main root element of the DAWPROJECT format. This is stored in the file project.xml file inside the container. */

struct Project {

    /* Version of DAWPROJECT format this file was saved as. */
    var version: String?

    /* Metadata (name/version) about the application that saved this file. */
    var application: Application

    /* Transport element containing playback parameters such as Tempo and Time-signature. */
    var transport: Transport?
    var structure: [Lane] // Track/Channel structure of this file.

    /* The main Arrangement timeline of this file. */
    var arrangement: Arrangement?
    var scenes: [Scene]? // Clip Launcher scenes of this file.
}

/* Metadata root element of the DAWPROJECT format. This is stored in the file metadata.xml file inside the container. */

struct MetaData {
    var title: String? // Title of the song/project.
    var artist: String? // Recording Artist.
    var album: String? // Album.
    var originalArtist: String? // Original Artist.
    var composer: String? // Composer.
    var songwriter: String? // Songwriter.
    var producer: String? // Producer.
    var arranger: String? // Arranger.
    var year: String? // Year this project/song was recorded.
    var genre: String? // Genre/style
    var copyright: String? // Copyright notice.
    var website: String? // URL to website related to this project.
    var comment: String? // General comment or description.
}

/* Metadata about the application which saved the DAWPROJECT file. */

struct Application {
    var name: String? // Name of the application.
    var version: String? // Version number of the application.
}

/* References a file either within a DAWPROJECT container or on disk. */

struct FileReference {

    /* File path. either
 <li>path within the container</li>
 <li>relative to .dawproject file (when external = "true")</li>
 <li>absolute path (when external = "true" and path starts with a slash or windows drive letter)</li> */
    var path: String?

    /* When true, the path is relative to the .dawproject file. Default value is false. */
    var external: Boolean?
}

/* Transport element containing playback parameters such as Tempo and Time-signature. */

struct Transport {

    /* Tempo parameter for setting and/or automating the tempo. */
    var tempo: RealParameter?
    var timeSignature: TimeSignatureParameter? // Time-signature parameter.
}

/* Represents a sequencer track. */

struct Track {

    /* Role of this track in timelines & arranger. Can be multiple (comma-separated). */
    var contentType: ContentType[]?
    var loaded: Boolean? // If this track is loaded/active of not.

    /* Mixer channel used for the output of this track. */
    var channel: Channel?

    /* Child tracks, typically used to represent group/folder tracks with contentType="tracks". */
    var tracks: [Track]?

    /* Unique string identifier of this element. This is used for referencing this instance from other elements. */
    var id: String?
    var name: String? // Name/label of this object.

    /* Color of this object in HTML-style format. (#rrggbb) */
    var color: String?
    var comment: String? // Comment/description of this object.
}

/* Represents a mixer channel. It provides the ability to route signals to other channels and can contain
 Device/Plug-in for processing. */

struct Channel {
    var role: MixerRole? // Role of this channel in the mixer.

    /* Number of audio-channels of this mixer channel. (1=mono, 2=stereo…) */
    var audioChannels: Int?
    var volume: RealParameter? // Channel volume
    var pan: RealParameter? // Channel pan/balance
    var mute: BoolParameter? // Channel mute
    var solo: Boolean? // Channel solo
    var destination: Channel? // Output channel routing
    var sends: [Send]? // Send levels & destination
    var devices: [Device] // Devices & plug-ins of this channel

    /* Unique string identifier of this element. This is used for referencing this instance from other elements. */
    var id: String?
    var name: String? // Name/label of this object.

    /* Color of this object in HTML-style format. (#rrggbb) */
    var color: String?
    var comment: String? // Comment/description of this object.
}

/* A single send of a mixer channel. */

struct Send {
    var volume: RealParameter // Send level.
    var pan: RealParameter? // Send pan/balance.
    var type: SendType? // Send type.
    var destination: Channel? // Send destination.

    /* Unique string identifier of this element. This is used for referencing this instance from other elements. */
    var id: String?
    var name: String? // Name/label of this object.

    /* Color of this object in HTML-style format. (#rrggbb) */
    var color: String?
    var comment: String? // Comment/description of this object.
}

/* Represents the main Arrangement timeline of a DAW. */

struct Arrangement {

    /* Automation data for time-signature inside this Arrangement.
 <pre><code>
 &lt;Arrangement&gt;
   &lt;TimeSignatureAutomation target=&quot;id-of-TimeSignatureParameter&quot; ... &gt;
     &lt;TimeSignaturePoint time=&quot;0&quot; numerator=&quot;7&quot;, denominator=&quot;8&quot;/&gt;
     &lt;TimeSignaturePoint time=&quot;21&quot; numerator=&quot;4&quot;, denominator=&quot;4&quot;/&gt;
        ...
   &lt;/TimeSignatureAutomation&gt;
 &lt;/Arrangement&gt;
 </code></pre> */
    var timeSignatureAutomation: Points?

    /* Automation data for tempo inside this Arrangement, which will define the conversion between seconds and beats
 at the root level. */
    var tempoAutomation: Points?
    var markers: Markers? // Cue markers inside this arrangement

    /* The lanes of this arrangement. Generally this would contain another Lanes timeline for (and scoped to) each
 track which would then contain all Note, Audio, and Automation timelines. */
    var lanes: Lanes?

    /* Unique string identifier of this element. This is used for referencing this instance from other elements. */
    var id: String?
    var name: String? // Name/label of this object.

    /* Color of this object in HTML-style format. (#rrggbb) */
    var color: String?
    var comment: String? // Comment/description of this object.
}

/* Represents a clip launcher Scene of a DAW. */

struct Scene {

    /* Content timeline of this scene, will typically be structured like this:
 <pre><code>
 &lt;Scene&gt;
   &lt;Lanes&gt;
     &lt;ClipSlot track=&quot;...&quot;&gt;
        &lt;Clip&gt;
           ...
        &lt;/Clip&gt;
     &lt;/ClipSlot&gt;
      ...
   &lt;/Lanes&gt;
 &lt;/Scene&gt;
 </code></pre> */
    var content: Timeline

    /* Unique string identifier of this element. This is used for referencing this instance from other elements. */
    var id: String?
    var name: String? // Name/label of this object.

    /* Color of this object in HTML-style format. (#rrggbb) */
    var color: String?
    var comment: String? // Comment/description of this object.
}

/* Represent a clip launcher slot within a Scene which can contain a Clip. It is generally set to a specific track. */

struct ClipSlot {

    /* Whether launching this slot should stop the track playback when this slot is empty. */
    var hasStop: Boolean?
    var clip: Clip? // Contained clip.

    /* When present, the timeline is local to this track. */
    var track: Track?

    /* The TimeUnit used by this and nested timelines. If no TimeUnit is provided by this or the parent scope then
 'beats' will be used. */
    var timeUnit: TimeUnit?

    /* Unique string identifier of this element. This is used for referencing this instance from other elements. */
    var id: String?
    var name: String? // Name/label of this object.

    /* Color of this object in HTML-style format. (#rrggbb) */
    var color: String?
    var comment: String? // Comment/description of this object.
}

/* The Lanes element provides the ability to contain multiple parallel timelines inside it, and is the main layering
  element of the format. It is also a natural fit for defining the scope of contained timelines to a specific track. */

struct Lanes {
    var lanes: [Timeline] // Lanes representing nested content.

    /* When present, the timeline is local to this track. */
    var track: Track?

    /* The TimeUnit used by this and nested timelines. If no TimeUnit is provided by this or the parent scope then
 'beats' will be used. */
    var timeUnit: TimeUnit?

    /* Unique string identifier of this element. This is used for referencing this instance from other elements. */
    var id: String?
    var name: String? // Name/label of this object.

    /* Color of this object in HTML-style format. (#rrggbb) */
    var color: String?
    var comment: String? // Comment/description of this object.
}

/* Represents a timeline of clips. Each contained Clip have its time and duration that defines its location on this
 timeline (defined by timeUnit of the Clips element). */

struct Clips {
    var clips: [Clip]? // Clips of this timeline.

    /* When present, the timeline is local to this track. */
    var track: Track?

    /* The TimeUnit used by this and nested timelines. If no TimeUnit is provided by this or the parent scope then
 'beats' will be used. */
    var timeUnit: TimeUnit?

    /* Unique string identifier of this element. This is used for referencing this instance from other elements. */
    var id: String?
    var name: String? // Name/label of this object.

    /* Color of this object in HTML-style format. (#rrggbb) */
    var color: String?
    var comment: String? // Comment/description of this object.
}

/* A Clip provides a clipped view on to a Timeline, and is used either on a Clips timeline (typically for arrangements) or inside a ClipSlot element (for clip launcher Scenes).
 A Clip must either have a child-element inheriting from Timeline or provide a ID reference to a timeline somewhere else (for linked/alias clips). */

struct Clip {

    /* Time on the parent timeline where this clips starts playing. */
    var time: double?

    /* Duration on the parent timeline of this clip. */
    var duration: double?

    /* The TimeUnit used by the scope inside this timeline. This affects the content/reference, playStart, playStop,
  loopStart, loopEnd but not time and duration which are using the TimeUnit of the parent scope. */
    var contentTimeUnit: TimeUnit?

    /* Time inside the content timeline (or reference) where the clip starts playing. */
    var playStart: Double?

    /* Time inside the content timeline (or reference) where the clip stops playing. */
    var playStop: Double?

    /* Time inside the content timeline (or reference) where the clip loop starts. */
    var loopStart: Double?

    /* Time inside the content timeline (or reference) where the clip loop ends. */
    var loopEnd: Double?

    /* The TimeUnit used by the fadeInTime and fadeOutTime. */
    var fadeTimeUnit: TimeUnit?
    var fadeInTime: Double? // Duration of fade-in
    var fadeOutTime: Double? // Duration of fade-out
    var content: Timeline? // Content Timeline this clip is playing.

    /* Reference to a Content Timeline this clip is playing, in case of linked/alias clips. You can use either content
 or reference for one clip, but not both. */
    var reference: Timeline?
    var name: String? // Name/label of this object.

    /* Color of this object in HTML-style format. (#rrggbb) */
    var color: String?
    var comment: String? // Comment/description of this object.
}

/* Timeline containing Notes (MIDI-style) */

struct Notes {
    var notes: [Note] // Contained notes.

    /* When present, the timeline is local to this track. */
    var track: Track?

    /* The TimeUnit used by this and nested timelines. If no TimeUnit is provided by this or the parent scope then
 'beats' will be used. */
    var timeUnit: TimeUnit?

    /* Unique string identifier of this element. This is used for referencing this instance from other elements. */
    var id: String?
    var name: String? // Name/label of this object.

    /* Color of this object in HTML-style format. (#rrggbb) */
    var color: String?
    var comment: String? // Comment/description of this object.
}

/* A single Note (MIDI-style).
 It can additionally contain child timelines to hold per-note expression. */

struct Note {

    /* Time on the parent timeline where this note starts playing. */
    var time: Double?

    /* Duration on the parent timeline of this note. */
    var duration: Double?
    var channel: int? // MIDI channel of this note.
    var key: int? // MIDI key of this note.

    /* Note On Velocity of this note. (normalized) */
    var velocity: Double?

    /* Note Off Velocity of this note. (normalized) */
    var releaseVelocity: Double?

    /* Per-note expressions can be stored within the note object as timelines. */
    var content: Timeline?
}

/* Representation of an audio file as a timeline. Duration should be the entire length of the file, any clipping
 should be done by placing the Audio element within a Clip element. The timeUnit attribute should always be set to
 seconds. */

struct Audio {
    var sampleRate: int? // Sample-rate of audio-file.

    /* Number of channels of audio-file (1=mono, 2=stereo...). */
    var channels: int?

    /* Playback algorithm used to warp audio (vendor-specific). */
    var algorithm: String?
    var file: FileReference // The media file.

    /* Duration in seconds of the media file (as stored). */
    var duration: double?

    /* When present, the timeline is local to this track. */
    var track: Track?

    /* The TimeUnit used by this and nested timelines. If no TimeUnit is provided by this or the parent scope then
 'beats' will be used. */
    var timeUnit: TimeUnit?

    /* Unique string identifier of this element. This is used for referencing this instance from other elements. */
    var id: String?
    var name: String? // Name/label of this object.

    /* Color of this object in HTML-style format. (#rrggbb) */
    var color: String?
    var comment: String? // Comment/description of this object.
}

/* Representation of a video file as a timeline. Duration should be the entire length of the file, any clipping
 should be done by placing the Audio element within a Clip element. The timeUnit attribute should always be set to
 seconds. */

struct Video {
    var sampleRate: int? // sample-rate of audio (if present)

    /* number of channels of audio (1=mono..., if present) */
    var channels: int?

    /* Playback algorithm used to warp audio (vendor-specific, if present) */
    var algorithm: String?
    var file: FileReference // The media file.

    /* Duration in seconds of the media file (as stored). */
    var duration: double?

    /* When present, the timeline is local to this track. */
    var track: Track?

    /* The TimeUnit used by this and nested timelines. If no TimeUnit is provided by this or the parent scope then
 'beats' will be used. */
    var timeUnit: TimeUnit?

    /* Unique string identifier of this element. This is used for referencing this instance from other elements. */
    var id: String?
    var name: String? // Name/label of this object.

    /* Color of this object in HTML-style format. (#rrggbb) */
    var color: String?
    var comment: String? // Comment/description of this object.
}

/* <p>Warps the time of nested content as defined by a list of <i>Warp</i> points.</p>

 <p>A typical use case would be to warp an audio-file (contentTimeUnit = seconds) onto a timeline defined in beats
 (timeUnit = beats) as defined by a set of Warp events.</p>

 <p>At least two Warp events need to present in order to define a usable beats/seconds conversion. For a plain
 fixed-speed mapping, provide two event: One at (0,0) and a second event with the desired beat-time length along
 with the length of the contained Audio file in seconds.</p>

 <pre><code>
   &lt;!-- example of a simple audio clip with beats-to-seconds warping --&gt;
   &lt;Clip time=&quot;0&quot; duration=&quot;8&quot;&gt;
     &lt;Warps contentTimeUnit=&quot;seconds&quot; timeUnit=&quot;beats&quot;&gt;
       &lt;Audio channels=&quot;1&quot; duration=&quot;4.657&quot; sampleRate=&quot;44100&quot;&gt;
          &lt;File path=&quot;samples/dummy.wav&quot;/&gt;
       &lt;/Audio&gt;
       &lt;Warp time=&quot;0&quot; contentTime=&quot;0&quot;/&gt;
       &lt;Warp time=&quot;8&quot; contentTime=&quot;4.657&quot;/&gt;
     &lt;/Warps&gt;
   &lt;/Clip&gt;
 </code></pre> */

struct Warps {

    /* Warp events defining the transformation. (minimum 2) */
    var events: [Warp]
    var content: Timeline // Content timeline to be warped.

    /* The TimeUnit used by the content (nested) timeline and the contentTime attribute of the Warp events */
    var contentTimeUnit: TimeUnit?

    /* When present, the timeline is local to this track. */
    var track: Track?

    /* The TimeUnit used by this and nested timelines. If no TimeUnit is provided by this or the parent scope then
 'beats' will be used. */
    var timeUnit: TimeUnit?

    /* Unique string identifier of this element. This is used for referencing this instance from other elements. */
    var id: String?
    var name: String? // Name/label of this object.

    /* Color of this object in HTML-style format. (#rrggbb) */
    var color: String?
    var comment: String? // Comment/description of this object.
}

/* A single warp event, which defines the time both on the outer scope (time) and the inner scope (contentTime). The
  time range between the Warp events are assumed to be linearly interpolated. */

struct Warp {

    /* The time this point represent to the 'outside' of the Warps element.
 The TimeUnit is defined by the parent Warps element timeUnit attribute
 or inherited from the parent element of the Warps container */
    var time: double?

    /* The time this point represent to the 'inside' of the Warps element.
 The TimeUnit is defined by the parent Warps element contentTimeUnit attribute */
    var contentTime: double?
}

/* Represents a timeline of cue-markers. */

struct Markers {
    var markers: [Marker] // Markers of this timeline.

    /* When present, the timeline is local to this track. */
    var track: Track?

    /* The TimeUnit used by this and nested timelines. If no TimeUnit is provided by this or the parent scope then
 'beats' will be used. */
    var timeUnit: TimeUnit?

    /* Unique string identifier of this element. This is used for referencing this instance from other elements. */
    var id: String?
    var name: String? // Name/label of this object.

    /* Color of this object in HTML-style format. (#rrggbb) */
    var color: String?
    var comment: String? // Comment/description of this object.
}

/* A single cue-marker. */

struct Marker {

    /* Time on the parent timeline of this marker. */
    var time: double?
    var name: String? // Name/label of this object.

    /* Color of this object in HTML-style format. (#rrggbb) */
    var color: String?
    var comment: String? // Comment/description of this object.
}

/* Represents a parameter which can provide a boolean (true/false) value and be used as an automation target. */

struct BoolParameter {
    var value: Boolean? // Boolean value for this parameter.

    /* Parameter ID as used by VST2 (index), VST3(ParamID) */
    var parameterID: Int?

    /* Unique string identifier of this element. This is used for referencing this instance from other elements. */
    var id: String?
    var name: String? // Name/label of this object.

    /* Color of this object in HTML-style format. (#rrggbb) */
    var color: String?
    var comment: String? // Comment/description of this object.
}

/* Represents an enumerated parameter which can provide a value and be used as an automation target. */

struct EnumParameter {
    var value: Int? // Index of the enum value.

    /* Number of entries in enum value. value will be in the range [0 .. count-1]. */
    var count: Int?
    var labels: String[]? // Labels of the individual enum values.

    /* Parameter ID as used by VST2 (index), VST3(ParamID) */
    var parameterID: Int?

    /* Unique string identifier of this element. This is used for referencing this instance from other elements. */
    var id: String?
    var name: String? // Name/label of this object.

    /* Color of this object in HTML-style format. (#rrggbb) */
    var color: String?
    var comment: String? // Comment/description of this object.
}

/* Represents an enumerated parameter which can provide a value and be used as an automation target. */

struct IntegerParameter {
    var value: Int? // Integer value for this parameter.

    /* Minimum value this parameter can have (inclusive). */
    var min: Int?

    /* Maximum value this parameter can have (inclusive). */
    var max: Int?

    /* Parameter ID as used by VST2 (index), VST3(ParamID) */
    var parameterID: Int?

    /* Unique string identifier of this element. This is used for referencing this instance from other elements. */
    var id: String?
    var name: String? // Name/label of this object.

    /* Color of this object in HTML-style format. (#rrggbb) */
    var color: String?
    var comment: String? // Comment/description of this object.
}

/* Represents a real valued (double) parameter which can provide a value and be used as an automation target. */

struct RealParameter {
    var value: Double? // Real (double) value for this parameter.

    /* Unit in which value, min and max are defined.
 <p>Using this rather than normalized value ranges allows transfer of parameter values and automation data.</p> */
    var unit: Unit?

    /* Minimum value this parameter can have (inclusive). */
    var min: Double?

    /* Maximum value this parameter can have (inclusive). */
    var max: Double?

    /* Parameter ID as used by VST2 (index), VST3(ParamID) */
    var parameterID: Int?

    /* Unique string identifier of this element. This is used for referencing this instance from other elements. */
    var id: String?
    var name: String? // Name/label of this object.

    /* Color of this object in HTML-style format. (#rrggbb) */
    var color: String?
    var comment: String? // Comment/description of this object.
}

/* Represents a (the) time-signature parameter which can provide a value and be used as an automation target. */

struct TimeSignatureParameter {

    /* Numerator of the time-signature. (3/4 &rarr; 3, 4/4 &rarr; 4) */
    var numerator: Int?

    /* Denominator of the time-signature. (3/4 &rarr; 4, 7/8 &rarr; 8) */
    var denominator: Int?

    /* Parameter ID as used by VST2 (index), VST3(ParamID) */
    var parameterID: Int?

    /* Unique string identifier of this element. This is used for referencing this instance from other elements. */
    var id: String?
    var name: String? // Name/label of this object.

    /* Color of this object in HTML-style format. (#rrggbb) */
    var color: String?
    var comment: String? // Comment/description of this object.
}

/* A timeline of points for automation or expression.
 <p>All the points should be of the same element-type and match the target.</p> */

struct Points {

    /* The parameter or expression this timeline should target. */
    var target: AutomationTarget

    /* The contained points. They should all be of the same type and match the target parameter. */
    var points: [Point]

    /* A unit should be provided for when used with RealPoint elements. */
    var unit: Unit?

    /* When present, the timeline is local to this track. */
    var track: Track?

    /* The TimeUnit used by this and nested timelines. If no TimeUnit is provided by this or the parent scope then
 'beats' will be used. */
    var timeUnit: TimeUnit?

    /* Unique string identifier of this element. This is used for referencing this instance from other elements. */
    var id: String?
    var name: String? // Name/label of this object.

    /* Color of this object in HTML-style format. (#rrggbb) */
    var color: String?
    var comment: String? // Comment/description of this object.
}

/* <p>Defines the target of automation or expression, usually used within a Points element.</p>

 <p>Either it points directly ot a parameter or an expression, and in the expression case
 it can either be monophonic (such as MIDI CCs) or per-note/polyphonic (such as poly pressure)</p> */

struct AutomationTarget {
    var parameter: Parameter? // Parameter to automate.
    var expression: ExpressionType? // Expression type to control.
    var channel: Int? // MIDI channel

    /* MIDI key. <p>Used when expression="polyPressure".</p> */
    var key: Int?

    /* MIDI Channel Controller Number (0 based index). <p>Used when expression="channelController".</p> */
    var controller: Int?
}

/*  */

struct RealPoint {
    var value: Double?

    /* Interpolation mode used for the segment starting at this point. Default to 'hold' when unspecified. */
    var interpolation: Interpolation?

    /* Time (within enclosing Points timeline) of this event */
    var time: Double?
}

/* A single automation point for a boolean value. */

struct BoolPoint {

    /* Boolean value of this point (true/false). */
    var value: Boolean?

    /* Time (within enclosing Points timeline) of this event */
    var time: Double?
}

/* A single automation point for an enumerated value. */

struct EnumPoint {

    /* Integer value of the Enum index for this point. */
    var value: Int?

    /* Time (within enclosing Points timeline) of this event */
    var time: Double?
}

/* A single automation point for an integer value. */

struct IntegerPoint {
    var value: Int? // Integer value of this point.

    /* Time (within enclosing Points timeline) of this event */
    var time: Double?
}

/* A single automation point for a time-signature value. */

struct TimeSignaturePoint {

    /* Numerator of the time-signature. (3/4 &rarr; 3, 4/4 &rarr; 4) */
    var numerator: Int?

    /* Denominator of the time-signature. (3/4 &rarr; 4, 7/8 &rarr; 8) */
    var denominator: Int?

    /* Time (within enclosing Points timeline) of this event */
    var time: Double?
}

/* Either a Plug-in or native Device with in a DAW. */

struct Device {

    /* This device is enabled (as in not bypassed). */
    var enabled: BoolParameter?
    var deviceRole: DeviceRole? // Role of this device/plug-in.

    /* If this device/plug-in is loaded/active of not. */
    var loaded: Boolean?
    var deviceName: String? // Name of the device/plugin

    /* Unique identifier of device/plug-in.
 For standards which use UUID as an identifier use the textual representation of the UUID (VST3).
 For standards which use an integer as an identifier use the value in decimal form. Base-10 unsigned. (VST2) */
    var deviceID: String?
    var deviceVendor: String? // Vendor name of the device/plugin

    /* Path to a file representing the device / plug-in state in its native format.
 <p>This file must be embedded inside the container ZIP and have the FileReference configured with (external=false).</p> */
    var state: FileReference?

    /* Parameters for this device, which is required for automated parameters in order to provide an ID. */
    var automatedParameters: [Parameter]

    /* Unique string identifier of this element. This is used for referencing this instance from other elements. */
    var id: String?
    var name: String? // Name/label of this object.

    /* Color of this object in HTML-style format. (#rrggbb) */
    var color: String?
    var comment: String? // Comment/description of this object.
}

struct AuPlugin {
    var pluginVersion: String? // Version of the plug-in

    /* This device is enabled (as in not bypassed). */
    var enabled: BoolParameter?
    var deviceRole: DeviceRole? // Role of this device/plug-in.

    /* If this device/plug-in is loaded/active of not. */
    var loaded: Boolean?
    var deviceName: String? // Name of the device/plugin

    /* Unique identifier of device/plug-in.
 For standards which use UUID as an identifier use the textual representation of the UUID (VST3).
 For standards which use an integer as an identifier use the value in decimal form. Base-10 unsigned. (VST2) */
    var deviceID: String?
    var deviceVendor: String? // Vendor name of the device/plugin

    /* Path to a file representing the device / plug-in state in its native format.
 <p>This file must be embedded inside the container ZIP and have the FileReference configured with (external=false).</p> */
    var state: FileReference?

    /* Parameters for this device, which is required for automated parameters in order to provide an ID. */
    var automatedParameters: [Parameter]

    /* Unique string identifier of this element. This is used for referencing this instance from other elements. */
    var id: String?
    var name: String? // Name/label of this object.

    /* Color of this object in HTML-style format. (#rrggbb) */
    var color: String?
    var comment: String? // Comment/description of this object.
}

/* A CLAP Plug-in instance.
 <p>The CLAP plug-in state should be stored in .clap-preset format.</p> */

struct ClapPlugin {
    var pluginVersion: String? // Version of the plug-in

    /* This device is enabled (as in not bypassed). */
    var enabled: BoolParameter?
    var deviceRole: DeviceRole? // Role of this device/plug-in.

    /* If this device/plug-in is loaded/active of not. */
    var loaded: Boolean?
    var deviceName: String? // Name of the device/plugin

    /* Unique identifier of device/plug-in.
 For standards which use UUID as an identifier use the textual representation of the UUID (VST3).
 For standards which use an integer as an identifier use the value in decimal form. Base-10 unsigned. (VST2) */
    var deviceID: String?
    var deviceVendor: String? // Vendor name of the device/plugin

    /* Path to a file representing the device / plug-in state in its native format.
 <p>This file must be embedded inside the container ZIP and have the FileReference configured with (external=false).</p> */
    var state: FileReference?

    /* Parameters for this device, which is required for automated parameters in order to provide an ID. */
    var automatedParameters: [Parameter]

    /* Unique string identifier of this element. This is used for referencing this instance from other elements. */
    var id: String?
    var name: String? // Name/label of this object.

    /* Color of this object in HTML-style format. (#rrggbb) */
    var color: String?
    var comment: String? // Comment/description of this object.
}

/* A VST2 Plug-in instance.
 <p>The VST2 plug-in state should be stored in FXB or FXP format.</p> */

struct Vst2Plugin {
    var pluginVersion: String? // Version of the plug-in

    /* This device is enabled (as in not bypassed). */
    var enabled: BoolParameter?
    var deviceRole: DeviceRole? // Role of this device/plug-in.

    /* If this device/plug-in is loaded/active of not. */
    var loaded: Boolean?
    var deviceName: String? // Name of the device/plugin

    /* Unique identifier of device/plug-in.
 For standards which use UUID as an identifier use the textual representation of the UUID (VST3).
 For standards which use an integer as an identifier use the value in decimal form. Base-10 unsigned. (VST2) */
    var deviceID: String?
    var deviceVendor: String? // Vendor name of the device/plugin

    /* Path to a file representing the device / plug-in state in its native format.
 <p>This file must be embedded inside the container ZIP and have the FileReference configured with (external=false).</p> */
    var state: FileReference?

    /* Parameters for this device, which is required for automated parameters in order to provide an ID. */
    var automatedParameters: [Parameter]

    /* Unique string identifier of this element. This is used for referencing this instance from other elements. */
    var id: String?
    var name: String? // Name/label of this object.

    /* Color of this object in HTML-style format. (#rrggbb) */
    var color: String?
    var comment: String? // Comment/description of this object.
}

/* A VST3 Plug-in instance.
 <p>The VST3 plug-in state should be stored in .vstpreset format.</p> */

struct Vst3Plugin {
    var pluginVersion: String? // Version of the plug-in

    /* This device is enabled (as in not bypassed). */
    var enabled: BoolParameter?
    var deviceRole: DeviceRole? // Role of this device/plug-in.

    /* If this device/plug-in is loaded/active of not. */
    var loaded: Boolean?
    var deviceName: String? // Name of the device/plugin

    /* Unique identifier of device/plug-in.
 For standards which use UUID as an identifier use the textual representation of the UUID (VST3).
 For standards which use an integer as an identifier use the value in decimal form. Base-10 unsigned. (VST2) */
    var deviceID: String?
    var deviceVendor: String? // Vendor name of the device/plugin

    /* Path to a file representing the device / plug-in state in its native format.
 <p>This file must be embedded inside the container ZIP and have the FileReference configured with (external=false).</p> */
    var state: FileReference?

    /* Parameters for this device, which is required for automated parameters in order to provide an ID. */
    var automatedParameters: [Parameter]

    /* Unique string identifier of this element. This is used for referencing this instance from other elements. */
    var id: String?
    var name: String? // Name/label of this object.

    /* Color of this object in HTML-style format. (#rrggbb) */
    var color: String?
    var comment: String? // Comment/description of this object.
}

struct BuiltinDevice {

    /* This device is enabled (as in not bypassed). */
    var enabled: BoolParameter?
    var deviceRole: DeviceRole? // Role of this device/plug-in.

    /* If this device/plug-in is loaded/active of not. */
    var loaded: Boolean?
    var deviceName: String? // Name of the device/plugin

    /* Unique identifier of device/plug-in.
 For standards which use UUID as an identifier use the textual representation of the UUID (VST3).
 For standards which use an integer as an identifier use the value in decimal form. Base-10 unsigned. (VST2) */
    var deviceID: String?
    var deviceVendor: String? // Vendor name of the device/plugin

    /* Path to a file representing the device / plug-in state in its native format.
 <p>This file must be embedded inside the container ZIP and have the FileReference configured with (external=false).</p> */
    var state: FileReference?

    /* Parameters for this device, which is required for automated parameters in order to provide an ID. */
    var automatedParameters: [Parameter]

    /* Unique string identifier of this element. This is used for referencing this instance from other elements. */
    var id: String?
    var name: String? // Name/label of this object.

    /* Color of this object in HTML-style format. (#rrggbb) */
    var color: String?
    var comment: String? // Comment/description of this object.
}

struct Equalizer {
    var bands: [EqBand]?
    var inputGain: RealParameter?
    var outputGain: RealParameter?

    /* This device is enabled (as in not bypassed). */
    var enabled: BoolParameter?
    var deviceRole: DeviceRole? // Role of this device/plug-in.

    /* If this device/plug-in is loaded/active of not. */
    var loaded: Boolean?
    var deviceName: String? // Name of the device/plugin

    /* Unique identifier of device/plug-in.
 For standards which use UUID as an identifier use the textual representation of the UUID (VST3).
 For standards which use an integer as an identifier use the value in decimal form. Base-10 unsigned. (VST2) */
    var deviceID: String?
    var deviceVendor: String? // Vendor name of the device/plugin

    /* Path to a file representing the device / plug-in state in its native format.
 <p>This file must be embedded inside the container ZIP and have the FileReference configured with (external=false).</p> */
    var state: FileReference?

    /* Parameters for this device, which is required for automated parameters in order to provide an ID. */
    var automatedParameters: [Parameter]

    /* Unique string identifier of this element. This is used for referencing this instance from other elements. */
    var id: String?
    var name: String? // Name/label of this object.

    /* Color of this object in HTML-style format. (#rrggbb) */
    var color: String?
    var comment: String? // Comment/description of this object.
}

struct EqBand {
    var freq: RealParameter
    var gain: RealParameter?
    var Q: RealParameter?
    var enabled: BoolParameter?
    var type: EqBandType?
    var order: Int?
}

/* A media file. (audio or video).
 <p>The duration attribute is intended to be provide the file length (and not be interpreted as a playback parameter, use a Clip or Warps element for that). </p> */

struct MediaFile {
    var file: FileReference // The media file.

    /* Duration in seconds of the media file (as stored). */
    var duration: double?

    /* When present, the timeline is local to this track. */
    var track: Track?

    /* The TimeUnit used by this and nested timelines. If no TimeUnit is provided by this or the parent scope then
 'beats' will be used. */
    var timeUnit: TimeUnit?

    /* Unique string identifier of this element. This is used for referencing this instance from other elements. */
    var id: String?
    var name: String? // Name/label of this object.

    /* Color of this object in HTML-style format. (#rrggbb) */
    var color: String?
    var comment: String? // Comment/description of this object.
}


