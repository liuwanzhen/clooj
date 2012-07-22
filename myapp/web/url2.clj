(ns myapp.web.url2
  (:use [clione.clj.web.core]
        [clojure.data.json :only (read-json json-str)]))


(defwebmethod "/ttt"
  ttt [id] 
  (println "id==" id)
  (println "json-str==" (json-str "刘万震112" :escape-unicode false))
  {:out "/test1.jsp"
   "name" "烽火普天"
   "id" "123"})

(defwebmethod "/lwz" lwz []
  {:out "/test1.jsp"
   "name" "烦人"
   "age" 30
   "address" {:id "lwz"
              :city "wuhan"}})
