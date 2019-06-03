(ns orc-extractor.binary-utils)

;TODO: why is not % working in post condition?
;TODO: make sure loop is lazy and will not fill stack

(defn header-length
  "Calculates unsigned integer of little endian byte array."
  {:test #(do            
            (assert (= 0 (header-length (byte-array [0x00]))))
            (assert (= 1 (header-length (byte-array [0x01 0x00]))))
            (assert (= 256 (header-length (byte-array [0x00 0x01]))))
            (assert (= 1 (header-length (byte-array [0x01 0x00 0x00]))))
            (assert (= 16 (header-length (byte-array [0x10 0x00 0x00 0x00 0x00]))))
            (assert (= 4096 (header-length (byte-array [0x00 0x10 0x00 0x00 0x00]))))
            (assert (= 1048576 (header-length (byte-array [0x00 0x00 0x10 0x00 0x00]))))
            (assert (= 68719476752 (header-length (byte-array [0x10 0x00 0x00 0x00 0x10]))))
            (assert (= 4034 (header-length (byte-array [0xC2 0x0F 0x00 0x00]))))
            (assert (= 4022 (header-length (byte-array [0xB6 0x0F 0x00 0x00]))))
            (assert (= 288495364 (header-length (byte-array [0x04 0x17 0x32 0x11]))))
            (assert (= 2361002 (header-length (byte-array [0xAA 0x06 0x24]))))
            (assert (= 1103806599937 (header-length (byte-array [0x01 0x13 0x00 0x00 0x01 0x01]))))
            (assert (= 59857 (header-length (byte-array [0xD1 0xE9])))))
;   :post [(pos? %)]
   }
  [bytes]
  (reduce +
          (map (fn [i]
                 (let [shift (* i 8)]
                   (bit-shift-left (bit-and (nth bytes i)
                                            0x000000FF)
                                   shift)))
               (range 0 (count bytes)))))

;(test #'header-length)