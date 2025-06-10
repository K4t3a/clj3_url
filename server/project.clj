(defproject url-shortener-server "0.1.0-SNAPSHOT"
  :description "URL Shortener REST API на Clojure"
  :dependencies [[org.clojure/clojure "1.11.1"]
                 [compojure "1.7.0"]
                 [ring/ring-defaults "0.3.3"]
                 [ring/ring-json "0.5.1"]
                 [cheshire "5.10.0"]]
  :plugins [[lein-ring "0.12.5"]]
  :ring {:handler url-shortener.server.core/app})
