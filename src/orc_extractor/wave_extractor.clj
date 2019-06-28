(ns orc-extractor.wave-extractor
  (:require [clj-mmap]
            [clojure.string :as str]
            [orc-extractor.orc-file-constants :as orc-const]
            [clojure.java.io :as io]))

;http://soundfile.sapp.org/doc/WaveFormat/
(defn write-wave-file [headers orc-file-mmap orc-file-path]
  (println "Headers:" headers)
  (let [wave-file-path (str/replace orc-file-path ".orc" ".wav")]
    (with-open [file (io/output-stream wave-file-path)]
      ;TODO calculate total riff size from fmt and data headers
      (.write file orc-extractor.orc-file-constants/RIFF_HEADER_START)
      ;TODO write total riff size as little endian byte array
      ;TODO write fmt header
      ;TODO write fmt data
      ;TODO write data header
      ;TODO write wave data
      )))
