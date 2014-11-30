(ns phonelib.shared (:import [com.google.i18n.phonenumbers PhoneNumberUtil]))

(defn phone-util [] (PhoneNumberUtil/getInstance))

(defn default-country-code [] "US")

(defn country-code-from [request]
  ((request :params) "country_code" default-country-code))

(defn number-from [request]
  ((request :params) "number"))

(defn phone-number-map [number]
  {:national_number (.getNationalNumber number)
   :country_code    (.getCountryCode number)
   :extension       (.getExtension number)})
