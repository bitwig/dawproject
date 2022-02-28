# .dawproject

Open exchange format for user data between Digital Audio Workstations (DAWs)

## Motivation

The .dawproject format wants to provide a (vendor-agnostic) way of transferring user data between different music applications (DAWs).

Currently, the choices available for this task are rather limited.
Standard MIDI files can represent note data, but it is often a lower-level representation (no ramps) of data than what the DAW uses internally, which forces consolidation on export. AAF only covers audio and doesn't have any concept of musical-time so it's unsuited for musical data. Most plug-ins do allow you to save presets to a shared location, but this has to be done for each instance. What most users end up doing is just exporting audio as stems.

The aim of this project is to export all that data (audio/note/automation/plug-in) along with the structure surrounding it into a single .dawproject file.

## Status

The format is being actively developed and will still undergo structural changes. 

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

* Being used as a native file-format for a DAW
* Optimal performance (like a binary format could provide)
* Adopting limitations from MIDI
* Storing non-session data (view settings, preferences)

## Enable experimental support in Bitwig Studio (4.0 Beta 3 or later)

Create a file named config.json with the following content inside you user settings directory.

```json
dawproject : true
```

The user settings directory is different on each platform

* Windows: %LOCALAPPDATA%/Bitwig Studio
* Mac: Library/Application Support/Bitwig/Bitwig Studio
* Linux: ~/.BitwigStudio

This will add an "Export Project..." entry in the FILE menu and allow .dawproject files to be opened.   

## Structure

* File Extension: .dawproject
* Container: ZIP
* Format: XML (project.xml, metadata.xml)
* Text encoding: UTF-8

Apart from the location of the XML files, the exporting DAW is free to choose the directory structure it wants.

## Devices / Plug-ins
Plug-in states are stored as files in their respective standard format (fxp/fxb/vstpreset) inside the container and referenced using paths.

For a future version a set of templates could be considered to cover the parameters set of standard effects & instruments (eq / compressor / sampler etc etc) to make these transferable between different DAWs.

## Timeline data types

### General

The timeline base-class has two attributes:

* timebase: assign the content of this timeline to a specific timebase [beats or seconds] (optional)
* track: assign the content of this timeline to a specific track (optional)

### Lanes
Contains a list of lanes, which can be of any timeline type.

### Markers
Contains a list of cue markers.

### Clips
Contains a list of clips.
Each clip contains a timeline, or alternatively a reference to a timeline which can be used to represent aliases.

### Warps
Contains a nested timeline along with a list of warp-markers which provides a remapping of time inside the warps object, which is usually a different timebase than the parent context.

This is typically used to represent time-warping of audio.

### Notes
Contains a list of notes.

### Audio
Contains a reference to an audio file (embedded or referenced) and settings for how it is to be played back.

### Video
Same as audio.

### Points
Contains a list of automation points along with a reference to the parameter being automated.

## Typical timeline structures

The exporting application is free to structure timelines in a way that fits its internal model.
The choice is left to the importing application to either use the level of structure provided (if applicable) or to flatten it.

Some examples (pseudo-xml):

```xml
<project>

  <!-- .... -->

  <arrangement timebase="beats"> <!-- the arrangement -->
    <lanes>
      <!-- note track -->
      <lanes track = "id of note track...">
        <clips>
          <!-- note clip -->
          <clip time="8" duration="8">
            <notes id="5">
              <note time="3" duration="0.5" key="55" vel="0.8" />
            </notes>
          </clip>

          <!-- alias clip -->
          <clip time="24" duration="8" reference="5"/>
        </clips>

        <!-- track-level automation -->
        <points parameter="id of parameter...">
          <point time="0" value="0"/>
          <point time="8" value="1.0"/>
        </points>
      </lanes>

      !-- audio track -->
      <lanes track = "id of audio track...">
        <clips>

          <!-- audio clip with un-warped audio  -->
          <clip time="0" duration="4.657">
            <audio path="samples/dummy.wav" duration="4.657" timebase="seconds"/>
          </clip>

          <!-- audio clip with beats-to-seconds warping  -->
          <clip time="0" duration="8">
            <warps timebase="seconds">
              <audio path="samples/dummy.wav" duration="4.657"/>
              <warp time="0" warped="0"/>
              <warp time="8" warped="4.657"/>
            </warps>
          </clip>

          <!-- clip with nested audio clips -->
          <clip time="24" duration="8">
            <clips>
              <clip time="0" duration="4.657">
                <audio path="samples/dummy.wav" duration="4.657" timebase="seconds"/>
              </clip>
              <clip time="20" duration="4.657">
                <audio path="samples/dummy.wav" duration="4.657" timebase="seconds"/>
              </clip>
            </clips>
          </clip>

          <!-- clip with local automation/expression -->
          <clip time="24" duration="8">
            <lanes>
              <points parameter="id of parameter...">
                <point time="0" value="0.2"/>
                <point time="20" value="0.7"/>
              </points>
              <audio/> <!-- clip content -->
            </lanes>
          </clip>
        </clips>
      </lanes>
   </lanes>
  </arrangement>
</project>

```
