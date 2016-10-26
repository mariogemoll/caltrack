(ns caltrack.view.home
  (:use [caltrack.view.page]))

(defn home []
  (generic-page
   "Caltrack"
   "home"
   "home"
   [:div.container
    [:div.narrow-centered
     [:h1 "Caltrack"]
     [:form#login-form
      [:div.form-group
       [:label {:for "passphrase"} "Passphrase"]
       [:input#passphrase.form-control {:type "password"}]]
      [:input.btn.btn-lg.btn-primary.btn-block {:type "submit" :value "Log in"}]]]]))
