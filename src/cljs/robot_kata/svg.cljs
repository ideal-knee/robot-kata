(ns robot-kata.svg

  (:require [dommy.core :refer [set-attr!]])

  (:require-macros [dommy.macros :refer [node set-attr!]]) )

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

