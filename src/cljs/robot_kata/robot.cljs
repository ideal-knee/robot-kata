(ns robot-kata.robot

  (:require [dommy.core :refer [append! set-attr!]])

  (:require-macros [dommy.macros :refer [node]]) )

(def robot-params {:robot-radius              35
                   :center-to-sensor-distance 30
                   :sensor-radius             2.5
                   :bumper-width              2
                   :robot-color               "silver"
                   :bumper-color              "black"
                   :sensor-color              "black" })

(defn make-robot-graphic [params]
  (node [:g#robot

         [:circle {:cx 0
                   :cy 0
                   :r            (params :robot-radius)
                   :stroke       (params :bumper-color)
                   :stroke-width (params :bumper-width)
                   :fill         (params :robot-color ) }]

         [:circle#sensor {:cx     0
                          :cy     (- (params :center-to-sensor-distance))
                          :r      (params :sensor-radius)
                          :stroke "none"
                          :fill   (params :sensor-color) }] ]) )

(defn set-position! [node position]
  (set-attr! node :transform (format "translate(%s, %s) rotate(%s)"
                                     (str (position :x     0))
                                     (str (position :y     0))
                                     (str (position :theta 0)) )) )

(defn init-robot-svg [svg]
  (let [robot (make-robot-graphic robot-params)]
    (append! svg robot)
    (set-position! robot {:x 400 :y 400 :theta 30}) ) )

(defn calculate-next-position [position distance theta]
  (let [new-theta-degrees (+ (position :theta) theta)
        new-theta (* new-theta-degrees (/ js/Math.PI 180)) ]
    {:x (+ (position :x) (* distance (js/Math.sin new-theta)))
     :y (+ (position :y) (- (* distance (js/Math.cos new-theta))))
     :theta new-theta-degrees } ) )

(defn get-sensor-position [robot-position robot-params]
  (calculate-next-position robot-position (robot-params :center-to-sensor-distance) 0) )

; ???? For testing, remove
(defn get-sensor-position-from-mouse [mouse-position]
  (get-sensor-position mouse-position robot-params) )

