(ns onboarding.routes.home.route
  (:require
    [onboarding.routes.home.events :as home-route-events]
    [onboarding.routes.home.view :refer [view]]
    [re-frame.core :as rf]))


(def ^:private home-controllers
  [{:start #(rf/dispatch [::home-route-events/index])}])


(def home-route
  {:name :home
   :view view
   :controllers home-controllers})
