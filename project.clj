(defproject phonelib "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [ring/ring-core "1.3.1"]
                 [ring/ring-jetty-adapter "1.3.1"]
                 [ring/ring-json "0.3.1"]
                 [compojure "1.2.2"]
                 [com.googlecode.libphonenumber/libphonenumber "7.0.1"]
                 [com.googlecode.libphonenumber/geocoder "2.18"]
                 [com.googlecode.libphonenumber/carrier "1.8"]]
  :repositories [["java.net" "http://download.java.net/maven/2"]]
  :plugins [[lein-ring "0.8.11"]]
  :ring {:handler phonelib.core/app}
  :main phonelib.core
  :profiles
  {:dev {:dependencies [[ring-mock "0.1.5"]]}})
