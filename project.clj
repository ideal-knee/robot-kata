(defproject robot-kata "0.1.0-SNAPSHOT"

  :description "A web application to explore a simple robot programming problem"
  :url "http://robot-kata.herokuapp.com"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html" }

  :uberjar-name "robot-kata-standalone.jar"
  :min-lein-version "2.1.2"
  :source-paths ["src/clj"]
  :test-paths ["test/clj"]

  :plugins [[lein-cljsbuild                  "0.3.3"]
            [lein-ring                       "0.8.7"]
            [com.cemerick/clojurescript.test "0.1.0"] ]
  :hooks [leiningen.cljsbuild]

  :dependencies [[org.clojure/clojure          "1.5.1"                 ]
                 [org.clojure/clojurescript    "0.0-1878"              ]
                 [org.clojure/core.async       "0.1.242.0-44b1e3-alpha"]
                 [ring/ring-jetty-adapter      "1.1.6"                 ]
                 [compojure                    "1.1.5"                 ]
                 [org.clojars.ideal-knee/dommy "0.1.3-SNAPSHOT"        ] ]

  :ring {:handler robot-kata.server/handler
         :open-browser? false }

  :cljsbuild {:builds {:dev {:source-paths ["src/cljs"]
                             :compiler {:output-to "resources/public/robot-kata.js"
                                        :optimizations :whitespace
                                        :pretty-print true } }
                       
                       :test {:source-paths ["src/cljs" "test/cljs"]
                              :compiler {:output-to "target/robot-kata-test.js"
                                         :optimizations :whitespace
                                         :pretty-print true } } }
              
              :test-commands {"phantom-test" ["phantomjs" :runner "target/robot-kata-test.js"]} } )

