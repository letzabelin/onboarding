(ns onboarding.routing.db
  (:require
    [re-frame.core :as rf]
    [reitit.frontend.easy :as rfe]))


(rf/reg-fx
  ::navigate!
  (fn [k params query]
    (rfe/push-state k params query)))
