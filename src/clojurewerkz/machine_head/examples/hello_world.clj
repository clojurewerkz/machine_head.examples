(ns clojurewerkz.machine-head.examples.hello-world
  (:gen-class)
  (:require [clojurewerkz.machine-head.client :as mh]))

(defn -main
  [& args]
  (let [id   (mh/generate-id)
        conn (mh/connect "tcp://127.0.0.1:1883" id)]
    ;; In version clojurewerkz.machine-head <1.0.0beta8 the topic is a list instead of a hashmap
    ;; the following line in 1.0.0-beta8 would look as such below.
    ;;
    ;; (mh/subscribe conn ["hello"] (fn [^String topic _ ^bytes payload]
    ;;                              (println (String. payload "UTF-8"))))
    ;;
    ;; Note: that the 0 in the topic map represents the mqtt QoS value.

    (mh/subscribe conn {"hello" 0} (fn [^String topic _ ^bytes payload]
                                   (println (String. payload "UTF-8"))))
    (mh/publish conn "hello" "Hello, world")
    (Thread/sleep 100)
    (mh/disconnect conn)
    (System/exit 0)))
