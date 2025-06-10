(defproject url-shortener-client "0.1.0-SNAPSHOT"
  :description "URL Shortener Client на Clojure"
  :dependencies [[org.clojure/clojure "1.11.1"]
                 [clj-http "3.12.3"]
                 [cheshire "5.10.0"]]
  :main ^:skip-aot url-shortener.client.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})