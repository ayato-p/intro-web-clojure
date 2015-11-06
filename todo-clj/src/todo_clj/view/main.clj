(ns todo-clj.view.main
  (:require [todo-clj.view.layout :as layout]))

(defn home-view [req]
  (->> [:section.card
        [:h2 "ホーム画面"]
        [:a {:href "/todo"} "TODO 一覧"]]
       (layout/common req)))
