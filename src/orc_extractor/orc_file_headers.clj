(ns orc-extractor.orc-file-headers
  (:require [orc-extractor.orc-file-constants :as orc-const]
            [orc-extractor.binary-utils :as bin-utils]))

(defn parse-header
  "Parse a RIFF-header and return the length of the following data chunk."
  [header, expected-header-name]
  (let [name-length (count expected-header-name)
        name (take name-length header)]
    (if (not= name (seq expected-header-name))
      (throw (IllegalArgumentException.
              (str "Expected header name " expected-header-name ", was " name))))
    (bin-utils/header-length (drop name-length header))))
