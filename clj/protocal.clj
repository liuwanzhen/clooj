(ns clj.protocal
  (:use [clj.util :as util])
  (:use [clojure.java.io :as io])
  (:import [java.io InputStreamReader OutputStreamWriter DataInputStream ByteArrayOutputStream]))

(def encoding "GBK")

 (defn parse-shell-by-process-builder [content]
  (let [pb (ProcessBuilder. (into-array String (.split content " ")))
        bos (ByteArrayOutputStream.)
       pc (.start pb)]
    (.waitFor pc)
    (io/copy (.getInputStream pc) bos)
   (String. (.toByteArray bos ) encoding )))
 
(defn parse-clj [content]
   (eval (read-string content) ))
 
(defn parse-entry [mode-str content]
  (condp = (keyword mode-str)
    :shell (parse-shell-by-process-builder content)
    :clj (parse-clj content)
    ))
 
 (defn parse-is [is]
  (let [len (util/readInt is)
        buf (byte-array len)
        req-str (util/readReqStr is buf)
        index (.indexOf req-str ":")]
    [(.substring req-str 0 index) 
     (.substring req-str (inc index))]))
 

(defn process-protocal-text [ins outs]
  (binding [*ns* (create-ns 'user)
              *warn-on-reflection* false
              *out* (new OutputStreamWriter outs encoding)
             ]
      (let [ ;;r (new InputStreamReader ins)
            [mode content] ( parse-is ins )]
          (util/println-local "mode:" mode "content" content)
        (try 
              ;; (prn (eval (read-string content) ))
               (println (parse-entry mode content))
               (catch Exception ex
                 (prn (.getMessage ex)))
               (finally (flush)))
        (.close ins)
        (.close outs)
      )))