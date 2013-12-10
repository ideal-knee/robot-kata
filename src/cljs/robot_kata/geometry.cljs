(ns robot-kata.geometry)

(defn calculate-next-position [position delta]
  (let [new-theta-degrees (+ (position :theta) (delta :rot))
        new-theta (* new-theta-degrees (/ js/Math.PI 180)) ]
    {:x (+ (position :x) (* (delta :trans) (js/Math.sin new-theta)))
     :y (+ (position :y) (- (* (delta :trans) (js/Math.cos new-theta))))
     :theta new-theta-degrees } ) )

(defn get-sensor-position [robot-position robot-params]
  (calculate-next-position robot-position {:trans (robot-params :center-to-sensor-distance) :rot 0}) )

(defn calculate-position-delta [velocity elapsed-time]
  (let [factor (/ elapsed-time 1000.0)]
    {:trans (* (velocity :trans) factor)
     :rot   (* (velocity :rot  ) factor) } ) )

