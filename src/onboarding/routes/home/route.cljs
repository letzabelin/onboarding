(ns onboarding.routes.home.route
  (:require
    [onboarding.routes.home.view :refer [home-page]]))


(def ^:private home-controllers
  [{:start (fn [_] (println "hello from home"))}])


(def home-route
  {:name :home
   :view home-page
   :controllers home-controllers})
