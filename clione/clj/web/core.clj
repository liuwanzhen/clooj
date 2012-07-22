(ns clione.clj.web.core
  (:use [clojure.string :only (join split)]
        [clione.clj.web.classpath :only (class-root)]
        [clojure.data.json ]
        [clione.clj.util ]
        [clojure.data.json :only (read-json json-str)])
  (:import [java.io File]
           [java.nio.charset Charset]))

(def ^:dynamic *debug* true)
(def ^:dynamic *request* nil)
(def ^:dynamic *response* nil)
(def ^:dynamic *default-encoding* "UTF-8")

(def url-map (atom {}))
(def load-path-set (atom #{}))

(defn load-clj-file 
  [^File file] 
  (println "load-file " (.toString file))
    (if (.isFile file)
      (load-file (str file))
      (let [fseq (file-seq file)]
         (map! load-clj-file (filter #(.isFile %) fseq))))) 


(defn mvc-package 
  ([package] (let [path (.replace package "." "/")] 
               (swap! load-path-set conj path)
               (load-clj-file (File. class-root path))))
  ([package & more] 
        (map! mvc-package (conj more package))))



(defmacro defwebmethod [url fname params & body]
  `(let [url# ~url
         empty# ~(empty? params)
         param-str#   (if empty#
                        ""
                        (join "," (quote ~(map name (conj params :map-when-params-empty-bug)))))
                      ]
     (swap! url-map assoc  url#  
                                   {:fn (defn ~fname 
                                       ([~@params ~'request ~'response]  ~@body)
                                       ([~@params]  (~fname ~@params nil nil ))
                                      ) 
                                  :param-str param-str#})
    ))

 



(defn process-request* [f params ]
     (if *debug* 
         (debug (apply f params))
        (apply f params)))
 
(defn render-html [ret]
  (for! [[k v]  ret]
    (when-not (keyword? k)
      (.setAttribute *request* k v)))
  (-> *request*
    (.getRequestDispatcher (:out ret))
    (.forward *request* *response*)))
 
(defn render-json [ret]
  (.setContentType *response* "application/json")
  (.. *response* 
     (getWriter)
     (write  (json-str (dissoc ret :out) :escape-unicode false ) ))
  )

(defn render* [suffix ret  ]
  (.setCharacterEncoding *response* *default-encoding*)
  (condp = suffix
    "do"   (render-html ret )
    "json" (render-json ret)
    ))


(defn process-request [request response]
  (binding [*request* request
            *response* response]
  (let [ servlet-path  (. *request* getServletPath)
         [path suffix] (split servlet-path #"\.")
         matcher-fn (get-in @url-map [path :fn])
         param-str-seq (->(get-in @url-map [path :param-str])
                          (split #","))
         params (map! #(.getParameter *request* %1) (take (- (count param-str-seq) 1) param-str-seq))
         ret (process-request* matcher-fn params )]
        (render*  suffix ret ))))

(defn default-encoding [encdoing]
  (println (str "reset encoding from [" *default-encoding* "] to [" encdoing "]"))
  (def ^:dynamic *default-encoding* encdoing))

 (prn @url-map)
