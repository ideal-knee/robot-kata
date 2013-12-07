(ns robot-kata.geometry-test

  (:require [cemerick.cljs.test                                  ]
            [robot-kata.geometry :refer [calculate-next-position]] )

  (:require-macros [cemerick.cljs.test :refer [is deftest testing]]) )

; ???? Probably better way to do this...
(defn approx?
  "Make sure two values are close to each other within some threshold (for comparing floats)"
  ([a b] (approx? a b 0.000001))
  ([a b threshold]
     (< (js/Math.abs (- a b)) threshold) ) )

(deftest robot-kata-geometry-test
  (testing "calculate-next-position"
    (let [new-position (calculate-next-position {:x 0 :y 0 :theta 0} 10 0)]
      (is (approx? (new-position :x)       0) "moves correctly up y axis")
      (is (approx? (new-position :y)     -10) "moves correctly up y axis")
      (is (approx? (new-position :theta)   0) "moves correctly up y axis") )

    (let [new-position (calculate-next-position {:x 0 :y 0 :theta 90} 10 0)]
      (is (approx? (new-position :x)      10) "moves correctly right on x axis")
      (is (approx? (new-position :y)       0) "moves correctly right on x axis")
      (is (approx? (new-position :theta)  90) "moves correctly right on x axis") )

    (let [new-position (calculate-next-position {:x 0 :y 0 :theta 0} 10 90)]
      (is (approx? (new-position :x)      10) "turns and moves correctly right on x axis")
      (is (approx? (new-position :y)       0) "turns and moves correctly right on x axis")
      (is (approx? (new-position :theta)  90) "turns and moves correctly right on x axis") )

    (let [new-position (calculate-next-position {:x 0 :y 0 :theta 180} 10 45)]
      (is (approx? (new-position :x)      (- (js/Math.sqrt 50))) "turns and moves correctly at an angle")
      (is (approx? (new-position :y)          (js/Math.sqrt 50)) "turns and moves correctly at an angle")
      (is (approx? (new-position :theta)                    225) "turns and moves correctly at an angle") ) ) )
