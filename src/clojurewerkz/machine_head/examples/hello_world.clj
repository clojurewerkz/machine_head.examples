(ns clojurewerkz.machine-head.examples.hello-world
  (:gen-class)
  (:require [clojurewerkz.machine-head.client :as mh]))

(defn -main
  [& args]
  (let [id   (mh/generate-id)
        conn (mh/connect "tcp://127.0.0.1:1883" id)]
    (mh/subscribe conn ["hello"] (fn [^String topic _ ^bytes payload]
                                   (println (String. payload "UTF-8"))
                                   (System/exit 0)))
    (mh/publish conn "hello" "Hello, world")))