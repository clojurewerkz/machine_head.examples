(ns clojurewerkz.machine-head.examples.weathr
  (:gen-class)
  (:require [clojurewerkz.machine-head.client :as mh]))

(defn handle-delivery
  [^String topic _ ^bytes payload]
  (println (format "[consumer] received %s for topic %s" (String. payload "UTF-8") topic)))


(defn -main
  [& args]
  (let [id    (mh/generate-id)
        conn  (mh/connect "tcp://127.0.0.1:1883" id)]
    (mh/subscribe conn {"americas/north/#" 1} handle-delivery)
    (mh/subscribe conn {"americas/south/#" 1} handle-delivery)
    (mh/subscribe conn {"americas/north/us/ca/+" 1} handle-delivery)
    (mh/subscribe conn {"#/tx/austin" 1} handle-delivery)
    (mh/subscribe conn {"europe/italy/rome" 1} handle-delivery)
    (mh/subscribe conn {"asia/southeast/hk/+" 1} handle-delivery)
    (mh/subscribe conn {"asia/southeast/#" 1} handle-delivery)
    (mh/publish conn "americas/north/us/ca/sandiego"     "San Diego update")
    (mh/publish conn "americas/north/us/ca/berkeley"     "Berkeley update")
    (mh/publish conn "americas/north/us/ca/sanfrancisco" "SF update")
    (mh/publish conn "americas/north/us/ny/newyork"      "NYC update")
    (mh/publish conn "americas/south/brazil/saopaolo"    "São Paolo update")
    (mh/publish conn "asia/southeast/hk/hongkong"        "Hong Kong update")
    (mh/publish conn "asia/southeast/japan/kyoto"        "Kyoto update")
    (mh/publish conn "asia/southeast/prc/shanghai"       "Shanghai update")
    (mh/publish conn "europe/italy/roma"                 "Rome update")
    (mh/publish conn "europe/france/paris"               "Paris update")
    (Thread/sleep 250)
    (mh/disconnect conn)
    (System/exit 0)))
