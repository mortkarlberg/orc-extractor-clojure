(ns orc-extractor.orc-file
  (:require [clj-mmap]
            [orc-extractor.orc-file-headers :as headers]))

(defn read-file [filename]
  (println "orc-file.read-file called for file:" filename)

  (with-open [orc-file (clj-mmap/get-mmap filename)
              midi-out (clj-mmap/get-mmap "./data/extracted.mid")]
    (headers/locate-nested-headers orc-file)))

;TODO don't require signed commits for personal github projects
;TODO test with more files
(comment
  (read-file "./data/Demosong.orc"))
