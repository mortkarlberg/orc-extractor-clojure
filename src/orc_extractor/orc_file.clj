(ns orc-extractor.orc-file
  (:require [orc-extractor.orc-file-constants :as orc-const]
            [clj-mmap]))

(defn read-file [filename]
  (println "orc-file.read-file called for file:" filename)

  (with-open [orc-file (clj-mmap/get-mmap filename)
              midi-out (clj-mmap/get-mmap "./data/extracted.mid")]

    (let [first-four-bytes (clj-mmap/get-bytes orc-file 0 4)]
      (println (str "First 4 bytes of orc-file, " (String. first-four-bytes) ))))
      (println (str "Header start, " (String. orc-const/RIFF_HEADER_START)))
)

(read-file (io/file "./data/Demosong.orc"))
