(ns orc-extractor.orc-file
  (:require [clj-mmap]
            [orc-extractor.midi-extractor :as midi]
            [orc-extractor.wave-extractor :as wave]
            [orc-extractor.orc-file-headers :as orc-headers]))

(defn read-file [orc-file-path]
  (println "orc-file.read-file called for file:" orc-file-path)

  (with-open [orc-file-mmap (clj-mmap/get-mmap orc-file-path)]
    (let [headers (orc-headers/locate-nested-headers orc-file-mmap)]
      (midi/write-midi-file headers orc-file-mmap orc-file-path)
      (wave/write-wave-file headers orc-file-mmap orc-file-path))))

(comment
  (read-file "./data/GI2final.orc")
  (read-file "./data/HUND.orc")
  (read-file "./data/KATT.orc")
  (read-file "./data/NEUENO.orc")
  (read-file "./data/NEUID.orc")
  (read-file "./data/NYYYYPON.orc")
  (read-file "./data/Demosong.orc")
  (read-file "./data/Sample.orc"))
