(ns robot-kata.geometry)

(defn calculate-next-position [position distance theta]
  (let [new-theta-degrees (+ (position :theta) theta)
        new-theta (* new-theta-degrees (/ js/Math.PI 180)) ]
    {:x (+ (position :x) (* distance (js/Math.sin new-theta)))
     :y (+ (position :y) (- (* distance (js/Math.cos new-theta))))
     :theta new-theta-degrees } ) )

(defn get-sensor-position [robot-position robot-params]
  (calculate-next-position robot-position (robot-params :center-to-sensor-distance) 0) )

