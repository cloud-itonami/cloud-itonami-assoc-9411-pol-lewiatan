(ns association.facts-test
  (:require [clojure.test :refer [deftest is]]
            [association.facts :as facts]))

(deftest lewiatan-has-spec-basis
  (let [sb (facts/spec-basis "lewiatan")]
    (is (= 2 (count sb)))
    (is (every? #(= "9411" (:association-rule/isic %)) sb))
    (is (every? #(= "POL" (:association-rule/country %)) sb))))

(deftest unknown-association-has-no-spec-basis
  (is (nil? (facts/spec-basis "spcr")))
  (is (nil? (facts/spec-basis "zzz"))))

(deftest coverage-is-honest
  (let [c (facts/coverage ["lewiatan" "spcr"])]
    (is (= 2 (:requested c)))
    (is (= 1 (:covered c)))
    (is (= ["spcr"] (:missing-associations c)))))

(deftest by-topic-filters
  (is (= 2 (count (facts/by-topic "lewiatan" :governance))))
  (is (empty? (facts/by-topic "lewiatan" :labor)))
  (is (empty? (facts/by-topic "spcr" :governance))))
