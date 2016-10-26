(ns caltrack.view.logout
  (:use [caltrack.view.page]))

(defn logout []
  (generic-page
   "Logged out."
   nil
   nil
   [:div.container
    [:div.narrow-centered
     [:h1 "Caltrack"]
     [:p "You have been logged out."]]]))
