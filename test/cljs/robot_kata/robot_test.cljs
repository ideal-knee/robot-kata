(ns robot-kata.robot-test

  (:require [cemerick.cljs.test                       ]
            [dommy.core         :refer [attr         ]]
            [robot-kata.robot   :refer [set-position!]] )

  (:require-macros [cemerick.cljs.test :refer (is deftest testing)]
                   [dommy.macros       :refer (node)]) )

(deftest robot-kata-robot-test

  (testing "set-position!"
    (let [g (node [:g])]
      (set-position! g :x 1 :y 2 :theta 3)
      (is (= (attr g :transform) "translate(1, 2) rotate(3)") "translates and rotates based on keyword arguments")
      (set-position! g)
      (is (= (attr g :transform) "translate(0, 0) rotate(0)") "defaults to 0 for keyword arguments") ) ) )

