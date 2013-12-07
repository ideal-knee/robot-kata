(ns robot-kata.core

  (:require [cljs.core.async       :refer [<! chan put!  ]]
            [goog.events           :refer [listen        ]]
            [goog.events.EventType :refer [MOUSEMOVE     ]]
            [robot-kata.robot      :refer [get-2d-context get-sensor-position-from-mouse init-robot-svg set-position!]] )

  (:require-macros [cljs.core.async.macros :refer [go  ]]
                   [dommy.macros           :refer [sel1]] ) )

(defn load-image [context, image-name, callback]
  (let [image (js/Image.)]
    (set! (.-onload image) #(do (.drawImage context image 0 0) (callback)))
    (set! (.-src image) image-name) ) )

(defn get-event-offset-position [event]
  {:x (.-offsetX event) :y (.-offsetY event)} ) ; Only works in WebKit????

(defn ^:export run []
  (let [floor-context (get-2d-context (sel1 :#floor))
        channel       (chan)                          ]

    (load-image floor-context "roomba-dock.png" #(js/console.log "Robot Kata!"))

    (listen (sel1 :#robot-svg) MOUSEMOVE #(put! channel %))

    (init-robot-svg (sel1 :#robot-svg))

    #_
    (go
     (while true
       (let [event (<! channel)]
         (set-position! (sel1 :#robot) (assoc (get-event-offset-position event) :theta 135))
         (js/console.log (get-color-name (get-pixel-color
                                          floor-context
                                          (get-sensor-position-from-mouse
                                           (assoc (get-event-offset-position event) :theta 135) ) ))) ) ) ) ) )

