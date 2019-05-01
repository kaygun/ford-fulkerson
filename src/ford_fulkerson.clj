(ns ford-fulkerson
  (:gen-class))

(defn clean-graph [G]
  (let [ks (into #{} (keys G))
        H (->> (map reverse ks)
               (filter ks)
               (mapcat (fn [k] (let [v0 (G k) v1 (G (reverse k))]
                                   (if (> v0 v1)
                                       {k (- v0 v1)}
                                       {(reverse k) (- v1 v0)}))))
               (into {}))]
      (as-> (map reverse (keys H)) $
            (concat (keys H) $)
            (into [] $)
            (merge (apply dissoc G $) H)
            (filter (fn [[k v]] (> v 0)) $))))

(defn random-graph [n m k w]
  (->> (range n)
       (mapcat (fn [i] (repeatedly m (fn [] [i (+ 1 i (rand-int k))]))))
       distinct
       (mapcat (fn [x] {x (+ 1 (rand-int w))}))
       (into {})))

(defn residual-graph [G S]
   (->> (mapcat (fn [[k v]] {k (- v) (reverse k) v}) S)
        (into {})
        (merge-with + G)
        (filter (fn [[k v]] (> v 0)))
        (into {})))

(defn find-a-path [G a b]
  (loop [H G
         x a
         P []]
    (let [ys (->> (keys H) (filter (fn [[u v]] (= u x))))]
      (cond (or (empty? H) (nil? x)) []
            (contains? (into #{} ys) [x b]) (reverse (cons [x b] P))
            (empty? ys) (recur (dissoc H (first P)) (-> P first first) (rest P))
            :true (recur (dissoc H (first ys)) (-> ys first second) (cons (first ys) P))))))

(defn ford-fulkerson [G a b]
   (loop [H G S {}]
     (let [R (find-a-path H a b)]
       (if (empty? R)
         (into {} (clean-graph S))
         (let [v (reduce min (map H R))
               P (zipmap R (repeat v))]
           (recur (residual-graph H P) (merge-with + S P)))))))

(defn -main []
   (let [G (random-graph 20 5 5 10)]
      (println (str (ford-fulkerson G 0 20)))))
