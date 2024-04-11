(ns onboarding.core
  (:require
    ["@mui/material" :as mui]
    [devtools.core :as devtools]
    [onboarding.config.default :as config]
    [onboarding.events :as core-events]
    [onboarding.routing.router :as router]
    [re-frame.core :as rf]
    [reagent.dom :as r]))


(when config/debug?
  (enable-console-print!))


(defn dev-setup
  []
  (when config/debug?
    (println "dev mode")
    (devtools/install!)))


(defn main
  []
  [:<> [:> mui/CssBaseline]
   [router/current-page]])


(defn mount-root
  []
  (rf/clear-subscription-cache!)
  (router/init-routes!)
  (r/render [main]
            (.getElementById js/document "root")))


(defn ^:export init
  []
  (rf/dispatch-sync [::core-events/initialize-db])
  (dev-setup)
  (mount-root))
