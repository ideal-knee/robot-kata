(ns hello.server
  (:require [ring.adapter.jetty :refer [run-jetty          ]]
            [compojure.core     :refer [defroutes GET      ]]
            [compojure.handler  :refer [site               ]]
            [compojure.route    :refer [resources not-found]] ) )

(defroutes hello-routes
           (GET "/" [] "<p>hi from compojure</p>")
           (resources "/")
           (not-found "Page not found") )

(defn -main [port]
  (run-jetty (site hello-routes) {:port (Integer. port) :join? false}) )
