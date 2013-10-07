(ns robot-kata.server-test
  (:require [clojure.test :refer :all]
            [robot-kata.server :refer :all]))

(deftest robot-kata-routes-test
         (testing "Routes work as expected"
                  (testing "Default route"
                           (let [resp (robot-kata-routes {:request-method :get :uri "/"})]
                             (is (= 302 (:status resp)) "Default route redirects")
                             (is (= "/robot-kata.html" ((:headers resp) "Location")) "Default route redirects to '/robot-kata.html'") ) )
                  (testing "Resource route"
                           (let [resp (robot-kata-routes {:request-method :get :uri "/robot-kata.html" :headers {"if-modified-since" "Sat, 29 Oct 1994 19:43:31 GMT"}})]
                             (is (= 200 (:status resp)) "GET of '/robot-kata.html' returns 200 status")
                             (is (= "text/html" ((:headers resp) "Content-Type")) "GET of '/robot-kata.html' returns content type 'text/html'")
                             (is (> (Integer. ((:headers resp) "Content-Length")) 0) "GET of '/robot-kata.html' returns content of length > 0") ) )
                  (testing "Page not found"
                           (let [resp (robot-kata-routes {:request-method :get :uri "/bad-route-asdf"})]
                             (is (= 404 (:status resp)) "GET of bad route returns 404 status")
                             (is (= "Page not found" (:body resp)) "GET of bad route returns 'Page not found'") ) ) ) )
