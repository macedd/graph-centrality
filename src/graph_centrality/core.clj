(ns graph-centrality.core
  (:require [graph-centrality.graph :as graph])
  (:use [clojure.tools.namespace.repl :only (refresh)]
        [graph-centrality.graph]
        ))

(def filepath "resources/edges")

; (def g (graph-load filepath))

(defn -main
  [& args]
  (let [g (graph-load filepath)]
    ; (println (graph-closeness g))
    (graph-centrality g)
    )
  )
