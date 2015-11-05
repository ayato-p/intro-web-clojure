(ns todo-clj.view.main
  (:require [hiccup.core :as hc]))

(defn home-view [req]
  (-> (list
       [:h1 "ホーム画面"]
       [:a {:href "/todo"} "TODO 一覧"])
      hc/html))
