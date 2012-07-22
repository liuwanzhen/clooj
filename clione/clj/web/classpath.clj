(ns clione.clj.web.classpath
  (:import [clione.java.servlet ClioneServlet]))

(def class-root (ClioneServlet/CLASS_PATH))

