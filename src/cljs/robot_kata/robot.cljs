(ns robot-kata.robot

  (:require [dommy.core          :refer [append! listen! remove-attr! set-attr!                              ]]
            [robot-kata.geometry :refer [calculate-next-position calculate-position-delta get-sensor-position]]
            [robot-kata.image    :refer [get-2d-context get-color-name get-pixel-color                       ]]
            [robot-kata.svg      :refer [make-robot-graphic set-position!                                    ]] )

  (:require-macros [dommy.macros :refer [node sel sel1]]) )

(def robot-params {:robot-radius              35 ; pixels
                   :center-to-sensor-distance 30 ; pixels
                   :sensor-radius              3 ; pixels
                   :bumper-width               2 ; pixels
                   :robot-color         "silver"
                   :bumper-color         "black"
                   :sensor-color         "black" })

(defn init-robot-svg [svg]
  (append! svg (make-robot-graphic robot-params))
  (set-position! (sel1 :#robot) {:x 275 :y 450 :theta 0})
  (init-ui) )

(defn init-ui []
  (let [stop-button (node [:button "Stop simulation"])]
    (set-attr! stop-button :disabled)
    (listen! stop-button :click stop-simulation!)
    (append! (sel1 :#stop-button-div) stop-button) )
  (add-test "Test 1" {:x 275 :y 450 :theta 0})
  (add-test "Test 2" {:x 200 :y 450 :theta 45})
  (add-test "Test 3" {:x 50 :y 150 :theta 90})
  (add-test "Test 4" {:x 200 :y 50 :theta -150})
  (add-test "Test 5" {:x 500 :y 100 :theta -150}) )

(defn add-test [name start-position]
  (let [button (node [:button name])]
    (listen! button :click #(do
                              (disable-start-test-buttons)
                              (simulate start-position) ))
    (append! (sel1 :#test-start-button-div) (node [:div button])) ) )

(let [last-tick-time (atom 0)]
  (defn time-for-control-tick? []
    (let [current-time (.now js/Date)]
      (if (> (- current-time @last-tick-time))
        (reset! last-tick-time current-time) ) ) ) )

(defn get-new-robot-velocity [sensed-color]
  (cond (= sensed-color "white") {:trans 50 :rot  0}
        :else                    {:trans  0 :rot 10} ) )

(defn enable-start-test-buttons []
  (doseq [b (sel [:#test-start-button-div :button])]
    (remove-attr! b :disabled) )
  (set-attr! (sel1 [:#stop-button-div :button]) :disabled) )

(defn disable-start-test-buttons []
  (doseq [b (sel [:#test-start-button-div :button])]
    (set-attr! b :disabled) )
  (remove-attr! (sel1 [:#stop-button-div :button]) :disabled) )

(let [stop-simulation? (atom false)]
  (defn stop-simulation! []
    (reset! stop-simulation? true) )
  
  (defn continue-simulation? [robot-position sensed-color]
    (cond
     @stop-simulation? (reset! stop-simulation? false) ; Returns false
     (= sensed-color "black") (do (js/alert "FAILURE!") false)
     (= sensed-color "blue")  (do (js/alert "SUCCESS!") false)
     :else true ) ) )

(defn simulate
  ([initial-robot-position] (simulate initial-robot-position {:trans 0 :rot 0} (.now js/Date)))
  ([previous-robot-position robot-velocity last-tick-time]
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
                                              tick-time ))
         (enable-start-test-buttons) ) ) ) )

