(ns orc-extractor.orc-file
  (:require [clj-mmap]
            [orc-extractor.orc-file-headers :as headers]))

(defn read-file [filename]
  (println "orc-file.read-file called for file:" filename)

  (with-open [orc-file (clj-mmap/get-mmap filename)
              midi-out (clj-mmap/get-mmap "./data/extracted.mid")]
    (let [bytes (clj-mmap/get-bytes orc-file 0 8)]
      (println (str "First 8 bytes of orc-file, " (String. bytes)))
    )))

;(read-file "./data/Demosong.orc")
