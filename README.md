Robot Kata
==========

A web application to explore a simple robot programming problem

Requirements
------------

To build and serve: [Leiningen](http://leiningen.org/) and a JDK (6 or newer?)

To use: a reasonably reasonable web browser with JavaScript console

Usage
-----

Build ClojureScript code

    lein cljsbuild once

Serve locally

    lein run -m robot-kata.server <PORT>

To deploy, push to Heroku.

License
-------

Copyright © 2013 Dan Kee

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
