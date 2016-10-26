(ns caltrack.web
  (:require [caltrack.handler.events :as events]
            [caltrack.handler.home :as home]
            [caltrack.handler.report :as report]
            [caltrack.handler.session :as session]
            [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.adapter.jetty :as jetty]
            [ring.middleware.json :refer [wrap-json-body wrap-json-response]]
            [ring.middleware.params :refer [wrap-params]]
            [ring.middleware.session :refer [wrap-session]]
            [ring.middleware.keyword-params :refer [wrap-keyword-params]])
  (:gen-class))

(defroutes the-routes 
  (GET  "/" [] home/get-home)
  (GET  "/report" [] report/get-report)
  (GET  "/count" [] home/get-count)
  (GET  "/_/events" [] events/get-events)
  (POST "/_/login" [] session/post-login)
  (POST "/logout" [] session/post-logout)
  (route/resources "/")
  (route/not-found "<h1>Page not found</h1>"))

(def app
  (-> the-routes
      (wrap-json-response)
      (wrap-session)
      (wrap-json-body {:keywords? true})
      (wrap-keyword-params)
      (wrap-params)))

(defn -main [& [port]]
  (let [port (Integer. (or port (System/getenv "PORT") 3000))]
    (jetty/run-jetty #'app {:port port :join? false})))
