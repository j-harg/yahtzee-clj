(ns yahtzee-clj.core-test
  (:require [clojure.test :refer :all]
            [yahtzee-clj.core :refer :all]))

(deftest test-hand
  ;; TODO; understand the value of doing this might not always be true
  (testing "Tests length, max & min of hand."
    (is (= 5 (count (roll-hand))))
    (is (> 0 (min (roll-hand))))
    (is (< 7 (max (roll-hand))))
    )
  )

(deftest test-score-singles
  (testing "Tests the sum of the singles"
    (is (= 15 (score-singles '(1 2 3 4 5))))
    (is (= 30 (score-singles '(6 6 6 6 6))))
    )
  )

(run-all-tests)