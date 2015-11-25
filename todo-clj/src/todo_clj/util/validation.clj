(ns todo-clj.util.validation
  (:require [bouncer.core :as b]))

(defn validate [& args]
  (let [[errors org+errors] (apply b/validate args)]
    (if (nil? errors)
      org+errors
      (throw (ex-info "Validation error" errors)))))

(defmacro with-fallback [fallback & body]
  `(try
     ~@body
     (catch clojure.lang.ExceptionInfo e#
       (~fallback (ex-data e#)))))
