(ns onboarding.routes.home.route
  (:require
    [onboarding.routes.home.events :as home-route-events]
    [onboarding.routes.home.view :refer [home-page]]
    [re-frame.core :as rf]))


(def ^:private home-controllers
  [{:start #(rf/dispatch [::home-route-events/index])}])


(def home-route
  {:name :home
   :view home-page
   :controllers home-controllers})
