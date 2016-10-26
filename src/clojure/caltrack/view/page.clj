(ns caltrack.view.page
  (:use [hiccup.page]))

(defn header-nav []
  (let [logout-js "function submitloginform() {document.logoutform.submit(); return false;} submitloginform()"]
    [:div.headernav
     [:h2 "Caltrack"]
     [:ul
      [:li [:a {:href "/report"} "Report"]]
      [:li
       [:a {:href "#" :onclick logout-js} "Logout"]]]
     [:form#logoutform {:action "/logout" :name "logoutform" :method "POST"}
      [:input {:type "hidden" :name "foo" :value "bar"}]]]))

(defn generic-page [title script css body-content]
  (html5
   [:head
    [:title title]
    (include-css "/assets/bootstrap/css/bootstrap.min.css")
    (include-css "/assets/caltrack.css")
    (include-css (str "/assets/" css ".css"))]
   [:body
    body-content
    (include-js (str "/assets/" script ".min.js"))]))

(defn page [title script css content]
  (generic-page
   title
   script
   css
   [:div.container
    (header-nav)
    content
    [:div.footer]]))
