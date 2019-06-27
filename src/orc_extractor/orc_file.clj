(ns orc-extractor.orc-file
  (:require [clj-mmap]
            [clojure.string :as str]
            [orc-extractor.orc-file-constants :as orc-const]
            [clojure.java.io :as io]
            [orc-extractor.orc-file-headers :as orc-headers]))

(defn write-midi-file [headers orc-file-mmap orc-file-path]
  (let [midi-file-path (str/replace orc-file-path ".orc" ".mid")
        midi-header (get headers orc-const/RIFF_HEADER_MIDI)
        start (+ (:start-index midi-header) (:header-size midi-header))
        size (:chunk-size midi-header)
        midi-data (clj-mmap/get-bytes orc-file-mmap start size)]
    (with-open [file (io/output-stream midi-file-path)]
      (.write file midi-data))))

;http://soundfile.sapp.org/doc/WaveFormat/
(defn write-wave-file [headers orc-file-mmap orc-file-path]
  (let [wave-file-path (str/replace orc-file-path ".orc" ".wav")]
    (with-open [file (io/output-stream wave-file-path)]
      ;TODO calculate total riff size from fmt and data headers
      ;TODO write RIFF header
      ;TODO write fmt header
      ;TODO write fmt data
      ;TODO write data header
      ;TODO write wave data
      )))

(defn read-file [orc-file-path]
  (println "orc-file.read-file called for file:" orc-file-path)

  (with-open [orc-file-mmap (clj-mmap/get-mmap orc-file-path)]
    (let [headers (orc-headers/locate-nested-headers orc-file-mmap)]
      (write-midi-file headers orc-file-mmap orc-file-path))))

(comment
  (read-file "./data/GI2final.orc")
  (read-file "./data/HUND.orc")
  (read-file "./data/KATT.orc")
  (read-file "./data/NEUENO.orc")
  (read-file "./data/NEUID.orc")
  (read-file "./data/NYYYYPON.orc")
  (read-file "./data/Demosong.orc")
  (read-file "./data/Sample.orc"))
