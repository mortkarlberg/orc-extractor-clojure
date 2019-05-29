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
      (is function? orc-file/check-header))

    (testing "header name equals expected"
      (is
        (orc-file/check-header (byte-array [82 73 70 70]) "RIFF")))
    
    (testing "header name not equal to expected"
      (is (thrown? IllegalArgumentException
        (orc-file/check-header (byte-array [81 70 17 7]) "RIFF"))))))

;header-name length
;52494646 C20F0000 = RIFF 4034
;524D4944 64617461 B60F0000 = RMID data 4022

(deftest test-header-length
  (testing "Little endian byte array to number"
    (are [x y] (= x y)
      0 (orc-file/header-length (byte-array [0x00 0x00 0x00 0x00]))
      1 (orc-file/header-length (byte-array [0x01 0x00 0x00 0x00]))
      16 (orc-file/header-length (byte-array [0x10 0x00 0x00 0x00]))
      4034 (orc-file/header-length (byte-array [0xC2 0x0F 0x00 0x00]))
      4022 (orc-file/header-length (byte-array [0xB6 0x0F 0x00 0x00])))))
