(ns todo-clj.middleware.http-response
  (:require [hiccup.core :as h]
            [ring.util.http-status :as status]
            [slingshot.slingshot :refer [try+]]
            [todo-clj.util.response :as res]))

(defn- error-view [{:as response :keys [status]}]
  (let [{:keys [name description]} (status/status status)]
    (-> `([:h1 ~name]
          [:h2 ~description])
        h/html
        res/ok
        res/html)))

(defn wrap-http-response [handler]
  (fn [req]
    (try+
     (handler req)
     (catch [:type :ring.util.http-response/response] {:keys [response]}
       (error-view response)))))
