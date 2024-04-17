(ns onboarding.routes.home.view
  (:require
    ["@mui/material" :as mui]
    [clojure.string :as str]
    [onboarding.components.table :refer [table]]
    [onboarding.routes.home.events :as home-events]
    [onboarding.routes.home.subs :as home-subs]
    [onboarding.subs :as core-subs]
    [re-frame.core :as rf]))


(defn render-name
  [names]
  (->> names
       (filter #(= (:use %) "official"))
       (first)
       ((fn [value] (str (first (:given value)) " " (:family value))))
       ((fn [value] (str/replace value #"\d" "")))))


(defn render-age
  [birth-date]
  (let [diff (- (.now js/Date) (.getTime (js/Date. birth-date)))
        age (abs (- (.getUTCFullYear (js/Date. diff)) 1970))]
    age))


(defn render-phone-numbers
  [telecoms]
  (->> telecoms
       (map #(:value %))
       (str/join "\n")))


(defn render-weight
  [weight]
  (:value weight))


(defn render-height
  [height]
  (:value height))


(defn render-diagnosis
  [diagnosis]
  (:text diagnosis))


(def columns
  [{:id :id
    :label "ID"}
   {:id :name
    :label "Name"
    :format render-name}
   {:id :gender
    :label "Gender"}
   {:id :birthdate
    :label "Age"
    :format render-age}
   {:id :telecom
    :label "Phone numbers"
    :format render-phone-numbers}
   {:id :weight
    :label "Weight (kg)"
    :format render-weight}
   {:id :height
    :label "Height (cm)"
    :format render-height}
   {:id :diagnosis
    :label "Diagnosis"
    :format render-diagnosis}])


(defn home-page
  []
  (let [session @(rf/subscribe [::core-subs/session])
        logout-status @(rf/subscribe [::home-subs/logout-status])
        store @(rf/subscribe [::home-subs/store])]
    [:<>
     [:> mui/AppBar
      [:> mui/Toolbar
       [:img {:src "/images/navbar-logo.svg"
              :alt "Navbar logo"
              :width "40px"
              :height "40px"}]
       [:> mui/Typography {:component "div" :sx (clj->js {:flexGrow 1})}]

       (when-not (nil? session)
         [:> mui/Button {:color "inherit"
                         :disabled (= logout-status "loading")
                         :onClick #(when-not (= logout-status "loading")
                                     (rf/dispatch [::home-events/log-out]))}
          "Log out"])]]

     [:> mui/Toolbar]

     [:main
      [table {:columns columns
              :store store
              :on-page-change (fn [_ new-page] (rf/dispatch [::home-events/change-page new-page]))
              :on-records-per-page-change #(rf/dispatch [::home-events/change-records-per-page (-> % .-target .-value)])
              :on-page-reload #(rf/dispatch [::home-events/reload-page])}]]]))
