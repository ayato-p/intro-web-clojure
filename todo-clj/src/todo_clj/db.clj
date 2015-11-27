(ns todo-clj.db
  (:require [clojure.java.jdbc :as jdbc]
            [environ.core :refer [env]]))

(def db-spec
  (:db env))

(defn migrated? []
  (pos? (count (jdbc/query db-spec "select tablename from pg_tables where schemaname = 'public'"))))

(defn migrate []
  (when-not (migrated?)
    (jdbc/db-do-commands
     db-spec
     (jdbc/create-table-ddl :todo [:id :serial] [:title :varchar]))))
