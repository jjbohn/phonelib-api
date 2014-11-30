(ns phonelib.core
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.json :refer [wrap-json-response]]
            [ring.middleware.params :refer :all]
            [ring.util.response :refer :all])
  (:import [com.google.i18n.phonenumbers PhoneNumberUtil]))

(defn phone-number-map [number]
  {:national_number (.getNationalNumber number)
   :country_code    (.getCountryCode number)
   :extension       (.getExtension number)
   })

(defn country-code-from [request]
  ((request :params) "country_code"))

(defn number-from [request]
  ((request :params) "number"))

(defn parse [request]
  (let [util (PhoneNumberUtil/getInstance)]
    (let [number (.parse util (number-from request)
                         (country-code-from request))]
    (response {:numbers [(phone-number-map number)]}))))

(defroutes app-routes
  (GET "/api/v1/parse" [] parse))

(def app
  (->
   (wrap-json-response app-routes)
   (wrap-params)))
