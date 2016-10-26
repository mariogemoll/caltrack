(ns caltrack.config)

(defn ev [name]
  "Returns the environment variable CALTRACK_name or throws an exception."
  (or (System/getenv (str "CALTRACK_" name))
      (throw (Exception. (str "Environment variable CALTRACK_" name " not set.")))))

(def caldav-url (ev "CALDAV_URL"))
(def caldav-username (ev "CALDAV_USERNAME"))
(def caldav-password (ev "CALDAV_PASSWORD"))
(def passphrase (ev "PASSPHRASE"))
