(defproject ford_fulkerson "0.0.1-SNAPSHOT"
  :description "An implementation of Ford-Fulkerson in Clojure"
  :license {:name "Eclipse Public License - v 1.0"
            :url "http://www.eclipse.org/legal/epl-v10.html"
            :distribution :repo
            :comments "same as Clojure"}
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.10.0"]]
  :main ford_fulkerson
  :aot [ford_fulkerson]
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})

