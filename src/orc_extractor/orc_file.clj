(ns orc-extractor.orc-file
  (:require [clj-mmap]
            [orc-extractor.orc-file-headers :as headers]))

(defn read-file [filename]
  (println "orc-file.read-file called for file:" filename)

  (with-open [orc-file (clj-mmap/get-mmap filename)
              midi-out (clj-mmap/get-mmap "./data/extracted.mid")]
    (headers/locate-nested-headers orc-file)))

(comment
  (read-file "./data/GI2final.orc")
  (read-file "./data/HUND.orc")
  (read-file "./data/KATT.orc")
  (read-file "./data/NEUENO.orc")
  (read-file "./data/NEUID.orc")
  (read-file "./data/NYYYYPON.orc")
  (read-file "./data/Demosong.orc")
  (read-file "./data/Sample.orc"))
