(ns todo-clj.view.todo
  (:require [hiccup.form :as hf]
            [todo-clj.view.layout :as layout]))

(defn todo-index-view [req todo-list]
  (->> [:section.card
        [:h2 "TODO 一覧"]
        [:ul
         (for [{:keys [title]} todo-list]
           [:li title])]]
       (layout/common req)))

(defn todo-new-view [req]
  (->> [:section.card
        [:h2 "TODO 追加"]
        (hf/form-to
         [:post "/todo/new"]
         [:input {:name :title :placeholder "TODO を入力してください"}]
         [:button.bg-blue "追加する"])]
       (layout/common req)))

(defn todo-complete-view [req]
  (->> [:section.card
        [:h2 "TODO を追加しました!!"]]
       (layout/common req)))
