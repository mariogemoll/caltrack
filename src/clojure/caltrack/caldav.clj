(ns caltrack.caldav
  (:require [caltrack.config :as config]
            [clojure.data.xml :as xml])
  (:import (biweekly Biweekly)
           (caltrack.http HttpReport)
           (org.apache.http.auth AuthScope UsernamePasswordCredentials)
           (org.apache.http.entity StringEntity)
           (org.apache.http.impl.client BasicCredentialsProvider HttpClientBuilder)
           (org.apache.http.util EntityUtils)))

(defn make-http-client [username password]
  (let [provider (BasicCredentialsProvider.)]
    (.setCredentials provider AuthScope/ANY (UsernamePasswordCredentials. username password))
    (-> (HttpClientBuilder/create)
        (.setDefaultCredentialsProvider provider)
        (.build))))

(defn format-short [iso-8601-string]
  "Remove dashes and colons, i.e. convert \"YYYY-MM-DDThh:mm:ssZ\" into \"YYYYMMDDhhmmssZ\""
  (clojure.string/join (remove #{\- \:} iso-8601-string)))

(defn query-xml-string [from to]
  (xml/emit-str
   (xml/sexp-as-element
   [:c:calendar-query {:xmlns:d "DAV:" :xmlns:c "urn:ietf:params:xml:ns:caldav"}
    [:d:prop
     [:c:calendar-data]]
    [:c:filter
     [:c:comp-filter {:name "VCALENDAR"}
      [:c:comp-filter {:name "VEVENT"}
       [:c:time-range {:start from :end to}]]]]])))

(defn get-xml [from to]
  (let [request (HttpReport. config/caldav-url)]
    (.setHeader request "Depth" "1")
    (.setEntity request (StringEntity. (query-xml-string (format-short from) (format-short to))))
    (with-open [http-client (make-http-client config/caldav-username config/caldav-password)
                response (.execute http-client request)]
      (xml/parse (java.io.StringReader. (EntityUtils/toString (.getEntity response)))))))

(defn extract-ical-from-result [result]
  (first (:content (first (:content (first (:content (second (:content result)))))))))

(defn ical-to-events [input]
  (.getEvents (.first (Biweekly/parse input))))

(defn extract-data [e]
  {:title (.getValue (.getSummary e))
   :start (.getValue (.getDateStart e))
   :end (.getValue (.getDateEnd e))})

(defn get-data [from to]
  (map #(extract-data (first (ical-to-events %)))
       (remove nil?
               (map #(extract-ical-from-result %)
                    (:content (get-xml from to))))))
