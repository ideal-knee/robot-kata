(ns robot-kata.server
  (:require [ring.adapter.jetty :refer [run-jetty          ]]
            [compojure.core     :refer [defroutes GET      ]]
            [compojure.handler  :refer [site               ]]
            [compojure.route    :refer [resources not-found]] ) )

(defroutes robot-kata-routes
           (resources "/")
           (not-found "Page not found") )

(defn -main [port]
  (run-jetty (site robot-kata-routes) {:port (Integer. port) :join? false}) )
