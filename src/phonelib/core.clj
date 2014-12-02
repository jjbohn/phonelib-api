(ns phonelib.core
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.json :refer [wrap-json-response]]
            [ring.middleware.params :refer :all]
            [ring.util.response :refer :all]
            [phonelib.shared :refer :all]))

(defn info [request]
  (let [number (.parse phone-util (number-from request) (country-code-from request))]
       (response {:numbers [(phone-number-map number)]})))

(defroutes app-routes
  (GET "/api/v1/info" [] info))

(def app
  (->
   (wrap-json-response app-routes)
   (wrap-params)))
