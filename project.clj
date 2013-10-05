(defproject clojure-heroku-hello-world "0.1.0-SNAPSHOT"

            :description "FIXME: write description"
            :url "http://example.com/FIXME"
            :license {:name "Eclipse Public License"
                      :url "http://www.eclipse.org/legal/epl-v10.html" }

            :uberjar-name "clojure-heroku-hello-world-standalone.jar"
            :min-lein-version "2.1.2"
            :dependencies [[org.clojure/clojure       "1.5.1"   ]
                           [org.clojure/clojurescript "0.0-1878"]
                           [ring/ring-jetty-adapter   "1.1.6"   ]
                           [compojure                 "1.1.5"   ] ]
            :source-paths ["src/clj"]
            :plugins [[lein-cljsbuild "0.3.3"]]

            :cljsbuild {:builds
                        [{:source-paths ["src/cljs"]
                          :compiler {:output-to "resources/public/hello.js"
                                      :optimizations :whitespace
                                      :pretty-print true } }] } )
