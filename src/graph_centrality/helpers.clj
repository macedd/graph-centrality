(ns graph-centrality.helpers
  (:use [clojure.java.io :only (reader)]))

(defn lazy-read [file]
  "Read file sequentially while closing the reader after done"
  (defn helper [rdr]
    (lazy-seq
      (if-let [line (.readLine rdr)]
        (cons line (helper rdr))
        (.close rdr))))
  (lazy-seq
    (helper (reader file))))
