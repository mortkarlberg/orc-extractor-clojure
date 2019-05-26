(ns orc-extractor.orc-file
  (:require [orc-extractor.orc-file-constants :as orc-const]
            [clj-mmap]))

(defn read-file [filename]
  (println "orc-file.read-file called for file:" filename)

  (with-open [orc-file (clj-mmap/get-mmap filename)
              midi-out (clj-mmap/get-mmap "./data/extracted.mid")]

    ;TODO instead: check all headers and save in collection with start positions/length
    ;TODO: don't forget to handle the padding bytes
    ;TODO: write midi to its own file
    ;TODO: parse voyl data and use it when handling waves
    ;TODO: write each wave to its own file, maybe with padding zeroes to push it to its correct start point in the song
    ;TODO bonus: write a compound sequencer file from all data in some open format
    (let [first-bytes (clj-mmap/get-bytes orc-file 0 100)]
      (println (str "First 100 bytes of orc-file, " (String. first-bytes)))))
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

  ;TODO: only consider sub-array (as long as expected)
  (let [header-name (String. header-bytes)]
    (println header-name)
    (if (not= header-name expected-header-name)
      (throw (IllegalArgumentException.
        (str "Expected header name " expected-header-name ", was " header-name))))
  )

  ;TODO: parse last four bytes as litte endian int and return it
  true
)

;(read-file "./data/Demosong.orc")
