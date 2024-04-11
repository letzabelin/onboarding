(ns onboarding.routing.db
  (:require
    [re-frame.core :as re-frame]
    [reitit.frontend.easy :as reitit-frontend-easy]))


(re-frame/reg-fx
  ::navigate!
  (fn [k params query]
    (reitit-frontend-easy/push-state k params query)))
