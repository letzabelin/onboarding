(ns onboarding.core
  (:require
    ["@mui/material" :as mui]
    [devtools.core :as devtools]
    [onboarding.config :as config]
    [onboarding.handlers]
    [onboarding.pages.home :refer [home-page]]
    [onboarding.routes :as routes]
    [onboarding.subs]
    [onboarding.views :as views]
    [re-frame.core :as rf]
    [reagent.dom :as reagent]))


(defn dev-setup
  []
  (when config/debug?
    (println "dev mode")
    (devtools/install!)))


(defn wrapper
  []
  [:<>
   [:> mui/CssBaseline]
   ;; [home-page]
   [views/current-page]])


(defn mount-root
  []
  (reagent/render [wrapper]
                  (.getElementById js/document "app")))


(defn ^:export init
  []
  (rf/dispatch-sync [:initialize-db])
  (dev-setup)
  (routes/app-routes)
  (mount-root))
