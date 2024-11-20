# DAWproject

Open exchange format for user data between Digital Audio Workstations (DAWs)

## Motivation

The DAWproject format provides a (vendor-agnostic) way of transferring user data between different music applications (DAWs).

Currently, there is no file-format which is purpose-built for this task.
Standard MIDI files can represent note data, but it is often a lower-level representation (no ramps) of data than what the DAW uses internally, which forces consolidation on export. AAF only covers audio and doesn't have any concept of musical-time, which limits it to post-audio workflows . Most plug-ins do allow you to save presets to a shared location, but this has to be done for each instance. What most users end up doing is just exporting audio as stems.

The aim of this project is to export all translatable project data (audio/note/automation/plug-in) along with the structure surrounding it into a single DAWproject file.

The table below aims to explain the scope format from a music-production perspective and how it compares to other methods of data transfer.

|                                 |                                                               DAWproject                                                               |                      Standard MIDI Files                      |                    Advanced Authoring Format (AAF)                    |
|---------------------------------|:--------------------------------------------------------------------------------------------------------------------------------------:|:-------------------------------------------------------------:|:---------------------------------------------------------------------:|
| Intended Use                    |                                                            Music Production                                                            |                        MIDI Sequencing                        |                         Video Post-Production                         |
| Time Format<br/>(seconds/beats) |                                                   Beats and seconds can be combined                                                    |                             Beats                             |                                Seconds                                |
| Audio                           |                  Audio<br/>Events/Clips<br/>Fades<br/>Crossfades<br/>Amplitude<br/>Pan<br/>Time Warping<br/>Transpose                  |                               -                               | Audio<br/>Events/Clips<br/>Fades<br/>Crossfades<br/>Amplitude<br/>Pan |
| Notes                           |                                                       Notes<br/>Note Expressions                                                       |                             Notes                             |                                   -                                   |
| Automation                      | Tempo<br/>Time Signature<br/>MIDI Messages<br/>Volume<br/>Pan<br/>Mute<br/>Sends<br/>Plug-in Parameters<br/>Built-in Device Parameters | Tempo<br/>Time Signature<br/>MIDI Messages<br/>SysEx Messages |              Volume<br/>Pan<br/>Video Related Parameters              |
| Plug-ins                        |                                       Stores full plug-in state<br/>and automation of parameters                                       |                               -                               |                                   -                                   |
| Built-in Devices                |                                 Generic EQ<br/>Generic Compressor<br/>Generic Gate<br/>Generic Limiter                                 |                               -                               |                                   -                                   |
| Clip Launcher                   |                                                            Clips<br/>Scenes                                                            |                               -                               |                                   -                                   |

## Status

The format is version 1.0 and is stable. 

## Goals

* Package all user data of a project/song into a single file.
  * Audio timeline data
  * Note timeline data
  * Note expression data
  * Automation timeline data
  * Audio data (embedded or referenced)
  * Plug-in states (always embedded)
* The format should be able to preserve as much user created data as feasible.
* The format should be able to express the track and timeline structures of the exporting DAW as is, leaving it up to the importer to use this data and flatten it as needed.
* Simple to implement
* Built upon established open standards
* Language agnostic, no special dependencies
* Open & free

## Non-goals

* Being the native file-format for a DAW
* Optimal performance (like a binary format could provide)
* Storing low-level MIDI events directly (but rather relying on higher level abstractions)
* Storing non-session data (view settings, preferences) 

## Format Specification

* File Extension: .dawproject
* Container: ZIP
* Format: XML (project.xml, metadata.xml)
* Text encoding: UTF-8
* The exporting DAW is free to choose the directory structure it wants for media and plug-in files.

