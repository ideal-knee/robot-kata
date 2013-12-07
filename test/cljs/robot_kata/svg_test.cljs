(ns robot-kata.svg-test

  (:require [cemerick.cljs.test                       ]
            [dommy.core         :refer [attr         ]]
            [robot-kata.svg     :refer [set-position!]] )

  (:require-macros [cemerick.cljs.test :refer [is deftest testing]]
                   [dommy.macros       :refer [node              ]]) )

(deftest robot-kata-svg-test

  (testing "set-position!"
    (let [g (node [:g])]
      (set-position! g {:x 1 :y 2 :theta 3})
      (is (= (attr g :transform) "translate(1, 2) rotate(3)") "translates and rotates based on position argument")
      (set-position! g {})
      (is (= (attr g :transform) "translate(0, 0) rotate(0)") "defaults to 0 for fields missing from position argument") ) ) )

