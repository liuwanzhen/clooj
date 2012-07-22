(ns clj.util
  (:import [java.io InputStreamReader OutputStreamWriter DataInputStream ByteArrayOutputStream]))
 
(defn println-local [& args]
    (binding [*out* (new OutputStreamWriter System/out)]  
          (apply println args)
          (print "\n")))
 (defn readInt [is]
   (let [dis (DataInputStream. is)]
     (.readInt dis)))
 
 (defn readReqStr [is buf]
   (.read is buf)
   (String. buf))