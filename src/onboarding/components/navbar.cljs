(ns onboarding.components.navbar
  (:require
    ["@mui/icons-material" :as mui-icons]
    ["@mui/material" :as mui]))


(defn navbar
  []
  [:> mui/Box {:sx (js-obj "flexGrow" 1)}
   [:> mui/AppBar {:position "static"}
    [:> mui/Toolbar
     [:> mui/IconButton {:size "large"
                         :edge "start"
                         :color "inherit"
                         :aria-label "menu"}
      [:> mui-icons/Menu]]
     [:div "News"]
     [:> mui/Button {:color "inherit"} "Login"]]]])
