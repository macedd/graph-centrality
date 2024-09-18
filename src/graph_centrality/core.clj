(ns graph-centrality.core
  (:require [graph-centrality.graph :as graph])
  (:use [clojure.tools.namespace.repl :only (refresh)]
        [graph-centrality.graph]))

;; Initial edges path
(def filepath "resources/edges")


(defn -main
  [& args]
  (let [g (graph-load filepath)]
    ; (println g)
    ; (println (graph-closeness g))
    (graph-centrality g)
    )
  )
