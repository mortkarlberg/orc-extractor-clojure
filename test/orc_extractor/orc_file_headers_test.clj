(ns orc-extractor.orc-file-headers-test
  (:require [clojure.test :refer :all]
            [orc-extractor.orc-file-headers :as headers :refer :all]
            [orc-extractor.orc-file-constants :as orc-const :refer :all]))

(deftest test-locate-headers
  (testing "function callable"
    (is function? headers/locate-headers))
  
  (testing "prints"
    (is (= true
           (headers/locate-headers nil)))))

(deftest test-parse-header
  (testing "function callable"
    (is function? headers/parse-header))

  (testing "header name equals expected"
    (is (= 289848
           (headers/parse-header
            (seq (byte-array [0x52 0x49 0x46 0x46 0x38 0x6C 0x04 0x00]))
            orc-const/RIFF_HEADER_START)))

    (is (= 18
           (headers/parse-header
            (seq (byte-array [0x57 0x41 0x56 0x45 0x66 0x6D 0x74 0x20 0x12 0x00 0x00 0x00]))
            orc-const/RIFF_HEADER_WAVE_START))))

  (testing "header name not equal to expected"
    (is (thrown? IllegalArgumentException
                 (headers/parse-header
                  (seq (byte-array [0x52 0x49 0x46 0x46 0x38 0x6C 0x04 0x00])
                       orc-const/RIFF_HEADER_MIDI))))))
