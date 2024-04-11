(ns onboarding.routes.root.events
  (:require
    [onboarding.routes.login.route :as login-route]
    [onboarding.routing.events :as routing-events]
    [re-frame.core :as re-frame]))


(re-frame/reg-event-fx
  ::check-session
  (fn [{:keys [db]} _]
    {:fx [(when (nil? (:session db))
            [:dispatch [::routing-events/navigate ::login-route/login]])]}))
