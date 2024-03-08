(ns onboarding.core
  (:require
    [reagent.core :as r]
    [reagent.dom :as d]))


(defn home-page
  []
  [:div [:h2 "Hello world"]])


(defn mount-root
  []
  (d/render [home-page] (.getElementById js/document "app")))


(defn ^:export init!
  []
  (mount-root))
