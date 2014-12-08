(ns phonelib.core
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.adapter.jetty :as jetty]
            [ring.middleware.json :refer [wrap-json-response]]
            [ring.middleware.params :refer :all]
            [ring.util.response :refer :all]
            [phonelib.shared :as phonelib]))

(defn number-from [request] ((request :params) "number"))

(defn text-from [request] ((request :params) "text"))

(defn country-code-from [request] ((request :params) "country_code" "US"))

(defn info [request]
  (let [number (phonelib/info (number-from request) (country-code-from request))]
    (response {:numbers [number]})))

(defn find-numbers [request]
  (let [numbers (phonelib/find-numbers (text-from request) (country-code-from request))]
    (response {:numbers numbers})))

(defroutes app-routes
  (GET "/api/v1/info" [] info)
  (GET "/api/v1/find" [] find-numbers))

(def app
  (->
   (wrap-json-response app-routes)
   (wrap-params)))

(defn -main []
  (let [port (Integer/parseInt (get (System/getenv) "PORT" "5000"))]
    (jetty/run-jetty app {:port port})))
