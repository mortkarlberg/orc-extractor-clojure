(ns orc-extractor.orc-file-test
    (:require [clojure.test :refer :all]
              [orc-extractor.orc-file :as orc-file :refer :all]))

  (deftest test-read
    (testing "orc-file.read"
      (testing "function callable")
      (is function? orc-file/read-file)))
