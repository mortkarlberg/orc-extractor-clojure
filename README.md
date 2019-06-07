# orc-extractor

Extract wave, midi and other data from orc-files (Voyetra Digital Orchestrator).

Made simple? Hopefully! This is a re-implementation of [this unfinished Phyton-project](https://github.com/Brygghuset/orc-extractor). For no other reason than me wanting to learn Clojure.

## Usage

FIXME: explanation

```bash
java -jar orc-extractor-0.1.0-standalone.jar [args]
```

## ORC File Description

All ORC files have the same format as RMI, but with some differences:

* ORC files don't use DLS sample banks.
* There is only one WAV track with all digital audio samples.
* Sample playback is controlled by Sequencer-Specific events in the MIDI sequence and by "voyl" chunk in the WAV track.

### RIFF Tree Structure

```text
File Root
│
├─ RIFF:RMID     - RIFF MIDI header
│  └─ data       - MIDI data chunk
│
└─ RIFF:WAVE     - RIFF Wave header
   ├─ fmt        - Wave format chunk
   ├─ voyl       - Voyetra special chunk
   └─ data       - Waveform data chunk
```

## MIDI

* All MIDI files begin with "MThd" which is the start of the MIDI header. There may also be sub-headers in the MIDI file. Inside each header there will be a "MTrk" which signifies the beginning of a track. Type-0 MIDI files only have 1 track, but type-1 files may contain multiple tracks.
* All properly formatted MIDI files should end with same three bytes, 0xFF 0x2F 0x00.
* MIDI can have a minimum tempo of 16 BPM and a maximum of 500 BPM.

## How to solve it

### MIDI implementation

1. read riff header ckID = RIFF (chID = A four-character code that identifies the representation of the chunk data. A program reading a RIFF file can skip over any chunk whose chunk ID it doesnt recognize; it simply skips the number of bytes specified by ckSize plus the pad byte, if present.)
2. read ckSize (A 32-bit unsigned value identifying the size of ckData. This size value does not include the size of the ckID or ckSize fields or the pad byte at the end of ckData.)
3. write ckData to new midi file, (ckData = Binary data of fixed or variable size. The start of ckData is word-aligned with respect to the start of the RIFF file. If the chunk size is an odd number of bytes, a pad byte with value zero is written after ckData. Word aligning improves access speed (for chunks resident in memory) and maintains compatibility with EA IFF. The ckSize value does not include the pad byte.)
(Source: <http://www.tactilemedia.com/info/MCI_Control_Info.html)>

### WAVE implementation

1. Extract each data chunk into three different files
2. Try and understand how wave chuck should be split to different channels
3. write channels to individual wav-files
4. how handle metadata, eg. pan and volume?

## Resources

### Domain

* <http://www.vgmpf.com/Wiki/index.php?title=ORC>
* <http://www.vgmpf.com/Wiki/index.php?title=RMI>
* <http://www.vgmpf.com/Wiki/index.php?title=MIDI>
* <http://www.vgmpf.com/Wiki/index.php?title=Digital_Orchestrator_Pro>
* RIFF explained - <http://www.tactilemedia.com/info/MCI_Control_Info.html>
* <http://soundfile.sapp.org/doc/WaveFormat/>
* <http://www.music.mcgill.ca/~ich/classes/mumt306/StandardMIDIfileformat.html>

### Technical sollution

* <https://www.oreilly.com/library/view/clojure-cookbook/9781449366384/ch04.html>
* <https://howtodoinjava.com/java7/nio/memory-mapped-files-mappedbytebuffer/>

## License

Copyright © 2019 Mårten Karlberg
