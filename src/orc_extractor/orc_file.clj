(ns orc-extractor.orc-file
  (:require [clojure.java.io :as io]))

(defn read-file [orc-file]
  (println "orc-file.read-file called for file:" orc-file)
  (with-open [orc-in (io/input-stream orc-file)
              midi-out (io/output-stream (io/file "./data/extracted.mid"))]
    (io/copy orc-in midi-out)
))

(read-file (io/file "./data/Demosong.orc"))
