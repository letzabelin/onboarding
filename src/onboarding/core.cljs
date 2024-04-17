(ns onboarding.core
  (:require
    ["@mui/material" :as mui]
    [devtools.core :as devtools]
    [onboarding.config.default :as config]
    [onboarding.events :as core-events]
    [onboarding.routing.router :as router]
    [onboarding.subs :as core-subs]
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
  (let [alert @(rf/subscribe [::core-subs/alert])
        on-alert-close (fn [_] (rf/dispatch [::core-events/close-alert]))]
    [:<>
     [:> mui/CssBaseline]
     [router/current-page]
     [:> mui/Snackbar {:open (:open? alert)
                       :autoHideDuration 3000
                       :onClose on-alert-close}
      [:> mui/Alert {:severity (:severity alert)
                     :onClose on-alert-close}
       (or (:text alert) "")]]]))


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
