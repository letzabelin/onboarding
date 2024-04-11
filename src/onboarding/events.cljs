(ns onboarding.events
  (:require
    [onboarding.db :as db]
    [re-frame.core :as re-frame]))


(def get-session-from-local-storage
  (re-frame/inject-cofx ::db/session-local-storage))


(re-frame/reg-event-fx
  ::initialize-db
  [get-session-from-local-storage]
  (fn [{:keys [session-local-storage]} _]
    {:db (assoc db/default-db :session session-local-storage)}))
