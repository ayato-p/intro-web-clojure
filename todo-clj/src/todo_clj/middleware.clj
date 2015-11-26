(ns todo-clj.middleware
  (:require [environ.core :refer [env]]
            [ring.middleware.defaults :as defaults]
            [todo-clj.middleware.http-response :as http-response]))

(defn- try-resolve [sym]
  (try
    (require (symbol (namespace sym)))
    (resolve sym)
    (catch java.io.FileNotFoundException _)
    (catch RuntimeException _)))

(defn wrap-dev [handler]
  {:pre [(or (fn? handler) (and (var? handler) (fn? (deref handler))))]}
  (let [wrap-exceptions (try-resolve 'prone.middleware/wrap-exceptions)
        wrap-reload (try-resolve 'ring.middleware.reload/wrap-reload)]
    (if (and wrap-reload wrap-exceptions)
      (-> handler
          wrap-exceptions
          wrap-reload)
      (throw (RuntimeException. "Middleware requires ring/ring-devel and prone;")))))

(def ^:private wrap #'defaults/wrap)

(defn middleware-set [handler]
  (-> handler
      http-response/wrap-http-response
      (wrap wrap-dev (:dev env))
      (defaults/wrap-defaults defaults/site-defaults)))
