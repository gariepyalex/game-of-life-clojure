(ns game-of-life.core-test
  (:require [clojure.test :refer :all]
            [game-of-life.core :refer :all]))


; This is the test board
;  0 1 2 3 4
; 0- - - x -
; 1- x - x -
; 2- x x x -
; 3- x - x -
; 4- - - - -
(def test-board
  {[0 0] {:alive? false}
   [0 1] {:alive? false}
   [0 2] {:alive? false}
   [0 3] {:alive? true}
   [0 4] {:alive? false}
   [1 0] {:alive? false}
   [1 1] {:alive? true}
   [1 2] {:alive? false}
   [1 3] {:alive? true}
   [1 4] {:alive? false}
   [2 0] {:alive? false}
   [2 1] {:alive? true}
   [2 2] {:alive? true}
   [2 3] {:alive? true}
   [2 4] {:alive? false}
   [3 0] {:alive? false}
   [3 1] {:alive? true}
   [3 2] {:alive? false}
   [3 3] {:alive? true}
   [3 4] {:alive? false}
   [4 0] {:alive? false}
   [4 1] {:alive? false}
   [4 2] {:alive? false}
   [4 3] {:alive? false}
   [4 4] {:alive? false}})

(deftest test-neighbors-set
  (testing
    (is (= #{[0 1] [1 0] [1 1]} 
           (neighbors-of-position test-board [0 0])))
    (is (= #{[0 0] [0 2] [1 0] [1 1] [1 2]} 
           (neighbors-of-position test-board [0 1])))
    (is (= #{[1 0] [3 0] [1 1] [2 1] [3 1]} 
           (neighbors-of-position test-board [2 0])))
    (is (= #{[1 1] [1 2] [1 3] [2 1] [2 3] [3 1] [3 2] [3 3]} 
           (neighbors-of-position test-board [2 2])))))

(deftest test-number-of-alive-neighbors
  (testing 
    (is (= 1 (number-of-alive-neighbors test-board [0 1])))
    (is (= 2 (number-of-alive-neighbors test-board [1 0])))
    (is (= 6 (number-of-alive-neighbors test-board [1 2])))
    (is (= 5 (number-of-alive-neighbors test-board [3 2])))
    (is (= 3 (number-of-alive-neighbors test-board [2 3])))))

(deftest test-alive-cells
  (testing 
    (is (will-be-alive? test-board [1 1]))
    (is (will-be-alive? test-board [2 1]))
    (is (will-be-alive? test-board [3 1]))
    (is (not (will-be-alive? test-board [3 0])))
    (is (not (will-be-alive? test-board [2 2])))
    (is (not (will-be-alive? test-board [0 3])))))

(deftest test-next-cell
  (testing 
    (is (= (next-cell test-board [[1 1] {}])
           [[1 1] {:alive? true}]))))

(print (board->str test-board))

(print (board->str (next-board test-board)))

(loop [board test-board n 0]
  (when (< n 50)
    (do
      (println (board->str board))
      (recur (next-board board) (inc n)))))
