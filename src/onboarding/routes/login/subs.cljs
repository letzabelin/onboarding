(ns onboarding.routes.login.subs
  (:require
    [re-frame.core :as re-frame]))


(re-frame/reg-sub
  ::form-status
  (fn [db]
    (get-in db [:current-route-db :form-status])))


(re-frame/reg-sub
  ::form-errors
  (fn [db]
    (get-in db [:current-route-db :form-errors])))
