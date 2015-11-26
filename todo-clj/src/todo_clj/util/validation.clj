(ns todo-clj.util.validation
  (:require [bouncer.core :as b]
            [slingshot.slingshot :refer [try+ throw+]]))

(defn validate [& args]
  (let [[errors org+errors] (apply b/validate args)]
    (if (nil? errors)
      org+errors
      (throw+ {:type ::validation-error :errors errors}))))

(defmacro with-fallback [fallback & body]
  `(try+
    ~@body
    (catch [:type ::validation-error] {:keys [errors#]}
      (~fallback errors#))))
