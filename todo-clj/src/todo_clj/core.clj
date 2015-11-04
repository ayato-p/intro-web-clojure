(ns todo-clj.core
  (:require [compojure.core :refer [routes]]
            [ring.adapter.jetty :as server]
            [todo-clj.handler.main :refer [main-routes]]
            [todo-clj.handler.todo :refer [todo-routes]]
            [todo-clj.middleware :refer [wrap-dev]]
            [environ.core :refer [env]]))

(defonce server (atom nil))

(defn- wrap [handler middleware opt]
  (if (true? opt)
    (middleware handler)
    (if opt
      (middleware handler opt)
      handler)))

(def app
  (-> (routes
       todo-routes
       main-routes)
      (wrap wrap-dev (:dev env))))

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
