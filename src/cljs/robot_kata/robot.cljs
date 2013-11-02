(ns robot-kata.robot

  (:require-macros [dommy.macros :refer [node]]) )

(defn make-robot-svg []
  (node [:g#robot {:transform "translate(400, 400)"}
         [:circle        {:cx 0 :cy   0 :r  35 :stroke "black" :stroke-width 2 :fill "silver"}]
         [:circle#sensor {:cx 0 :cy -30 :r 2.5 :stroke "none"                  :fill  "black"}] ])  )

