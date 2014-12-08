(ns phonelib.shared
  (:require [clojure.string :refer [lower-case]]
            [clojure.reflect :refer :all]
            [clojure.pprint :refer [print-table]])
  (:import [java.util Locale]
           [com.google.i18n.phonenumbers PhoneNumberUtil
            PhoneNumberUtil$PhoneNumberFormat
            PhoneNumberToCarrierMapper]
           [com.google.i18n.phonenumbers.geocoding PhoneNumberOfflineGeocoder]))

(def phone-util (PhoneNumberUtil/getInstance))
(def carrier-mapper (PhoneNumberToCarrierMapper/getInstance))
(def geocoder (PhoneNumberOfflineGeocoder/getInstance))

(defn nil-if-empty [arg]
  (if (empty? arg) nil arg))

(defn format-e164 [number]
  (.format phone-util number PhoneNumberUtil$PhoneNumberFormat/E164))

(defn format-international [number]
  (.format phone-util number PhoneNumberUtil$PhoneNumberFormat/INTERNATIONAL))

(defn format-national [number]
  (.format phone-util number PhoneNumberUtil$PhoneNumberFormat/NATIONAL))

(defn carrier [number]
  (.getNameForValidNumber carrier-mapper number Locale/ENGLISH))

(defn geocode [number]
  (.getDescriptionForNumber geocoder number Locale/ENGLISH))

(defn country [number]
  (.getCountryNameForNumber geocoder number Locale/ENGLISH))

(defn phone-number-map [number]
  {:national_number      (.getNationalNumber number)
   :country_code         (.getCountryCode number)
   :extension            (nil-if-empty (.getExtension number))
   :type                 (lower-case (.getNumberType phone-util number))
   :e164_format          (format-e164 number)
   :international_format (format-international number)
   :national_format      (format-national number)
   :carrier              (nil-if-empty (carrier number))
   :location             (nil-if-empty (geocode number))})

(defn info [number, country-code]
  (let [number (.parse phone-util number, country-code)]
    (phone-number-map number)))

(defn find-numbers [string, country-code]
  (let [numbers (map #(.number %)
                     (iterator-seq (.iterator (.findNumbers phone-util string, country-code))))]
    (map phone-number-map numbers)))
