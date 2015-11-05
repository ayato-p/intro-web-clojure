(ns todo-clj.view.todo
  (:require [hiccup.core :as hc]))

(defn todo-index-view [req todo-list]
  (-> `([:h1 "TODO 一覧"]
        [:ul
         ~@(for [{:keys [title]} todo-list]
             [:li title])])
      hc/html))
