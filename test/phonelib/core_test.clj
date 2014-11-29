(ns phonelib.core-test
  (:require [clojure.test :refer :all]
            [ring.mock.request :as mock]
            [phonelib.core :refer :all]))

(deftest test-app
  (testing "main route"
    (let [response (app (mock/request :get "/"))]
      (is (= (:status response) 200))
      (is (= (:body response) "{\"foo\":\"bar\"}")))))
