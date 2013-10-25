(ns robot-kata.core-test
  (:require-macros [cemerick.cljs.test :refer (is deftest testing)])
  (:require        [cemerick.cljs.test                            ]
                   [robot-kata.core    :refer (get-2d-context)    ] ) )

(deftest robot-kata-core-test

  (testing "get-2d-context"
    (let [canvas (.createElement js/document "canvas")]
      (is (instance? js/CanvasRenderingContext2D (get-2d-context canvas)) "Returns CanvasRenderingContext2D") ) ) )
