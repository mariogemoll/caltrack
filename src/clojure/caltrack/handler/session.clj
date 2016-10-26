(ns caltrack.handler.session
  (:require [caltrack.config :as c]
            [caltrack.view.logout :as logout-view])
  (:use [ring.util.response]))

(defn post-login [{json-data :body session :session :as req}]
  (if (= (:passphrase json-data) c/passphrase)
    (-> (response {})
        (assoc :session (assoc session :logged-in true)))
    {:status 403
     :body {:error "Wrong passphrase"}}))

(defn post-logout [_]
  (-> (response (logout-view/logout))
      (assoc :session nil)))
