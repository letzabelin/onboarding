(ns onboarding.pages.login.view
  (:require
    ["@mui/material" :as mui]
    [onboarding.components.textfields :refer [textfield password-textfield]]))


(defn login-page
  []
  [:> mui/Box {:display "flex"
               :alignItems "center"
               :justifyContent "center"
               :width "100vw"
               :height "100vh"
               :bgcolor "#F6F6F6"}

   [:> mui/Box {:component "section"
                :display "flex"
                :alignItems "center"
                :justifyContent "space-evenly"
                :width "40%"
                :height "40%"
                :boxShadow "0px 2px 6px -1px rgba(0, 0, 0, 0.12)"
                :bgcolor "#FFFFFF"
                :borderRadius "8px"}

    [:img {:src "/images/logo.svg"
           :alt "Login logo"
           :width "150px"
           :height "150px"}]

    [:> mui/Box {:component "form"
                 :display "flex"
                 :flexDirection "column"
                 :alignItems "center"
                 :justifyContent "center"
                 :gap "16px"
                 :onSubmit #(do (.preventDefault %)
                                (println "form submit"))}

     [textfield {:label "Username"}]

     [password-textfield {:label "Password"}]

     [:> mui/Button {:type "submit" :variant "contained" :sx {:width "100%"}} "Login"]]]])
