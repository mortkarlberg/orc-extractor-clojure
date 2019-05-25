(ns orc-extractor.core
  (:require [orc-extractor.orc-file :as orc-file])
  (:gen-class))

(defn -main
  "Extract orc file to separate midi and wave files."
  [& args]
  (println "Hello, Voyetra Digital Orcestrator user!"))
