(ns robot-kata.robot

  (:require [dommy.core          :refer [append!                                                             ]]
            [robot-kata.geometry :refer [calculate-next-position calculate-position-delta get-sensor-position]]
            [robot-kata.image    :refer [get-2d-context get-color-name get-pixel-color                       ]]
            [robot-kata.svg      :refer [make-robot-graphic set-position!                                    ]] )

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
  (simulate {:x 400 :y 800 :theta 0} {:trans 0 :rot 0} (.now js/Date)) )

(let [last-tick-time (atom 0)]
  (defn time-for-control-tick? []
    (let [current-time (.now js/Date)]
      (if (> (- current-time @last-tick-time))
        (reset! last-tick-time current-time) ) ) ) )

(defn get-new-robot-velocity [sensed-color]
  (cond (= sensed-color "white") {:trans 50 :rot  0}
        :else                    {:trans  0 :rot 10} ) )

(let [stop-simulation? (atom false)]
  (defn stop-simulation! []
    (reset! stop-simulation? true) )
  
  (defn continue-simulation? [robot-position sensed-color]
    (cond
     @stop-simulation? (reset! stop-simulation? false) ; Returns false
     (= sensed-color "black") (do (js/alert "FAILURE!") false)
     (= sensed-color "blue")  (do (js/alert "SUCCESS!") false)
     :else true ) ) )

(defn simulate [previous-robot-position robot-velocity last-tick-time]
  (let [tick-time      (.now js/Date)
        elapsed-time   (min (- tick-time last-tick-time) 100)
        robot-position (calculate-next-position previous-robot-position
                                                (calculate-position-delta robot-velocity elapsed-time) )
        sensed-color   (get-color-name (get-pixel-color (get-2d-context (sel1 :#floor))
                                                        (get-sensor-position robot-position robot-params) )) ]
    (set-position! (sel1 :#robot) robot-position)
    (if (continue-simulation? robot-position sensed-color)
      (js/requestAnimationFrame #(simulate robot-position
                                           (if (time-for-control-tick?)
                                             (get-new-robot-velocity sensed-color)
                                             robot-velocity )
                                           tick-time ))  ) ) )

