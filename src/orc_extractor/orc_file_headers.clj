(ns orc-extractor.orc-file-headers
  (:require [orc-extractor.orc-file-constants :as orc-const]
            [orc-extractor.binary-utils :as bin-utils]))

(defn locate-headers
  "Parse all headers and return a collection of their locations."
  [orc-file-mmap]
  (println "hej")  
  (let [expected-headers [orc-const/RIFF_HEADER_START
                          orc-const/RIFF_HEADER_MIDI
                          orc-const/RIFF_HEADER_WAVE_START
                          orc-const/RIFF_HEADER_WAVE_DATA
                          orc-const/RIFF_HEADER_WAVE_VOYL]]
    (str expected-headers)))

(defn parse-header
  "Parse a RIFF-header and return the length of the following data chunk."
  [header, expected-header-name]
  (let [name-length (count expected-header-name)
        name (take name-length header)]
    (if (not= name (seq expected-header-name))
      (throw (IllegalArgumentException.
              (str "Expected header name " expected-header-name ", was " name))))
    (bin-utils/header-length (drop name-length header))))
