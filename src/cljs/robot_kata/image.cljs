(ns robot-kata.image)

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

