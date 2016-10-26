(ns caltrack.handler.report
    (:require [caltrack.view.report :as v]))

(defn get-report [req]
  (v/report))
