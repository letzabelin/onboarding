(ns onboarding.routes.root.route
  (:require
    [onboarding.routes.root.events :as root-events]
    [re-frame.core :as re-frame]))


(def ^:private root-controllers
  [{:start #(re-frame/dispatch [::root-events/check-session])}])


(def root-route
  {:controllers root-controllers})
