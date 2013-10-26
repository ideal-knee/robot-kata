(ns robot-kata.core

  (:require [cljs.core.async       :refer [<! chan put!]]
            [goog.events           :refer [listen      ]]
            [goog.events.EventType :refer [MOUSEMOVE   ]] )

  (:require-macros [cljs.core.async.macros :refer [go  ]]
                   [dommy.macros           :refer [sel1]] ) )

(defn get-2d-context [canvas]
  (.getContext canvas "2d") )

(defn load-image [context, image-name, callback]
  (let [image (js/Image.)]
    (set! (.-onload image) #(do (.drawImage context image 0 0) (callback)))
    (set! (.-src image) image-name) ) )

(defn get-event-offset-position [event]
  {:x (.-offsetX event) :y (.-offsetY event)} ) ; Only works in WebKit????

(defn get-pixel-color [context position]
  (let [image-data (.-data (.getImageData context (position :x) (position :y) 1 1))]
    {:r (aget image-data 0) :g (aget image-data 1) :b (aget image-data 2)} ) )

(defn get-color-name [color]
  (let [threshold 192]
    (cond (and (> (color :r) threshold) (> (color :g) threshold) (> (color :b) threshold)) "white"
          (and (> (color :r) threshold) (> (color :g) threshold))                          "yellow"
          (> (color :r) threshold)                                                         "red"
          (> (color :g) threshold)                                                         "green"
          (> (color :b) threshold)                                                         "blue"
          true                                                                             "black" ) ) )

(defn ^:export run []
  (let [floor         (sel1 :#floor)
        floor-context (get-2d-context floor)
        channel       (chan)                 ]

    (load-image floor-context "roomba-dock.png" #(js/console.log "Robot Kata!"))

    (listen floor MOUSEMOVE #(put! channel %))

    (go
     (while true
       (let [event (<! channel)]
         (js/console.log (get-color-name (get-pixel-color floor-context
                                                          (get-event-offset-position event) ))) ) ) ) ) )

