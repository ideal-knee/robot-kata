(ns robot-kata.core
  (:require [domina                :refer [by-id       ]]
            [cljs.core.async       :refer [<! chan put!]]
            [goog.events           :refer [listen      ]]
            [goog.events.EventType                     ])
  
  (:require-macros [cljs.core.async.macros :refer [go]]) )

(defn get-2d-context [canvas]
  (.getContext canvas "2d") )

(defn load-image [context, image-name, callback]
  (let [image (js/Image.)]
    (set! (.-onload image) #(do (.drawImage context image 0 0) (callback)))
    (set! (.-src image) image-name) ) )

(load-image (get-2d-context (by-id "floor")) "roomba-dock.png" #(js/console.log "Robot Kata!"))

(let [floor   (by-id "floor")
      channel (let [c (chan)] (listen floor goog.events.EventType.MOUSEMOVE #(put! c %)) c) ]
  (go (while true
        (let [event (<! channel)]
          (js/console.log (str (.-offsetX event) ", " (.-offsetY event))) ) )) )
