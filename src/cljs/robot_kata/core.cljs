(ns robot-kata.core
  (:require [domina :refer (by-id)]) )

(defn get-2d-context [canvas]
  (.getContext canvas "2d") )

(defn load-image [context, image-name, callback]
  (let [image (js/Image.)]
    (set! (.-onload image) #(do (.drawImage context image 0 0) (callback)))
    (set! (.-src image) image-name) ) )

(load-image (get-2d-context (by-id "floor")) "roomba-dock.png" #(js/console.log "Robot Kata!"))
