# .dawproject

Open exchange format for user data between different Digital Audio Workstations (DAWs)

## Motivation

The .dawproject format wants to provide a (vendor-agnostic) way of transferring user data between different music applications (DAWs).

Currently, the choices available for this task is extremely limited. 
Standard MIDI files can represent note data, but it is often a lower-level representation (no ramps) of data than the DAW uses internally which forces consolidation on export. AAF only covers audio and doesn't have any concept of musical-time so it's unsuited for musical data. Most plug-ins do allow you to save presets to a shared location, but this has to be done for each instance. What most users end up doing is just exporting audio as stems.

The aim of this project is to export all that data (audio/note/automation/plug-in) along with the structure surrounding it into a single .dawproject file.

## Status

The format is being actively developed and will probably undergo structural changes

## Goals

* Package all user data of a project/song into a single file.
  * Audio timeline data
  * Note timeline data
  * Note expression data
  * Automation timeline data
  * Audio data (embedded or referenced)
  * Plug-in states (always embedded)
* The format should be able to preserve as much user created data as feasible.
* The format should be able to express the timeline structure of the exporting DAW as is, leaving it up to the importer to use this data and flatten it as needed.
* Simple to implement
* Built upon established open standards
* Language agnostic, no special dependencies
* Open & free

## Non-goals

* Being used as a native file-format for a DAW
* Optimal performance (like a binary format could provide)
* Adopting limitations from MIDI
* Storing non-session data (view settings, preferences)

## Structure

* File Extension: .dawproject
* Container: ZIP
* Format: XML (project.xml, metadata.xml)
* Text encoding: UTF-8
  
Apart from the location of the XML files, the exporting DAW is free to choose the directory structure it wants.

## Tracks and Channels

Some DAWs treat these as separate entities and some do not, so in order to express the structure of all products, they have been exposed as separate entities.
Track represents the sequencer part, what holds timeline data.
Channel represents the mixing part, and holds settings for mixing, instruments & effects, routing and so on.

A Track will have a routing to a single Channel. A single property *isTrackChannel* defines if there’s a 1-to-1 mapping between a channel and the track sending to it.

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

Each clip contains:
* A timeline, or a reference to a timeline (to represent aliases).
* A list of warps (optional)  

### Warps
Contains a list of warp-markers where. 
This is normally used to represent time-warping of audio, where the "inside" of a clip has a different time-representation than the "outside". 

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
<!-- note data -->
<lanes>
  <lanes track = "...">
    <clips>
      <!-- note clip -->
      <clip time="8" duration="8" />
        <notes id="5">
          <note time="3" duration="0.5" key="55" vel="0.8" />
        </notes>
      </clip>

      <!-- alias clip -->
      <clip time="24" uration="8" reference="5"/>
    </clips>
  </lanes>
</lanes>

<!-- audio data -->
<lanes timebase="beats">
  <lanes track = "...">
    <clips>
      
      <!-- audio clip with un-warped audio  -->
      <clip time="0" duration="4.657">
        <audio path="samples/dummy.way" duration="4.657" timebase="seconds"/>
      </clip>
      
      <!-- audio clip with beats-to-seconds warping  -->
      <clip time="0" duration="8">
        <warps id = "5" timebase="seconds">
          <audio path="samples/dummy.way" duration="4.657"/>
          <warp time="0" warped="0"/>
          <warp time="8" warped="4.657"/>
        </warps>        
      </clip>
      
      <!-- clip with nested audio clips -->
      <clip time="24" duration="8">
        <clips>
          <clip time="0" duration="2">
            <!-- clip content -->
          </clip>
        </clips>
      </clip>

      <!-- clip with local automation/expression -->
      <clip time="24" duration="8">
        <lanes>
          <points parameter="...">
            <point time="0" value="0.2"/>
            <point time="20" value="0.7"/>
          </points>
          <audio/> <!-- clip content -->
        </lanes>         
      </clip>
    </clips>
  </lanes>
</lanes>
```
