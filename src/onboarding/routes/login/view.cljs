(ns onboarding.routes.login.view
  (:require
    ["@mui/icons-material" :as mui-icons]
    ["@mui/lab" :as mui-lab]
    ["@mui/material" :as mui]
    [onboarding.components.textfield :refer [textfield]]
    [onboarding.routes.login.events :as login-route-events]
    [onboarding.routes.login.subs :as login-page-subs]
    [re-frame.core :as rf]
    [reagent.core :as r]))


(defn view
  []
  (let [form-status @(rf/subscribe [::login-page-subs/form-status])
        form-errors @(rf/subscribe [::login-page-subs/form-errors])
        submit-handler (fn [event]
                         (let [form-data (js/FormData. (.-target event))]
                           (.preventDefault event)
                           (when-not (= form-status "loading")
                             (rf/dispatch
                               [::login-route-events/log-in
                                {:email (.get form-data "email")
                                 :password (.get form-data "password")}]))))]
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

      [:img {:src "/images/login-logo.svg"
             :alt "Login logo"
             :width "150px"
             :height "150px"}]

      [:> mui/Box {:component "form"
                   :display "flex"
                   :flexDirection "column"
                   :alignItems "center"
                   :justifyContent "center"
                   :gap "16px"
                   :onSubmit submit-handler}

       [textfield {:label "Email"
                   :name "email"
                   :error (= form-status "failure")
                   :required true
                   :auto-focus true}]

       [textfield {:type "password"
                   :label "Password"
                   :error (= form-status "failure")
                   :helper-text form-errors
                   :required true
                   :name "password"}]

       [:> mui-lab/LoadingButton {:type "submit"
                                  :variant "contained"
                                  :disabled (= form-status "loading")
                                  :loading (= form-status "loading")
                                  :loadingPosition (when (= form-status "loading") "start")
                                  :startIcon (when (= form-status "loading")
                                               (r/as-element [:> mui-icons/Save]))
                                  :sx {:width "100%"}}
        "Log in"]]]]))
