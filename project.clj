(defproject graph-centrality "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :main ^:skip-aot graph-centrality.core
  :plugins [[lein-ring "0.8.11"]]
  :ring {:handler graph-centrality.api/handler}
  :dependencies [
                [org.clojure/clojure "1.7.0"]
                [org.clojure/tools.namespace "0.2.11"]
                [liberator "0.14.1"]
                [compojure "1.3.4"]
                [ring/ring-core "1.2.1"]
                [cheshire "5.6.0"]])
