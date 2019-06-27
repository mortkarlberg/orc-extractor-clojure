(ns orc-extractor.file-utils
  (:require [clojure.java.io :as io]))

(defn write-file [path data]
  (with-open [file (io/output-stream path)]
    (.write file data)))
