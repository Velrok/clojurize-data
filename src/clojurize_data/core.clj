(ns clojurize-data.core)

(defn- type-dispatch
  [data]
  (cond
      (keyword? data) :identidy
      (number? data)  :identidy
      (string? data)  :identidy
      (set? data)     :set
      (seq? data)     :seq
      (vector? data)  :vector
      (map? data)     :map
      :else           (class data)))

(defmulti clojurize
  (fn [data & _]
    (type-dispatch data)))

(defmethod clojurize :identidy 
  [data & _] 
  data)

(defn replace-underscore-with-hyphon 
  [s]
  (clojure.string/replace s #"_" "-"))

(defn- convert-map-key
  [data options]
  (cond-> data
    (contains? options :convert-hyphons-to-underscores-in-map-keys)
    replace-underscore-with-hyphon
    
    true
    (keyword)))

(defmethod clojurize :map 
  [data & options] 
  (let [options (set options)]
    (into {}
          (for [[k v] data]
            [(convert-map-key k options) 
             (clojurize v options)]))))

(defn is-snake-case? 
  [s]
  (not (nil? (re-matches #"\w*(_\w*)*" s))))

(defn convert-snake-case-to-keyword 
  [s]
  (if (is-snake-case? s)
    (keyword s)
    s))

(defmethod clojurize :vector
  [data & options]
  (into []
        (map #(apply clojurize (cons % options)) data)))

(defmethod clojurize :seq
  [data & options]
  (map #(apply clojurize [% options]) data))
