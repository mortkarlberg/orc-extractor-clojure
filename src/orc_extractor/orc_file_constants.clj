(ns orc-extractor.orc-file-constants)

(def RIFF_HEADER_START (.getBytes "RIFF"))
(def RIFF_HEADER_MIDI (.getBytes "RMIDdata"))
(def RIFF_HEADER_WAVE_START (.getBytes "WAVEfmt "))
(def RIFF_HEADER_WAVE_VOYL (.getBytes "voyl"))
(def RIFF_HEADER_WAVE_DATA (.getBytes "data"))
(def ^:const RIFF_HEADER_SIZE_BYTES 4)
