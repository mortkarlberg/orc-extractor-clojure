(ns orc-extractor.orc-file
  (:require [orc-extractor.orc-file-constants :as orc-const]
            [orc-extractor.binary-utils :as bin-utils]
            [clj-mmap]))

(defn read-file [filename]
  (println "orc-file.read-file called for file:" filename)

  (with-open [orc-file (clj-mmap/get-mmap filename)
              midi-out (clj-mmap/get-mmap "./data/extracted.mid")]
    (let [header-bytes (clj-mmap/get-bytes orc-file 0 8)]
      (println (str "First 8 bytes of orc-file, " (String. header-bytes)))
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
