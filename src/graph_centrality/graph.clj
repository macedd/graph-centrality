(ns graph-centrality.graph
  (:require [graph-centrality.helpers :as helpers])
  (:use [clojure.tools.namespace.repl :only (refresh)]
        [clojure.string :only (split)]))

(defn read-edges [filepath]
  "Load text file into vector of edges"
    (map #(split % #" ")
          (helpers/lazy-read filepath)))

(defn merge-edges [edges]
  "Map vertices with their neighbours"
  (defn merger [m e]
    (let [k (first e) v (last e)]
      (if-not (contains? m k)
        (assoc m k []))
      (assoc m k (conj (get m k) v))
      ))
  (reduce #(-> %1
            (merger %2)
            (merger (reverse %2)))
          {} edges))

(defn graph-load [filepath]
  "Load the graph with mapped vertices"
  (let [edges (read-edges filepath)]
    (merge-edges edges)))


(defn vertex-paths
  "Calculate a vertex shortest-path to all other vertices"
  ([g src]
    (def inf Double/POSITIVE_INFINITY)
    (defn nbrs-costs
      [g costs unvisited curr]
      (let [curr-cost (get costs curr)]
        (reduce
          (fn [c nbr]
            (if (unvisited nbr)
              (update-in c [nbr] min (+ curr-cost 1))
              c))
          costs
          (get g curr))))

    (loop [costs (assoc (zipmap (keys g) (repeat inf)) src 0)
           curr-node src
           unvisited (disj (apply hash-set (keys g)) src)]
      (cond
       (or (empty? unvisited) (= inf (get costs curr-node)))
       costs

       :else
       (let [next-costs (nbrs-costs g costs unvisited curr-node)
             next-node (apply min-key next-costs unvisited)]
         (recur next-costs next-node (disj unvisited next-node)))))))

(defn vertex-farness [g src]
  "Sum vertex distances to other vertices"
  (let [paths (vertex-paths g src)
        distances (vals paths)]
      (apply + distances)))

; (defn vertex-closeness [g src]
;   (let [farness (vertex-farness g src)]
;     (/ 1 farness)))

(defn vertex-closeness [g src]
  "Apply the closeness to each vertex (to allow disconnections) and sum the closeness of the vertex"
  (let [paths (vertex-paths g src)
        distances (vals paths)]
      (/ (apply + (map #(if (> % 0) (/ 1 %) 0) distances))
         (count distances))))

(defn graph-closeness [g]
  "Calculate the closeness of the whole mapped graph"
  (reduce-kv
    (fn [m k v]
      (assoc m k (float (vertex-closeness g k))))
    {} g))

(defn graph-centrality [g]
  "Sort vertices by their centrality in the graph (higher is better)"
  (sort-by val > (graph-closeness g)))