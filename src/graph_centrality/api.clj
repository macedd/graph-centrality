(ns graph-centrality.api
  (:require [liberator.core :refer [resource defresource]]
            [ring.middleware.params :refer [wrap-params]]
            [compojure.core :refer [defroutes ANY]]
            [cheshire.core :refer :all])
  (:use [graph-centrality.core :only (filepath)]
        [graph-centrality.graph]))


(defn get-centrality []
  (let [g (graph-load filepath)]
    (graph-centrality g)))

; File writing lock object
(def filewrite (Object.))

(defn check-edge [edge]
  (and (not-empty edge)
      (= 2 (count (.split edge " ")))))
  

(defn append-edge [edge]
  (if (check-edge edge)
    (locking filewrite
      (spit filepath (str "\n" edge) :append true))))

; (spit "event.log" "test 1\n" :append true)

(defroutes app
  (ANY "/centrality" [] (resource :available-media-types ["application/json"]
                           :handle-ok (fn [ctx]
                                        (generate-string (get-centrality)))))

  (ANY "/add-edge" [] (resource :available-media-types ["text/plain"]
                          :allowed-methods [:post]
                          :handle-ok "ok"
                          :post! (fn [ctx]
                                  (let [body (slurp (get-in ctx [:request :body]))]
                                      (append-edge body))))))
(def handler 
  (-> app 
      wrap-params))
