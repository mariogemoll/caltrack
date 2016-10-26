(ns caltrack.handler.home
  (:require [caltrack.view.home :as v])
  (:use [ring.util.response]))

(defn get-home [{session :session :as req}]
  (if (:logged-in session)
    (redirect "/report")
    (v/home)))

(defn get-count [{session :session}]
  (let [count   (:count session 0)
        session (assoc session :count (inc count))]
    (-> (response (str "You accessed this page " count " times."))
        (assoc :session session))))
