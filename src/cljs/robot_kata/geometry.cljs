(ns robot-kata.geometry)

(defn calculate-next-position [position delta]
  (let [new-theta-degrees (+ (position :theta) (delta :theta))
        new-theta (* new-theta-degrees (/ js/Math.PI 180)) ]
    {:x (+ (position :x) (* (delta :distance) (js/Math.sin new-theta)))
     :y (+ (position :y) (- (* (delta :distance) (js/Math.cos new-theta))))
     :theta new-theta-degrees } ) )

(defn get-sensor-position [robot-position robot-params]
  (calculate-next-position robot-position (robot-params :center-to-sensor-distance) 0) )

(defn calculate-position-delta [velocity elapsed-time]
  (let [factor (/ elapsed-time 1000.0)]
    {:distance (* (velocity :distance) factor)
     :theta    (* (velocity :theta)    factor) } ) )

