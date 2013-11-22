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

(defn set-position! [node & {:keys [x y theta] :or {x 0 y 0 theta 0}}]
  (set-attr! node :transform (format "translate(%s, %s) rotate(%s)" (str x) (str y) (str theta))) )

(defn init-robot-svg [svg]
  (let [robot (make-robot-graphic robot-params)]
    (append! svg robot)
    (set-position! robot :x 400 :y 400 :theta 30) ) )

