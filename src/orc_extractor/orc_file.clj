(ns orc-extractor.orc-file
  (:require [orc-extractor.orc-file-constants :as orc-const]
            [orc-extractor.binary-utils :as bin-utils]
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
    (let [header-bytes (clj-mmap/get-bytes orc-file 0 8)]
      (println (str "First 8 bytes of orc-file, " (String. header-bytes)))
      ;(check-header header-bytes "RIFF")
    )))

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

(defn parse-header
  "Parse a RIFF-header and return the length of the following data chunk."
  [header-bytes, expected-header-name]
  (let [header-name (String. (subvec header-bytes 0 (count expected-header-name)))]
    (println header-name)
    (if (not= header-name expected-header-name)
      (throw (IllegalArgumentException.
        (str "Expected header name " expected-header-name ", was " header-name)))))

  (bin-utils/header-length (subvec header-bytes -4)))


;(read-file "./data/Demosong.orc")
