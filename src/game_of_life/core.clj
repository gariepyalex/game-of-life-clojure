(ns game-of-life.core
  (:gen-class))

(defn square-of-position
  "Returns the square of size 3 centered at position as the list of cells it contains"
  [[i j]]
  (for [k (range (dec i) (+ 2 i))
        l (range (dec j) (+ 2 j))]
    [k l]))

(defn neighbors-of-position
  [board position]
  (into #{}
    (filter #(and (contains? board %) (not= position %)) (square-of-position position))))

(defn number-of-alive-neighbors
  [board position]
  (let [neighbors (neighbors-of-position board position)]
    (count 
      (filter #(get % :alive?) 
        (map #(get board %) neighbors)))))

(defn will-be-alive?
  [board position]
  (let [n (number-of-alive-neighbors board position)]
    (if (get (get board position) :alive?)
      (cond 
       (< n 2) false
       (> n 3) false
       :else true)
      (= 3 n))))

(defn next-cell
  [board [position flags]]
  (if (will-be-alive? board position)
    [position (conj flags [:alive? true])]
    [position (conj flags [:alive? false])]))

(defn next-board
  [board]
  (let [board-seq (seq board)]
    (loop [board-seq board-seq 
           new-board {}]
      (let [cell (first board-seq)]
        (if (nil? cell)
          new-board
          (recur (rest board-seq) (conj new-board (next-cell board cell))))))))

(defn board->str
  [board]
  (let [min-i 0
        max-i (apply max (map first (keys board)))
        min-j 0
        max-j (apply max (map second (keys board)))]
    (apply str
      (for [i (range min-i (inc max-i))
            j (range min-j (inc max-j))]
        (let [delimiter (if (= j max-j) " \n" " ")]
          (if (get (get board [i j]) :alive?)
            (str "x" delimiter)
            (str "-" delimiter)))))))

(defn -main
  [& args]
  true)
