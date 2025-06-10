(ns url-shortener.client.core
  (:require [clj-http.client :as http]
            [cheshire.core :as json]
            [clojure.string :as str])
  (:gen-class))

(def server-url "http://localhost:8080")

(defn print-menu []
  (println "\nМеню:")
  (println "1. Создать")
  (println "2. Показать")
  (println "3. Изменить")
  (println "4. Удалить")
  (println "5. Выйти")
  (print "Выберите действие (1-5): ")
  (flush))

(defn create-url []
  (print "Введите полный URL: ")
  (flush)
  (let [full-url (str/trim (read-line))]
    (try
      (let [response (http/post (str server-url "/normal-url")
                               {:body (json/encode {:normalUrl full-url})
                                :content-type :json
                                :accept :json})
            short-code (:body response)]
        (println "Короткий URL:" short-code))
      (catch Exception e
        (println "Ошибка:" (.getMessage e))))))

(defn show-url []
  (print "Введите короткий URL: ")
  (flush)
  (let [short-code (str/trim (read-line))]
    (try
      (let [response (http/get (str server-url "/" short-code)
                               {:accept :json})
            full-url (:body response)]
        (println "Полный URL:" full-url))
      (catch Exception e
        (println "Ошибка:" (.getMessage e))))))

(defn update-url []
  (print "Введите короткий URL: ")
  (flush)
  (let [short-code (str/trim (read-line))]
    (print "Введите новый полный URL: ")
    (flush)
    (let [full-url (str/trim (read-line))]
      (try
        (let [response (http/put (str server-url "/" short-code "/" full-url)
                                 {:accept :json})
              message (:body response)]
          (println "Результат:" message))
        (catch Exception e
          (println "Ошибка:" (.getMessage e)))))))

(defn delete-url []
  (print "Введите короткий URL: ")
  (flush)
  (let [short-code (str/trim (read-line))]
    (try
      (let [response (http/delete (str server-url "/" short-code)
                                  {:accept :json})
            message (:body response)]
        (println "Результат:" message))
      (catch Exception e
        (println "Ошибка:" (.getMessage e))))))

(defn run-client []
  (loop []
    (print-menu)
    (let [choice (str/trim (read-line))]
      (cond
        (= choice "1") (do (create-url) (recur))
        (= choice "2") (do (show-url) (recur))
        (= choice "3") (do (update-url) (recur))
        (= choice "4") (do (delete-url) (recur))
        (= choice "5") (println "Выход")
        :else (do (println "Неверный выбор, попробуйте снова.") (recur))))))

(defn -main [& args]
  (println "Клиент URL-коротателя запущен.")
  (run-client))