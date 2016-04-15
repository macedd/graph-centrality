(ns graph-centrality.helpers
  (:use [clojure.java.io :only (reader)]))

(defn lazy-read [file]
  "Read file sequentially while closing the reader after done"
  (let [rdr (reader file)]
    (lazy-seq
      (loop [line (.readLine rdr) acc (list)]
        (if (seq line)
            (recur (.readLine rdr) (cons line acc))
            (do (.close rdr) acc))))))
