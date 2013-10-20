(ns robot-kata.server
  (:require [ring.adapter.jetty :refer [run-jetty          ]]
            [ring.util.response :refer [redirect           ]]
            [compojure.core     :refer [defroutes GET      ]]
            [compojure.handler  :refer [site               ]]
            [compojure.route    :refer [resources not-found]] ) )

(defroutes robot-kata-routes
  (GET "/" [_] (redirect "/robot-kata.html"))
  (resources "/")
  (not-found "Page not found") )

(def handler (site robot-kata-routes))

(defn -main [port]
  (run-jetty handler {:port (Integer. port) :join? false}) )
