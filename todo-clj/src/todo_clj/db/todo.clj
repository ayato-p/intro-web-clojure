(ns todo-clj.db.todo
  (:require [clojure.java.jdbc :as jdbc]
            [todo-clj.db :as db]))

(defn save-todo [title]
  (jdbc/insert! db/db-spec :todo {:title title}))

(defn find-todo-all []
  (jdbc/query db/db-spec "select * from todo"))
