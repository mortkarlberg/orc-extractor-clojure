(ns orc-extractor.orc-file-headers
  (:require [orc-extractor.orc-file-constants :as orc-const]
            [orc-extractor.binary-utils :as bin-utils]
            [clj-mmap]))

(def defualt-expected-headers [[orc-const/RIFF_HEADER_START
                                orc-const/RIFF_HEADER_MIDI]
                               [orc-const/RIFF_HEADER_START
                                orc-const/RIFF_HEADER_WAVE_START
                                orc-const/RIFF_HEADER_WAVE_VOYL
                                orc-const/RIFF_HEADER_WAVE_DATA]])

(defn parse-header
  "Parse a RIFF-header and return the length of the following data chunk."
  [header, expected-header-name]
  (println "Header to parse" header)
  (println "Expected header name" expected-header-name)
  (let [name-length (count expected-header-name)
        name (take name-length header)]
    (if (not= name (seq expected-header-name))
      (throw (IllegalArgumentException.
              (str "Expected header name " expected-header-name ", was " name ", from " header))))
    (bin-utils/header-length (drop name-length header))))

(defn build-location
  [header-name start-index chunk-size]
  {:header-name header-name
   :start-index start-index
   :chunk-size chunk-size})

(defn offset-and-pad-index
  [index & offsets]
  (let [sum (apply + index offsets)]
    (if (even? sum)
      sum
      (+ sum 1))))

(defn locate-headers
  "Parse all headers and return a collection of their locations."
  [orc-file-mmap expected-headers & [located-headers start-index]]
  (let [header-name (first expected-headers)
        header-length (+ orc-const/RIFF_HEADER_SIZE_BYTES (count header-name))
        start (or start-index 0)
        bytes (clj-mmap/get-bytes orc-file-mmap start header-length)
        chunk-size (parse-header bytes header-name)
        location (build-location header-name start chunk-size)
        located (conj (or located-headers []) location)
        next-start (offset-and-pad-index start header-length chunk-size)
        next-headers (next expected-headers)]
    (println "Inner start" start)
    (println start "Inner expected headers" expected-headers)
    (println start "Inner header name" header-name)
    (println start "Inner located" located)
    (println start "Inner chunk size" chunk-size)
    (println start "Inner location" location)
    (println start "Inner next start" next-start)
    (println start "Inner next headers" next-headers)
    (if next-headers
      (locate-headers orc-file-mmap next-headers located next-start)
      located)))

(defn locate-nested-headers
  "Parse all nested headers and return a flat collection of their locations."
  [orc-file-mmap & [expected-headers located-headers start-index]]
  (println "######################")
  (let [headers (or expected-headers defualt-expected-headers)
        prev-located (or located-headers [])
        start (or start-index 0)
        nested (first headers)
        outer (first nested)
        outer-located (first (locate-headers orc-file-mmap [outer] prev-located start))
        inner (next nested)
        outer-size (+ orc-extractor.orc-file-constants/RIFF_HEADER_SIZE_BYTES (count outer))
        inner-start (+ start outer-size)
        located (locate-headers orc-file-mmap inner prev-located inner-start)
        next-start (offset-and-pad-index start outer-size (outer-located :start-index) (outer-located :chunk-size))
        next-nested (next headers)]
    (println "Nested start" start)
    (println start "Headers" headers)
    (println start "Nested headers" nested)
    (println start "Outer header located" outer-located)
    (println start "Expected inner headers" inner)
    (println start "Inner start index" inner-start)
    (println start "All located" located)
    (println start "Next nested" next-nested)
    (println start "Next nested start" next-start)
    (if next-nested
      (locate-nested-headers orc-file-mmap next-nested located next-start)
      located)))
