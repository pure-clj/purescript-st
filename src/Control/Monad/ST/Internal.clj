(ns Control.Monad.ST.Internal._foreign
  (:refer-clojure :exclude [while for read]))

(defn map_ [f]
  (fn [a]
    (fn [& _]
      (f (a nil)))))

(defn pure_ [a]
  (fn [& _]
    a))

(defn bind_ [a]
  (fn [f]
    (fn [& _]
      ((f (a nil)) nil))))

(defn run [f]
  (f nil))

(defn while [f]
  (fn [a]
    (fn [& _]
      (clojure.core/while (f nil)
        (a nil)))))

(defn for [lo]
  (fn [hi]
    (fn [f]
      (fn [& _]
        (doseq [i (range lo hi)]
          ((f i) nil))))))

(defn foreach [as]
  (fn [f]
    (fn [& _]
      (doseq [x as]
        ((f x) nil)))))

(defn new [val]
  (fn [& _]
    (atom val)))

(defn read [ref]
  (fn [& _]
    @ref))

(defn modify' [f]
  (fn [ref]
    (fn [& _]
      (let [t (f @ref)]
        (reset! ref (get t "state"))
        (get t "value")))))

(defn write [a]
  (fn [ref]
    (fn [& _]
      (reset! ref a))))
