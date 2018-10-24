(ns yahtzee-clj.core
  (:gen-class))

(defn roll []
  "Rolls a six sided die once."
  (+ 1 (rand-int 6)))

(defn roll-hand []
  "Rolls a hand of 5 six sided dice."
  (repeatedly 5 roll))

(defn score-singles [hand]
  "Scores the singles."
  (reduce + hand))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println (roll)))
