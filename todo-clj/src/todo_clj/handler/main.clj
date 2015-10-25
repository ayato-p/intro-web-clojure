(ns todo-clj.handler.main
  (:require [compojure.core :refer [defroutes GET]]
            [compojure.route :as route]
            [todo-clj.util.response :as res]))

(defn home-view [req]
  "<h1>ホーム画面</h1>
   <a href=\"/todo\">TODO 一覧</a>")

(defn home [req]
  (-> (home-view req)
      res/response
      res/html))

(defroutes main-routes
  (GET "/" _ home)
  (route/not-found "<h1>Not found</h1>"))
