(ns orc-extractor.wave-extractor
  (:require [clj-mmap]
            [clojure.java.io :as io]
            [clojure.string :as str]
            [orc-extractor.binary-utils :as bin-utils]
            [orc-extractor.orc-file-constants :as orc-const]))

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
    (bin-utils/int-to-4-byte-array riff-size)))

(defn- write-riff-header [file headers]
  (.write file orc-const/RIFF_HEADER_START)
  (.write file (calculate-riff-size headers)))

(defn- write-data [file header orc-file-mmap]
  ;TODO write header
  ;TODO write data
  )

;http://soundfile.sapp.org/doc/WaveFormat/
(defn write-wave-file
  [headers orc-file-mmap orc-file-path]
  (println "Headers:" headers)
  (let [wave-file-path (str/replace orc-file-path ".orc" ".wav")]
    (with-open [file (io/output-stream wave-file-path)]
      (write-riff-header file headers)
      (write-data file (orc-const/RIFF_HEADER_WAVE_START headers) orc-file-mmap)
      (write-data file (orc-const/RIFF_HEADER_WAVE_DATA headers) orc-file-mmap))))

(defn write-voyl-file
  [headers orc-file-mmap orc-file-path]
  (let [voyl-file-path (str/replace orc-file-path ".orc" ".voyl")
        voyl-header    (get headers orc-const/RIFF_HEADER_WAVE_VOYL)
        start          (+ (:start-index voyl-header) (:header-size voyl-header))
        size           (:chunk-size voyl-header)
        voyl-data      (clj-mmap/get-bytes orc-file-mmap start size)]
    (with-open [file (io/output-stream voyl-file-path)]
      (.write file voyl-data))))
