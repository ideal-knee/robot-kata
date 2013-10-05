clojure-heroku-hello-world
==========================

A skeleton project for using Clojure and ClojureScript on Heroku.

Based on [the Heroku Clojure getting started guide][hcgsg] and [modern-cljs][mcljs].

Usage
-----

Build ClojureScript code

    lein cljsbuild once

Serve locally

    lein run -m hello.server <PORT>

To deploy, push to Heroku.

License
-------

Copyright © 2013 Dan Kee

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.

[hcgsg]: https://devcenter.heroku.com/articles/getting-started-with-clojure
[mcljs]: https://github.com/magomimmo/modern-cljs