* [DAWproject XML Reference](https://htmlpreview.github.io/?https://github.com/bitwig/dawproject/blob/main/Reference.html)
* [Project XML Schema](Project.xsd)
* [MetaData XML Schema](MetaData.xsd)

## Language Support

DAWproject is based on plain XML/ZIP and can be used with any programming language that can parse those.

The DOM of DAWproject is defined by a set of Java classes which have XML-related annotations and HTML-induced Javadoc comments.
Those are used (via reflection) to generate XML Documentation and Schemas. Potentially, the same approach could be used to generate code for other languages (contributions welcome).

## Building the Library, Documentation and Tests

Requires Java Runtime version 16 or later.

To build (using Gradle):

```
./gradlew build
```

## Example project

The exporting application is free to structure tracks and timelines in a way that fits its internal model.
The choice is left to the importing application to either use the level of structure provided (if applicable) or to flatten/convert it to match its model. 

As an example, here's the project.xml of a simple file saved in Bitwig Studio 5.0 with one instrument track and one audio track. As the audio clips in Bitwig Studio are themselves a timeline of audio events, you will notice that there are two levels of <Clips> elements, id25 representing the clip timeline on the arrangement, and id26 representing the  audio events inside the clip.

```xml
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<Project version="1.0">
  <Application name="Bitwig Studio" version="5.0"/>
  <Transport>
    <Tempo max="666.000000" min="20.000000" unit="bpm" value="149.000000" id="id0" name="Tempo"/>
    <TimeSignature denominator="4" numerator="4" id="id1"/>
  </Transport>
  <Structure>
    <Track contentType="notes" loaded="true" id="id2" name="Bass" color="#a2eabf">
      <Channel audioChannels="2" destination="id15" role="regular" solo="false" id="id3">
        <Devices>
          <ClapPlugin deviceID="org.surge-synth-team.surge-xt" deviceName="Surge XT" deviceRole="instrument" loaded="true" id="id7" name="Surge XT">
            <Parameters/>
            <Enabled value="true" id="id8" name="On/Off"/>
            <State path="plugins/d19b1f6e-bbb6-42fe-a6c9-54b41d97a05d.clap-preset"/>
          </ClapPlugin>
        </Devices>
        <Mute value="false" id="id6" name="Mute"/>
        <Pan max="1.000000" min="0.000000" unit="normalized" value="0.500000" id="id5" name="Pan"/>
        <Volume max="2.000000" min="0.000000" unit="linear" value="0.659140" id="id4" name="Volume"/>
      </Channel>
    </Track>
    <Track contentType="audio" loaded="true" id="id9" name="Drumloop" color="#b53bba">
      <Channel audioChannels="2" destination="id15" role="regular" solo="false" id="id10">
        <Mute value="false" id="id13" name="Mute"/>
        <Pan max="1.000000" min="0.000000" unit="normalized" value="0.500000" id="id12" name="Pan"/>
        <Volume max="2.000000" min="0.000000" unit="linear" value="0.177125" id="id11" name="Volume"/>
      </Channel>
    </Track>
    <Track contentType="audio notes" loaded="true" id="id14" name="Master">
      <Channel audioChannels="2" role="master" solo="false" id="id15">
        <Mute value="false" id="id18" name="Mute"/>
        <Pan max="1.000000" min="0.000000" unit="normalized" value="0.500000" id="id17" name="Pan"/>
        <Volume max="2.000000" min="0.000000" unit="linear" value="1.000000" id="id16" name="Volume"/>
      </Channel>
    </Track>
  </Structure>
  <Arrangement id="id19">
    <Lanes timeUnit="beats" id="id20">
      <Lanes track="id2" id="id21">
        <Clips id="id22">
          <Clip time="0.0" duration="8.0" playStart="0.0">
            <Notes id="id23">
              <Note time="0.000000" duration="0.250000" channel="0" key="65" vel="0.787402" rel="0.787402"/>
              <Note time="1.000000" duration="0.250000" channel="0" key="65" vel="0.787402" rel="0.787402"/>
              <Note time="4.000000" duration="0.250000" channel="0" key="65" vel="0.787402" rel="0.787402"/>
              <Note time="5.000000" duration="0.250000" channel="0" key="65" vel="0.787402" rel="0.787402"/>
              <Note time="0.500000" duration="0.250000" channel="0" key="64" vel="0.787402" rel="0.787402"/>
              <Note time="4.500000" duration="0.250000" channel="0" key="64" vel="0.787402" rel="0.787402"/>
              <Note time="1.500000" duration="2.500000" channel="0" key="53" vel="0.787402" rel="0.787402"/>
              <Note time="5.500000" duration="0.250000" channel="0" key="53" vel="0.787402" rel="0.787402"/>
              <Note time="6.000000" duration="2.000000" channel="0" key="53" vel="0.787402" rel="0.787402"/>
            </Notes>
          </Clip>
        </Clips>
      </Lanes>
      <Lanes track="id9" id="id24">
        <Clips id="id25">
          <Clip time="0.0" duration="8.00003433227539" playStart="0.0" loopStart="0.0" loopEnd="8.00003433227539" fadeTimeUnit="beats" fadeInTime="0.0" fadeOutTime="0.0" name="Drumfunk3 170bpm">
            <Clips id="id26">
              <Clip time="0.0" duration="8.00003433227539" contentTimeUnit="beats" playStart="0.0" fadeTimeUnit="beats" fadeInTime="0.0" fadeOutTime="0.0">
                <Warps contentTimeUnit="seconds" timeUnit="beats" id="id28">
                  <Audio algorithm="stretch" channels="2" duration="2.823541666666667" sampleRate="48000" id="id27">
                    <File path="audio/Drumfunk3 170bpm.wav"/>
                  </Audio>
                  <Warp time="0.0" contentTime="0.0"/>
                  <Warp time="8.00003433227539" contentTime="2.823541666666667"/>
                </Warps>
              </Clip>
            </Clips>
          </Clip>
        </Clips>
      </Lanes>
      <Lanes track="id14" id="id29">
        <Clips id="id30"/>
      </Lanes>
    </Lanes>
  </Arrangement>
  <Scenes/>
</Project>
```

## DAW Support

DAWproject 1.0 is currently supported by the following DAWs

* Bitwig Studio 5.0.9
* PreSonus Studio One 6.5
* Steinberg Cubase 14

## Converters

There are various tools that can convert from and to DAWproject files

* https://github.com/SatyrDiamond/DawVert: various formats
* https://github.com/git-moss/ProjectConverter: Cockos Reaper
