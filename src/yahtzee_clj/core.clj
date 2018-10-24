(ns yahtzee-clj.core
  (:gen-class))
(use '[clojure.string :only (join includes?)])

(defn roll []
  "Rolls a six sided die once."
  (+ 1 (rand-int 6)))

(defn roll-hand []
  "Rolls a hand of 5 six sided dice."
  (repeatedly 5 roll))

(defn score-chance [hand]
  "Scores the chance - i.e. sum of hand."
  (reduce + hand))

(defn score-singles [hand single]
  "Scores the singles"
  (reduce + (filter #(= single %) hand))
  )

(defn score-of-a-kind [hand kind-type]
  "Scores three, four and five of a kind"
  (let [most-common (last (sort-by val (frequencies hand)))
        face (first most-common)
        count (last most-common)]
    (cond
      (< count 3) 0
      (and (>= count 3) (= kind-type 3)) (* face 3)
      (and (>= count 4) (= kind-type 4)) (* face 4)
      (and (= count 5) (= kind-type 5)) 50
      :else 0
      )
    ))

;; TODO: this is horrible!
(defn score-full-house [hand]
  "Scores three, four and five of a kind"
  (let [freqs (sort-by val (frequencies hand))
        most-common (last (last freqs))
        second-most-common (last (last (butlast freqs)))]
    (if (and
          (= most-common 3)
          (= second-most-common 2))
      25 0)))

;; TODO: use threading macro to make it easier to read?
(defn hand-to-sorted-string [hand]
  "Returns a hand into a sorted string having removed dupes"
  (join "" (sort (into [] (set (map str hand)))))
  )

(defn score-straight [hand straight_length]
  "Scores small and large straight"
  (let [valid_small '("1234" "2345" "3456")
        valid_large '("12345" "23456")]
    (cond
      (and (= straight_length 4) (some #(includes? (hand-to-sorted-string hand) %) valid_small)) 30
      (and (= straight_length 5) (some #(includes? (hand-to-sorted-string hand) %) valid_large)) 40
      :else 0
      )
    )
  )

;; TODO: a function that enumerates the keywords for the singles
(defn score-hand [hand]
  "Returns a map of all the possible scores for a hand"
  {:chance (score-chance hand)
   :ones (score-singles hand 1)
   :twos (score-singles hand 2)
   :threes (score-singles hand 3)
   :fours (score-singles hand 4)
   :fives (score-singles hand 5)
   :sixes (score-singles hand 6)
   :three-of-a-kind (score-of-a-kind hand 3)
   :four-of-a-kind (score-of-a-kind hand 4)
   :full-house (score-full-house hand)
   :small-straight (score-straight hand 4)
   :large-straight (score-straight hand 5)
   :yahtzee (score-of-a-kind hand 5)
   }
  )

(defn -main
  "Runs the game"
  [& args]
  (let [hand (roll-hand)]
    (println hand (score-hand hand))))

