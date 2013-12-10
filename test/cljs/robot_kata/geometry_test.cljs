(ns robot-kata.geometry-test

  (:require [cemerick.cljs.test                                  ]
            [robot-kata.geometry :refer [calculate-next-position calculate-position-delta]] )

  (:require-macros [cemerick.cljs.test :refer [is deftest testing]]) )

; ???? Probably better way to do this...
(defn approx?
  "Make sure two values are close to each other within some threshold (for comparing floats)"
  ([a b] (approx? a b 0.000001))
  ([a b threshold]
     (< (js/Math.abs (- a b)) threshold) ) )

(deftest robot-kata-geometry-test
  (testing "calculate-next-position"
    (let [new-position (calculate-next-position {:x 0 :y 0 :theta 0} {:trans 10 :rot 0})]
      (is (approx? (new-position :x)       0) "moves correctly up y axis")
      (is (approx? (new-position :y)     -10) "moves correctly up y axis")
      (is (approx? (new-position :theta)   0) "moves correctly up y axis") )

    (let [new-position (calculate-next-position {:x 0 :y 0 :theta 90} {:trans 10 :rot 0})]
      (is (approx? (new-position :x)      10) "moves correctly right on x axis")
      (is (approx? (new-position :y)       0) "moves correctly right on x axis")
      (is (approx? (new-position :theta)  90) "moves correctly right on x axis") )

    (let [new-position (calculate-next-position {:x 0 :y 0 :theta 0} {:trans 10 :rot 90})]
      (is (approx? (new-position :x)      10) "turns and moves correctly right on x axis")
      (is (approx? (new-position :y)       0) "turns and moves correctly right on x axis")
      (is (approx? (new-position :theta)  90) "turns and moves correctly right on x axis") )

    (let [new-position (calculate-next-position {:x 0 :y 0 :theta 180} {:trans 10 :rot 45})]
      (is (approx? (new-position :x)      (- (js/Math.sqrt 50))) "turns and moves correctly at an angle")
      (is (approx? (new-position :y)          (js/Math.sqrt 50)) "turns and moves correctly at an angle")
      (is (approx? (new-position :theta)                    225) "turns and moves correctly at an angle") ) )

  (testing "calculate-position-delta"
    (let [position-delta (calculate-position-delta {:trans 100 :rot 100} 500)]
      (is (approx? (position-delta :trans) 50) "calculates position delta based on elapsed time")
      (is (approx? (position-delta :rot  ) 50) "calculates position delta based on elapsed time")) ) )

