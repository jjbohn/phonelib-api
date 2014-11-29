(ns phonelib.core
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.json :refer [wrap-json-response]]
            [ring.util.response :refer :all])
  (:import [com.google.i18n.phonenumbers PhoneNumberUtil]))

(defn parse [request]
  (let [util (PhoneNumberUtil/getInstance)]
    (.parse util "615-521-6937" "US"))
  (response {:foo 'bar}))

(defroutes app
  (GET "/" [] parse))

(def app
  (wrap-json-response app))
