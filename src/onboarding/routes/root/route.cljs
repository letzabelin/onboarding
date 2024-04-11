(ns onboarding.routes.root.route
  (:require
    [onboarding.routes.root.events :as root-events]
    [re-frame.core :as rf]))


(def ^:private root-controllers
  [{:start #(rf/dispatch [::root-events/check-session])}])


(def root-route
  {:controllers root-controllers})
