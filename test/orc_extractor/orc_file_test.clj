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
      (is (orc-file/check-header (byte-array [82 73 70 70]) "RIFF")))
    
    (testing "header name not equal to expected"
      (is (not (orc-file/check-header (byte-array [81 70 17 7]) "RIFF"))))
  )
)
