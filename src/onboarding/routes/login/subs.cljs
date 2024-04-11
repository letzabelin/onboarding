(ns onboarding.routes.login.subs
  (:require
    [re-frame.core :as rf]))


(rf/reg-sub
  ::form-status
  :-> (comp :form-status :current-route-db))


(rf/reg-sub
  ::form-errors
  :-> (comp :form-errors :current-route-db))
