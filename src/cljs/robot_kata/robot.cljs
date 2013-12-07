(ns robot-kata.robot

  (:require [dommy.core          :refer [append!                                      ]]
            [robot-kata.geometry :refer [calculate-next-position get-sensor-position  ]]
            [robot-kata.image    :refer [get-2d-context get-color-name get-pixel-color]]
            [robot-kata.svg      :refer [make-robot-graphic set-position!             ]] )

  (:require-macros [dommy.macros :refer [sel1]]) )

(def robot-params {:robot-radius              35
                   :center-to-sensor-distance 30
                   :sensor-radius             2.5
                   :bumper-width              2
                   :robot-color               "silver"
                   :bumper-color              "black"
                   :sensor-color              "black" })

(defn init-robot-svg [svg]
  (append! svg (make-robot-graphic robot-params))
  (animate {:x 0 :y 0 :theta 135}) )

(defn animate [robot-position]
  (js/setTimeout #(let [new-robot-position (calculate-next-position robot-position 5 0)]
                    (set-position! (sel1 :#robot) new-robot-position)
                    (js/console.log (get-color-name (get-pixel-color (get-2d-context (sel1 :#floor))
                                                                     (get-sensor-position new-robot-position
                                                                                          robot-params ) )))
                    (animate new-robot-position) )
                 200 ) )

