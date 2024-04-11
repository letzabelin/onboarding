(ns onboarding.routes.root.events
  (:require
    [onboarding.routing.events :as routing-events]
    [re-frame.core :as rf]))


(rf/reg-event-fx
  ::check-session
  (fn [{:keys [db]} _]
    {:fx [(when (nil? (:session db))
            [:dispatch [::routing-events/navigate :login]])]}))
