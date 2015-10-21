(ns todo-clj.core
  (:require [ring.adapter.jetty :as server]))

(defonce server (atom nil))

(defn handler [req]
  {:status 200
   :headers {"Content-Type" "text/plain"}
   :body "Hello, world"})

(defn start-server []
  (if-not @server
    (reset! server (server/run-jetty handler {:port 3000 :join? false}))))
