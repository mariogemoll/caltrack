(ns caltrack.view.report
  (:use [caltrack.view.page]
        [hiccup.page]))

(defn report []
  (page
   "Report"
   "report"
   "report"
   [:div#main
    [:div.header
     [:h1.hl "Report"]]
    [:div.row
     [:div.col-md-3
      [:h3 "Total"]
      [:p#total "..."]
      [:form#query
       [:h3 "Query"]
       [:table
        [:tr
         [:td
          [:label "From"]
          [:input#from.form-control {:type "text" :name "from"}]]
         [:td
          [:label "To"]
          [:input#to.form-control {:type "text" :name "to"}]]]]
       [:input {:class "btn btn-primary" :type "submit" :value "Update"}]]]
     [:div#list.col-md-9
      [:table#entries.table
       [:thead
        [:tr
         [:th "Timespan"]
         [:th "Duration"]
         [:th "Task"]]]]]]]))
