(ns onboarding.routing.subs
  (:require
    [re-frame.core :as rf]))


(rf/reg-sub
  ::current-route
  :-> :current-route)
