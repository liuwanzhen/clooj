(ns clj.repl
  (:use [clojure.java.io :as io])
  (:use [clj.util :as util])
  (:use [clj.protocal :as protocal])
  (:import clj.Shell))

(import '(java.net ServerSocket Socket SocketException )
        '(java.io InputStreamReader OutputStreamWriter DataInputStream ByteArrayOutputStream)
        '(clojure.lang LineNumberingPushbackReader))

 

 
 ;(defn parse-by-java-shell [content]
 ;  (let [barray (Shell/execute content)]
  ;   (String. barray)))
 
 ;(defn parse-shell-by-linux-bash [content]
 ;   (println-local "parse-shell-by-linux-bash")
 ; (let [bash (.. (Runtime/getRuntime) (exec "/bin/bash"))
  ;       bash-ins  (.getInputStream bash)
  ;;       bash-outs  (.getOutputStream bash)
   ;      bos (ByteArrayOutputStream.)]
  ;   (.write bash-outs (.getBytes (str content "\r\n")))
   ;  (.write bash-outs (.getBytes (str "exit" "\r\n")))
   ;  (.flush bash-outs)
   ;  (println-local "ready for copy :" content)
    ; (.waitFor bash)
   ;  (println-local "ready for copy 11")
;     (io/copy bash-ins bos)
;     (println-local "ready for copy 22")
;     (String. (.toByteArray bos) )))



;;(defn reset-mode [m]
 ;; (reset! mode (keyword m)))

(defn on-thread [f]
  (doto (new Thread f) (.start)))
 
(defn create-server 
  "creates and returns a server socket on port, will pass the client
  socket to accept-socket on connection" 
  [accept-socket port]
    (let [ss (new ServerSocket port)]
      (on-thread #(when-not (. ss (isClosed))
                    (try (accept-socket (. ss (accept)))
                         (catch SocketException e))
                    (recur)))
      ss))
 

 
;;(defn read-stream-as-str [is]
  ;;(let [len (.read is buf)]
    ;;(String. buf 0 len)))


(defn repl
  "runs a repl on ins and outs until eof"
  [ins outs]
  (let [protocal (util/readReqStr ins (byte-array 4))]
    (condp = protocal
    "0001" (protocal/process-protocal-text ins outs)
    "0002" (println-local "aaaaaa")
    )) )
 
(defn socket-repl 
  "starts a repl thread on the iostreams of supplied socket"
  [s] (on-thread #(repl (. s (getInputStream)) (. s (getOutputStream)))))
 

;; (on-thread #(loop [count 1]
;;                (. System/out println ( str "hello--" count ":")) 
;;		(. Thread sleep hello/sleep-time)
;;		  (recur (inc count))))


; ( println  "hello####" *command-line-args* )

;;	(defn say-hello []
;;	   ( println  "hello" (. System currentTimeMillis) )
 ;;	  (. Thread sleep hello/xx)
 ;;	   (recur))

;;	(defn say-args []
;;	   ( println  "say-args" *command-line-args* )
 ;;	  )

;;(on-thread say-hello)
;;( say-args)
(def server (create-server socket-repl 13579))



