(ns url-shortener.server.core
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.adapter.jetty :refer [run-jetty]]
            [ring.middleware.json :refer [wrap-json-body wrap-json-response]]
            [clojure.string :as str])
  (:gen-class))

(def link-store (atom {}))

(def charset "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-_")

(defn rand-char []
  (nth charset (rand-int (count charset))))

(defn create-short-code []
  (apply str (repeatedly 8 rand-char)))

(defn valid-url? [url]
  (and (string? url)
       (re-matches #"https?://[a-zA-Z0-9\-.]+\.[a-zA-Z]{2,}.*" url)))

(defroutes app-routes
  (POST "/normal-url" request
    (let [full-url (get-in request [:body :normalUrl])]
      (if (valid-url? full-url)
        (let [short-code (create-short-code)]
          (swap! link-store assoc short-code full-url)
          {:status 200 :body short-code})
        {:status 400 :body "Invalid URL"})))
  
  (GET "/:shortCode" [shortCode]
    (if-let [full-url (get @link-store shortCode)]
      {:status 200 :body full-url}
      {:status 404 :body "Link Not Found"}))
  
  (PUT "/:shortCode/:fullUrl" [shortCode fullUrl]
    (if (and (contains? @link-store shortCode) (valid-url? fullUrl))
      (do (swap! link-store assoc shortCode fullUrl)
          {:status 200 :body "Link Updated"})
      {:status 404 :body "Link Not Found or Invalid URL"}))
  
  (DELETE "/:shortCode" [shortCode]
    (if (contains? @link-store shortCode)
      (do (swap! link-store dissoc shortCode)
          {:status 200 :body "Link Removed"})
      {:status 404 :body "Link Not Found"}))
  
  (route/not-found "Resource Not Found"))

(def app
  (-> app-routes
      (wrap-json-response)
      (wrap-json-body {:keywords? true})))

(defn -main [& args]
  (run-jetty app {:port 8080 :join? false}))