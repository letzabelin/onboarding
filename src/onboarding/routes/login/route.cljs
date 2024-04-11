(ns onboarding.routes.login.route
  (:require
    [onboarding.routes.login.events :as login-page-events]
    [onboarding.routes.login.view :refer [login-page]]
    [re-frame.core :as re-frame]))


(def ^:private login-controllers
  [{:start #(re-frame/dispatch [::login-page-events/index %])}])


(def login-route
  {:name ::login
   :view login-page
   :controllers login-controllers})
