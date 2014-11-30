(ns phonelib.core
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.json :refer [wrap-json-response]]
            [ring.middleware.params :refer :all]
            [ring.util.response :refer :all]
            [phonelib.shared :refer :all])
  (:import [com.google.i18n.phonenumbers PhoneNumberUtil]))



(defn parse [request]
  (let [number (.parse (phone-util) (number-from request) (default-country-code))]
    (response {:numbers [(phone-number-map number)]})))

(defroutes app-routes
  (GET "/api/v1/parse" [] parse))

(def app
  (->
   (wrap-json-response app-routes)
   (wrap-params)))
