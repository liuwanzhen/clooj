(ns clione.clj.repl.repl
  )

(import '(java.net ServerSocket Socket SocketException SocketInputStream)
        '(java.io InputStreamReader OutputStreamWriter)
        '(clojure.lang LineNumberingPushbackReader))
 
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
 
(defn repl
  "runs a repl on ins and outs until eof"
  [ins outs]
    (binding [*ns* (create-ns 'user)
              *warn-on-reflection* false
              *out* (new OutputStreamWriter outs)
             ]
      ;; *out* (new OutputStreamWriter outs)
      (let [eof (new Object)
            r (new LineNumberingPushbackReader (new InputStreamReader ins))]
        (loop [e (read r false eof)]
          (when-not (= e eof)
             (try 
               (prn (eval e))
               (catch Exception ex
                 (prn (.getMessage ex)))
               (finally (flush)))
            (recur (read r false eof)))))))
 
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
;;;(def server (create-server socket-repl 13579))



