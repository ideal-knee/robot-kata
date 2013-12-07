(ns robot-kata.robot

  (:require [dommy.core      :refer [append! set-attr!]])

  (:require-macros [dommy.macros :refer [node sel1]]) )

(def robot-params {:robot-radius              35
                   :center-to-sensor-distance 30
                   :sensor-radius             2.5
                   :bumper-width              2
                   :robot-color               "silver"
                   :bumper-color              "black"
                   :sensor-color              "black" })

(defn get-2d-context [canvas]
  (.getContext canvas "2d") )

(defn get-color-name [color]
  (let [threshold 192]
    (cond (and (> (color :r) threshold) (> (color :g) threshold) (> (color :b) threshold)) "white"
          (and (> (color :r) threshold) (> (color :g) threshold))                          "yellow"
          (> (color :r) threshold)                                                         "red"
          (> (color :g) threshold)                                                         "green"
          (> (color :b) threshold)                                                         "blue"
          true                                                                             "black" ) ) )

(defn get-pixel-color [context position]
  (let [image-data (.-data (.getImageData context (position :x) (position :y) 1 1))]
    {:r (aget image-data 0) :g (aget image-data 1) :b (aget image-data 2)} ) )

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

    (animate {:x 0 :y 0 :theta 135})

    #_
    (set-position! robot {:x 400 :y 400 :theta 30}) ) )

(defn calculate-next-position [position distance theta]
  (let [new-theta-degrees (+ (position :theta) theta)
        new-theta (* new-theta-degrees (/ js/Math.PI 180)) ]
    {:x (+ (position :x) (* distance (js/Math.sin new-theta)))
     :y (+ (position :y) (- (* distance (js/Math.cos new-theta))))
     :theta new-theta-degrees } ) )

(defn get-sensor-position [robot-position robot-params]
  (calculate-next-position robot-position (robot-params :center-to-sensor-distance) 0) )

(defn animate [robot-position]
  (js/setTimeout #(let [new-robot-position (calculate-next-position robot-position 5 0)]
                    (set-position! (sel1 :#robot) new-robot-position)
                    (js/console.log (get-color-name (get-pixel-color (get-2d-context (sel1 :#floor))
                                                                     (get-sensor-position new-robot-position
                                                                                          robot-params ) )))
                    (animate new-robot-position) )
                 200 ) )

