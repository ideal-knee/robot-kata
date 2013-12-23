(ns robot-kata.image)

(defn get-2d-context [canvas]
  (.getContext canvas "2d") )

(defn get-color-name [color]
  (let [on-threshold  240
        off-threshold  96 ]
    (cond (and (> (color :r)  on-threshold) (< (color :g) off-threshold) (< (color :b) off-threshold)) "red"
          (and (> (color :r)  on-threshold) (> (color :g)  on-threshold) (< (color :b) off-threshold)) "yellow"
          (and (< (color :r) off-threshold) (> (color :g)  on-threshold) (< (color :b) off-threshold)) "green"
          (and (< (color :r) off-threshold) (< (color :g) off-threshold) (> (color :b)  on-threshold)) "blue"
          (and (< (color :r) off-threshold) (< (color :g) off-threshold) (< (color :b) off-threshold)) "black"
          true                                                                                         "white" ) ) )

(defn get-pixel-color [context position]
  (let [image-data (.-data (.getImageData context (position :x) (position :y) 1 1))]
    {:r (aget image-data 0) :g (aget image-data 1) :b (aget image-data 2)} ) )

