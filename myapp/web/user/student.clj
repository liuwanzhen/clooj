(ns myapp.web.user.student
  (:use [clione.clj.web.core]))

(defwebmethod "/student"
  student [id] 
  (println "id==" id)
  {:out "/test1.jsp"
   "name" (str "学生:" id)
   "id" "123"})