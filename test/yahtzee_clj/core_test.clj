(ns yahtzee-clj.core-test
  (:require [clojure.test :refer :all]
            [yahtzee-clj.core :refer :all]))

(deftest test-hand
  ;; TODO; understand the value of doing this might not always be true
  (testing "Tests length, max & min of hand."
    (is (= 5 (count (roll-hand))))
    (is (> (apply min (roll-hand)) 0))
    (is (< (apply max (roll-hand)) 7))
    )
  )

;; TODO: better docs as these are redundant?
(deftest test-score-chance
  (testing "Tests the sum of the singles"
    (is (= 15 (score-chance '(1 2 3 4 5))))
    (is (= 30 (score-chance '(6 6 6 6 6))))
    )
  )

(deftest test-score-singles
  (testing "Tests the singles"
    (is (= 3 (score-singles '(1 2 1 1 3) 1)))
    (is (= 1 (score-singles '(1 2 3 4 5) 1)))
    (is (= 5 (score-singles '(1 2 3 4 5) 5)))
    (is (= 6 (score-singles '(2 2 3 4 2) 2)))
    (is (= 6 (score-singles '(2 2 3 4 2) 2)))
    (is (= 5 (score-singles '(1 1 1 1 1) 1)))
    (is (= 0 (score-singles '(1 1 1 1 1) 6)))
    )
  )

(deftest test-score-of-a-kind
  (testing "Tests the score when choosing x of a kind"
    (is (= 3 (score-of-a-kind '(1 6 1 6 1) 3)))
    (is (= 50 (score-of-a-kind '(6 6 6 6 6) 5)))
    (is (= 50 (score-of-a-kind '(1 1 1 1 1) 5)))
    (is (= 18 (score-of-a-kind '(6 6 1 6 1) 3)))
    (is (= 0 (score-of-a-kind '(1 2 5 6 1) 3)))
    (is (= 3 (score-of-a-kind '(1 1 1 1 1) 3)))
    (is (= 4 (score-of-a-kind '(1 1 1 1 1) 4)))
    (is (= 50 (score-of-a-kind '(1 1 1 1 1) 5)))
    )
  )

(deftest test-score-full-house
  (testing
    (is (= 25 (score-full-house '(1 6 1 6 1))))
    (is (= 25 (score-full-house '(5 5 5 2 2))))
    (is (= 0 (score-full-house '(1 6 1 6 3))))
    (is (= 0 (score-full-house '(5 5 5 2 1))))
    )
  )


(deftest test-hand-to-sorted-string
  (testing
    (is (= (hand-to-sorted-string '(1 2 3 4 5)) "12345"))
    (is (= (hand-to-sorted-string '(1 1 1 1 1)) "1"))
    (is (= (hand-to-sorted-string '(2 3 4 5 5)) "2345"))
    (is (= (hand-to-sorted-string '(2 2 3 4 5)) "2345"))
    (is (= (hand-to-sorted-string '(2 3 4 5 6)) "23456"))
    (is (= (hand-to-sorted-string '(1 2 2 3 1)) "123"))
    )
  )

(deftest test-score-straight
  (testing
    (is (= 30 (score-straight '(1 2 3 4 5) 4)))
    (is (= 30 (score-straight '(5 6 1 4 3) 4)))
    (is (= 0 (score-straight '(5 6 1 4 2) 4)))
    (is (= 0 (score-straight '(5 6 1 4 1) 4)))
    (is (= 0 (score-straight '(5 6 1 4 1) 5)))
    (is (= 40 (score-straight '(5 6 3 2 4) 5)))
    (is (= 40 (score-straight '(2 1 3 5 4) 5)))
    ))

(deftest test-score-hand
  (testing
    (is (= (score-hand '(1 2 3 3 3))
            {:chance 12
             :ones 1
             :twos 2
             :threes 9
             :fours 0
             :fives 0
             :sixes 0
             :three-of-a-kind 9
             :four-of-a-kind 0
             :full-house 0
             :small-straight 0
             :large-straight 0
             :yahtzee 0
             }
           ))))

(run-all-tests)