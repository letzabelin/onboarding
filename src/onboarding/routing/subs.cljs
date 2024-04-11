(ns onboarding.routing.subs
  (:require
    [re-frame.core :as re-frame]))


(re-frame/reg-sub
  ::current-route
  :-> :current-route)
