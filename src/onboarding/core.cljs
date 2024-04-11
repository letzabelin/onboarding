(ns onboarding.core
  (:require
    ["@mui/material" :as mui]
    [devtools.core :as devtools]
    [onboarding.config.default :as config]
    [onboarding.events :as core-events]
    [onboarding.routing.router :as router]
    [re-frame.core :as re-frame]
    [reagent.dom :as reagent]))


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
  (re-frame/clear-subscription-cache!)
  (router/init-routes!)
  (reagent/render [main]
                  (.getElementById js/document "root")))


(defn ^:export init
  []
  (re-frame/dispatch-sync [::core-events/initialize-db])
  (dev-setup)
  (mount-root))
