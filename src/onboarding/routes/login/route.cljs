(ns onboarding.routes.login.route
  (:require
    [onboarding.routes.login.events :as login-route-events]
    [onboarding.routes.login.view :refer [view]]
    [re-frame.core :as rf]))


(def ^:private login-controllers
  [{:start #(rf/dispatch [::login-route-events/index])}])


(def login-route
  {:name :login
   :view view
   :controllers login-controllers})
