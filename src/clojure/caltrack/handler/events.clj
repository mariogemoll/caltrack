(ns caltrack.handler.events
  (:require [caltrack.caldav :as caldav]))

(defn get-events [{params :params session :session :as req}]
  (let [events (caldav/get-data (:from params) (:to params))]
    {:body
     {:events events}}))
