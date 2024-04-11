(ns onboarding.routes.home.events
  (:require
    [onboarding.db :refer [remove-from-local-storage]]
    [onboarding.routing.events :as routing-events]
    [re-frame.core :as rf]))


(def remove-session-from-local-storage
  (rf/after (partial remove-from-local-storage "session")))


(rf/reg-event-fx
  ::log-out
  [remove-session-from-local-storage]
  (fn [{:keys [db]} _]
    {:db (assoc db :session nil)
     :fx [[:dispatch [::routing-events/navigate :login]]]}))
