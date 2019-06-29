(ns orc-extractor.wave-extractor
  (:require [clj-mmap]
            [clojure.string :as str]
            [orc-extractor.orc-file-constants :as orc-const]
            [clojure.java.io :as io]))

(defn- pad-size [size]
  (if (even? size)
    size
    (+ size 1)))

(defn- calculate-riff-size [headers]
  (let [fmt-header  (get headers orc-const/RIFF_HEADER_WAVE_START)
        data-header (get headers orc-const/RIFF_HEADER_WAVE_DATA)
        riff-size   (+ (:header-size fmt-header)
                       (pad-size (:chunk-size fmt-header))
                       (:header-size data-header)
                       (pad-size (:chunk-size data-header)))]
    ;TODO transform riff-size to little endian byte array
    (println (byte-array [riff-size]))
    (byte-array [riff-size])))

(defn- write-riff-header [file headers]
  (.write file orc-const/RIFF_HEADER_START)
  (.write file (calculate-riff-size headers)))

;http://soundfile.sapp.org/doc/WaveFormat/
(defn write-wave-file
  [headers orc-file-mmap orc-file-path]
  (println "Headers:" headers)
  (let [wave-file-path (str/replace orc-file-path ".orc" ".wav")]
    (with-open [file (io/output-stream wave-file-path)]
      (write-riff-header file headers)
      ;TODO write fmt header
      ;TODO write fmt data
      ;TODO write data header
      ;TODO write wave data
      )))

(defn write-voyl-file
  [headers orc-file-mmap orc-file-path]
  (let [voyl-file-path (str/replace orc-file-path ".orc" ".voyl")
        voyl-header    (get headers orc-const/RIFF_HEADER_WAVE_VOYL)
        start          (+ (:start-index voyl-header) (:header-size voyl-header))
        size           (:chunk-size voyl-header)
        voyl-data      (clj-mmap/get-bytes orc-file-mmap start size)]
    (with-open [file (io/output-stream voyl-file-path)]
      (.write file voyl-data))))
