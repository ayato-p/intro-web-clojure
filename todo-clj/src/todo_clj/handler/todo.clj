(ns todo-clj.handler.todo
  (:require [bouncer.validators :as v]
            [compojure.core :refer [defroutes context GET POST]]
            [todo-clj.db.todo :as todo]
            [todo-clj.util.response :as res]
            [todo-clj.util.validation :as uv]
            [todo-clj.view.todo :as view]))

(def todo-validator {:title [[v/required :message "TODO を入力してください"]]})

(defn todo-index [req]
  (let [todo-list (todo/find-todo-all)]
    (-> (view/todo-index-view req todo-list)
        res/ok
        res/html)))

(defn todo-new [req]
  (-> (view/todo-new-view req)
      res/ok
      res/html))

(defn todo-new-post [{:as req :keys [params]}]
  (uv/with-fallback #(todo-new (assoc req :errors %))
    (let [params (uv/validate params todo-validator)]
      (if-let [todo (first (todo/save-todo (:title params)))]
        (-> (res/found (str "/todo/" (:id todo)))
            (assoc :flash {:msg "TODO を正常に追加しました。"})
            res/html)
        (res/internal-server-error!)))))

(defn todo-search [req] "TODO search")

(defn todo-show [{:as req :keys [params]}]
  (if-let [todo (todo/find-first-todo (Long/parseLong (:todo-id params)))]
    (-> (view/todo-show-view req todo)
        res/ok
        res/html)
    (res/not-found!)))

(defn todo-edit [{:as req :keys [params]}]
  (if-let [todo (todo/find-first-todo (Long/parseLong (:todo-id params)))]
    (-> (view/todo-edit-view req todo)
        res/ok
        res/html)
    (res/not-found!)))

(defn todo-edit-post [{:as req :keys [params]}]
  (uv/with-fallback #(todo-edit (assoc req :errors %))
    (let [params (uv/validate params todo-validator)
          todo-id (Long/parseLong (:todo-id params))]
      (if (pos? (first (todo/update-todo todo-id (:title params))))
        (-> (res/found (str "/todo/" todo-id))
            (assoc :flash {:msg "TODO を正常に更新しました"})
            res/html)
        (res/conflict!)))))

(defn todo-delete [{:as req :keys [params]}]
  (if-let [todo (todo/find-first-todo (Long/parseLong (:todo-id params)))]
    (-> (view/todo-delete-view req todo)
        res/ok
        res/html)
    (res/not-found!)))

(defn todo-delete-post [{:as req :keys [params]}]
  (let [todo-id (Long/parseLong (:todo-id params))]
    (if (pos? (first (todo/delete-todo todo-id)))
      (-> (res/found "/todo")
          (assoc :flash {:msg "TODO を正常に削除しました"})
          res/html)
      (res/conflict!))))

(defroutes todo-routes
  (context "/todo" _
    (GET "/" _ todo-index)
    (GET "/new" _ todo-new)
    (POST "/new" _ todo-new-post)
    (GET "/search" _ todo-search)
    (context "/:todo-id" _
      (GET "/" _ todo-show)
      (GET "/edit" _ todo-edit)
      (POST "/edit" _ todo-edit-post)
      (GET "/delete" _ todo-delete)
      (POST "/delete" _ todo-delete-post))))
