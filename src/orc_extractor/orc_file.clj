(ns orc-extractor.orc-file
  (:require [orc-extractor.orc-file-constants :as orc-const]
            [orc-extractor.binary-utils :as bin-utils]
            [clj-mmap]))

(defn read-file [filename]
  (println "orc-file.read-file called for file:" filename)

  (with-open [orc-file (clj-mmap/get-mmap filename)
              midi-out (clj-mmap/get-mmap "./data/extracted.mid")]

    ;TODO instead: check all headers and save in collection with start positions/length
    ;TODO: don't forget to handle the padding bytes
    ;TODO: write midi to its own file
    ;TODO: parse voyl data and use it when handling waves
    ;TODO: write each wave to its own file, maybe with padding zeroes to push it to its correct start point in the song
    ;TODO bonus: write a compound sequencer file from all data in some open format
    (let [header-bytes (clj-mmap/get-bytes orc-file 0 8)]
      (println (str "First 8 bytes of orc-file, " (String. header-bytes)))
      ;(check-header header-bytes "RIFF")
    )))

(defn parse-header
  "Parse a RIFF-header and return the length of the following data chunk."
  [header, expected-header-name]
  (let [name-length (count expected-header-name)
        name (take name-length header)]
    (if (not= name (seq expected-header-name))
      (throw (IllegalArgumentException.
              (str "Expected header name " expected-header-name ", was " name))))
    (bin-utils/header-length (drop name-length header))))

;(read-file "./data/Demosong.orc")
