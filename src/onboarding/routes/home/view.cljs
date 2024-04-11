(ns onboarding.routes.home.view
  (:require
    ["@mui/material" :as mui]
    [onboarding.routes.home.events :as home-events]
    [onboarding.subs :as core-subs]
    [re-frame.core :as rf]))


(defn home-page
  []
  (let [session (rf/subscribe [::core-subs/session])]
    [:<>
     [:> mui/Box {:sx (clj->js {:flexGrow 1})}
      [:> mui/AppBar {:position "static"}
       [:> mui/Toolbar
        [:img {:src "/images/navbar-logo.svg"
               :alt "Navbar logo"
               :width "40px"
               :height "40px"}]
        [:> mui/Typography {:component "div" :sx (clj->js {:flexGrow 1})}]

        (when-not (nil? session)
          [:> mui/Button {:color "inherit"
                          :onClick #(rf/dispatch [::home-events/log-out])}
           "Log out"])]]]]))
