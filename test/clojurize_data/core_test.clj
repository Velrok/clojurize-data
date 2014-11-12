(ns clojurize-data.core-test
  (:require [clojure.test :refer :all]
            [clojurize-data.core :refer :all]))

(clojurize ["hello_world" "i_am_rob"] 
           :convert-snake-case-strings-to-keywords
           :convert-hyphons-to-underscores)

(deftest clojureize
  (testing "it converts string keys in maps to keywords"
    (is (= (clojurize {"a" "b"})
           {:a "b"})))
  
  (testing "by default _ are not converted to -"
    (is (= (clojurize {"a_b" "c" "c-d" "e"})
           {:a_b "c"
            :c-d "e"})))

  (testing "the option :convert-hyphons-to-underscores-in-map-keys converts _ to - in map keys"
    (is (= (clojurize {"a_b" "c" "c-d" "e"} :convert-hyphons-to-underscores-in-map-keys)
           {:a-b "c"
            :c-d "e"})))
  
  (testing "general correctness"
    (is (= (clojurize {"a" ["a" :b {"c" :d} 23]})
           {:a ["a" :b {:c :d} 23]}))
    (is (= (clojurize "hello world")
           "hello world"))
    (is (= (clojurize '("hello" "world"))
           '("hello" "world")))
    (is (= (clojurize ["hello" "world"])
           ["hello" "world"]))
    (is (= (clojurize [{"a" "b"}])
           [{:a "b"}]))))
