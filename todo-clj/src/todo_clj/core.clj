(ns todo-clj.core
  (:require [compojure.core :refer [routes]]
            [ring.adapter.jetty :as server]
            [todo-clj.handler.main :refer [main-routes]]
            [todo-clj.handler.todo :refer [todo-routes]]))

(defonce server (atom nil))

(def app
  (routes
   todo-routes
   main-routes))

(defn start-server []
  (when-not @server
    (reset! server (server/run-jetty #'app {:port 3000 :join? false}))))

(defn stop-server []
  (when @server
    (.stop @server)
    (reset! server nil)))

(defn restart-server []
  (when @server
    (stop-server)
    (start-server)))
