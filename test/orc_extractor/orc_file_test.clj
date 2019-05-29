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
      (is (=
        (orc-file/check-header (byte-array [0x52 0x49 0x46 0x46 0x38 0x6C 0x04 0x00])
                               "RIFF")
        289848))

      (is (=
        (orc-file/check-header (byte-array [0x57 0x41 0x56 0x45 0x66 0x6D 0x74 0x20 0x12 0x00 0x00 0x00])
                               "WAVEfmt ")
        0)))
    
    (testing "header name not equal to expected"
      (is (thrown? IllegalArgumentException
        (orc-file/check-header (byte-array [81 70 17 7]) "RIFF"))))))

(deftest test-header-length
  (testing "Little endian byte array to number"
    (are [x y] (= x y)
      0 (orc-file/header-length (byte-array [0x00]))
      1 (orc-file/header-length (byte-array [0x01 0x00]))
      1 (orc-file/header-length (byte-array [0x01 0x00 0x00]))
      16 (orc-file/header-length (byte-array [0x10 0x00 0x00 0x00 0x00]))
      4096 (orc-file/header-length (byte-array [0x00 0x10 0x00 0x00 0x00]))
      1048576 (orc-file/header-length (byte-array [0x00 0x00 0x10 0x00 0x00]))
      68719476752 (orc-file/header-length (byte-array [0x10 0x00 0x00 0x00 0x10]))
      4034 (orc-file/header-length (byte-array [0xC2 0x0F 0x00 0x00]))
      4022 (orc-file/header-length (byte-array [0xB6 0x0F 0x00 0x00]))
      288495364 (orc-file/header-length (byte-array [0x04 0x17 0x32 0x11]))
      2361002 (orc-file/header-length (byte-array [0xAA 0x06 0x24]))
      1103806599937 (orc-file/header-length (byte-array [0x01 0x13 0x00 0x00 0x01 0x01]))
      59857 (orc-file/header-length (byte-array [0xD1 0xE9])))))
