(ns clione.clj.util
  ;(:use [clione.clj.web.classpath :only (class-root)])
  (:import [java.net URL]))
(defmacro for! [seq-exprs body]
  `(doall (for ~seq-exprs ~body)))

(defmacro map! [f & colls]
  `(doall (map ~f ~@colls)))

(defmacro reload [ns]
  `(use (quote ~ns) :reload))

(defmacro debug [lisp]
  `(do
     (let [expr# (list ~@lisp)]
       (println "expr:" expr#)
       (println "invoke function ["  (second expr# ) "] with params" (drop 2 expr#)))
     
     (let [ret# (time ~lisp)]
       (println "and the result is :" ret#)
       ret#)))

;; the follow macro are all come from 《clojure in Action》
(defmacro defonce [name expr]
  `(let [v# (def ~name)]
     (when-not (.hasRoot v#)
       (def ~name ~expr))))


