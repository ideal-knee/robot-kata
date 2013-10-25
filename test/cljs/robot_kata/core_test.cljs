(ns robot-kata.core-test
  (:require-macros [cemerick.cljs.test :refer (is deftest testing)])
  (:require        [cemerick.cljs.test                                       ]
                   [robot-kata.core    :refer (get-2d-context get-color-name)] ) )

(deftest robot-kata-core-test

  (testing "get-2d-context"
    (let [canvas (.createElement js/document "canvas")]
      (is (instance? js/CanvasRenderingContext2D (get-2d-context canvas)) "returns CanvasRenderingContext2D") ) )

  (testing "get-color-name"
    (is (= (get-color-name {:r 200 :g 230 :b 193}) "white" ) "detects white correctly" )
    (is (= (get-color-name {:r 200 :g 230 :b 191}) "yellow") "detects yellow correctly")
    (is (= (get-color-name {:r 200 :g   0 :b 191}) "red"   ) "detects red correctly"   )
    (is (= (get-color-name {:r   0 :g 200 :b 191}) "green" ) "detects green correctly" )
    (is (= (get-color-name {:r 123 :g   0 :b 193}) "blue"  ) "detects blue correctly"  )
    (is (= (get-color-name {:r   0 :g 100 :b 191}) "black" ) "detects black correctly" ) ) )

