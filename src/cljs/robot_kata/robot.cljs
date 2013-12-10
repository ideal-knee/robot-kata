(ns robot-kata.robot

  (:require [dommy.core          :refer [append!                                      ]]
            [robot-kata.geometry :refer [calculate-next-position get-sensor-position  ]]
            [robot-kata.image    :refer [get-2d-context get-color-name get-pixel-color]]
            [robot-kata.svg      :refer [make-robot-graphic set-position!             ]] )

  (:require-macros [dommy.macros :refer [sel1]]) )

(def robot-params {:robot-radius              35 ; pixels
                   :center-to-sensor-distance 30 ; pixels
                   :sensor-radius              3 ; pixels
                   :bumper-width               2 ; pixels
                   :robot-color         "silver"
                   :bumper-color         "black"
                   :sensor-color         "black" })

(defn init-robot-svg [svg]
  (append! svg (make-robot-graphic robot-params))
  #_(animate {:x 0 :y 0 :theta 135})
  #_(animate-interval 1000 #(js/console.log "hi"))
  (simulate {:x 400 :y 800 :theta 0} {:distance 0 :theta 0} (.now js/Date)) )

(defn animate [robot-position]
  (js/setTimeout #(let [new-robot-position (calculate-next-position robot-position 5 0)]
                    (set-position! (sel1 :#robot) new-robot-position)
                    (js/console.log (get-color-name (get-pixel-color (get-2d-context (sel1 :#floor))
                                                                     (get-sensor-position new-robot-position
                                                                                          robot-params ) )))
                    (animate new-robot-position) )
                 200 ) )

(defn animate-interval
  ([period func] (animate-interval period func 0))
  ([period func last-tick-time]
     (js/requestAnimationFrame #(let [tick-time (.now js/Date)]
                                  (if (> (- tick-time last-tick-time) period)
                                    (do
                                      (func)
                                      (animate-interval period func tick-time) )
                                    (animate-interval period func last-tick-time) ) )) ) )

(defn simulate [robot-position robot-velocity last-tick-time]
  (let [tick-time          (.now js/Date)
        elapsed-time       (min (- tick-time last-tick-time) 100)
        new-robot-position (calculate-next-position robot-position
                                                    (calculate-position-delta robot-velocity elapsed-time) ) ]
    (set-position! (sel1 :#robot) new-robot-position)
    (if (continue-simulation?)
      (js/requestAnimationFrame #(simulate new-robot-position
                                           (if (time-for-control-tick?)
                                             (get-new-robot-velocity new-robot-position)
                                             robot-velocity )
                                           tick-time ))  ) ) )

