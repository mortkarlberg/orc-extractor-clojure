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

(defn read-and-handle
  "Read some bytes from mmap and handle with some method"
  [orc-file start length handler & handler-args]
)

;def check_riff_header(file, header_name):
;    header_bytes = file.read(len(header_name))
;    print(bytes)
;    if header_name != header_bytes:
;        print("Unknown file content.", header_bytes)
;        exit()
;
;    size_bytes = file.read(4)
;    size = int.from_bytes(size_bytes, "little")
;
;    read_bytes = header_bytes + size_bytes
;    print(size, read_bytes)
;
;return size, read_bytes


(defn check-header
  "Parse a RIFF-header and return the length of the following data chunk."
  [header-bytes, expected-header-name]

  (println (String. header-bytes))

  (if (not= (String. header-bytes) expected-header-name)
  false
  true
  )
)

;(read-file "./data/Demosong.orc")
