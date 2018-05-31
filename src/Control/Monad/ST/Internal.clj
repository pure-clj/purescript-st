(ns Control.Monad.ST.Internal._foreign
  (:refer-clojure :exclude [while for read]))

(defn map_ [f]
  (fn [a]
    (fn []
      (f (a)))))

(defn pure_ [a]
  (fn []
    a))

(defn bind_ [a]
  (fn [f]
    (fn []
      ((f (a))))))

(defn run [f]
  (f))

(defn while [f]
  (fn [a]
    (fn []
      (clojure.core/while (f)
        (a)))))

(defn for [lo]
  (fn [hi]
    (fn [f]
      (fn []
        (doseq [i (range lo hi)]
          ((f i)))))))

(defn foreach [as]
  (fn [f]
    (fn []
      (doseq [x as]
        ((f x))))))

(defn new [val]
  (fn []
    (atom val)))

(defn read [ref]
  (fn []
    @ref))

(defn modify' [f]
  (fn [ref]
    (fn []
      (let [t (f @ref)]
        (reset! ref (get t "state"))
        (get t "value")))))

(defn write [a]
  (fn [ref]
    (fn []
      (reset! ref a))))
