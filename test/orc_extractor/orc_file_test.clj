(ns orc-extractor.orc-file-test
  (:require [clojure.test :refer :all]
            [orc-extractor.orc-file :as orc-file :refer :all]))

(deftest test-read-file
  (testing "orc-file.read-file"
    (testing "function callable"
     (is function? orc-file/read-file))))

(deftest test-parse-header
  (testing "orc-file.check-header"
    (testing "function callable"
      (is function? orc-file/parse-header))

    (testing "header name equals expected"
      (is (=
        (orc-file/parse-header (byte-array [0x52 0x49 0x46 0x46 0x38 0x6C 0x04 0x00])
                               "RIFF")
        289848))

      (is (=
        (orc-file/parse-header (byte-array [0x57 0x41 0x56 0x45 0x66 0x6D 0x74 0x20 0x12 0x00 0x00 0x00])
                               "WAVEfmt ")
        18)))
    
    (testing "header name not equal to expected"
      (is (thrown? IllegalArgumentException
        (orc-file/parse-header (byte-array [81 70 17 7]) "RIFF"))))))
