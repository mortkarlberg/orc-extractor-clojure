(ns orc-extractor.midi-extractor
  (:require [clj-mmap]
            [clojure.string :as str]
            [orc-extractor.orc-file-constants :as orc-const]
            [clojure.java.io :as io]))

(defn write-midi-file [headers orc-file-mmap orc-file-path]
  (let [midi-file-path (str/replace orc-file-path ".orc" ".mid")
        midi-header (get headers orc-const/RIFF_HEADER_MIDI)
        start (+ (:start-index midi-header) (:header-size midi-header))
        size (:chunk-size midi-header)
        midi-data (clj-mmap/get-bytes orc-file-mmap start size)]
    (with-open [file (io/output-stream midi-file-path)]
      (.write file midi-data))))
