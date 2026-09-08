(ns association.facts-test
  "What the catalog must hold to be worth citing.

  The counts below are deliberately exact rather than `pos?`: this catalog's
  failure mode is not a wrong answer, it is a quiet shrink -- an entry dropped
  in an edit reads the same as an entry that was never there."
  (:require [clojure.test :refer [deftest is testing]]
            [kotoba.lang.text :as str]
            [association.facts :as facts]))

(def ^:private entries (facts/spec-basis "lewiatan"))

(deftest lewiatan-has-spec-basis
  (is (= 10 (count entries)))
  (is (every? #(= "9411" (:association-rule/isic %)) entries))
  (is (every? #(= "POL" (:association-rule/country %)) entries)))

(deftest unknown-association-has-no-spec-basis
  (is (nil? (facts/spec-basis "spcr")))
  (is (nil? (facts/spec-basis "zzz"))))

(deftest coverage-is-honest
  (let [c (facts/coverage ["lewiatan" "spcr"])]
    (is (= 2 (:requested c)))
    (is (= 1 (:covered c)))
    (is (= ["spcr"] (:missing-associations c)))))

(deftest by-topic-filters
  (is (= 3 (count (facts/by-topic "lewiatan" :governance))))
  (is (= 3 (count (facts/by-topic "lewiatan" :european-representation))))
  (is (= 1 (count (facts/by-topic "lewiatan" :social-dialogue))))
  (is (empty? (facts/by-topic "lewiatan" :labor)))
  (is (empty? (facts/by-topic "spcr" :governance))))

(deftest every-entry-carries-the-span-its-claim-rests-on
  ;; A citation without a quote cannot be checked against its own source: the
  ;; URL still resolves long after the page stops saying the thing.
  (doseq [e entries]
    (testing (:association-rule/id e)
      (is (string? (:association-rule/source-quote e)))
      (is (< 20 (count (:association-rule/source-quote e)))
          "a span too short to be distinctive matches by accident")
      (is (string? (:association-rule/source-article e)))
      (is (str/starts-with? (:association-rule/url e) "https://")))))

(deftest a-dated-entry-says-how-precisely-its-source-dates-it
  ;; Precision is a property of the source. Recording it per entry is what
  ;; lets the README's judgement about month-precise dates be checked rather
  ;; than restated.
  (doseq [e entries]
    (testing (:association-rule/id e)
      (let [d (:association-rule/established-date e)
            p (:association-rule/date-precision e)]
        (if d
          (do (is (#{:day :month :year} p))
              (is (= (case p :day 3 :month 2 :year 1)
                     (count (str/split d #"-")))
                  "the precision claimed must match the date written"))
          (do (is (nil? p))
              (is (keyword? (:association-rule/date-unknown-because e))
                  "undated is a fact about the source and is recorded, not hidden")))))))

(deftest the-catalog-does-not-rest-on-a-single-page
  ;; Both entries this catalog started with cited one page. That is one
  ;; redesign away from citing nothing, and nothing in the data said so.
  (let [srcs (facts/sources)]
    (is (= 6 (count srcs)))
    (is (= 10 (reduce + (map count (vals srcs)))))
    (is (some #(str/includes? % "businesseurope.eu") (keys srcs))
        "the BusinessEurope membership is corroborated from the other side")))

(deftest undated-entries-name-their-reason
  (let [u (facts/undated)]
    (is (= 2 (count u)))
    (is (every? (comp keyword? second) u))))
