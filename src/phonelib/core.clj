(ns phonelib.core
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.json :refer [wrap-json-response]]
            [ring.util.response :refer :all]))

(defn basic [request]
  (response {:foo 'bar}))

(defroutes app
  (GET "/" [] basic))

(def app
  (wrap-json-response app))
