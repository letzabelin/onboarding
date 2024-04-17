(ns onboarding.events
  (:require
    [onboarding.db :as core-db]
    [re-frame.core :as rf]))


(def get-session-from-local-storage
  (rf/inject-cofx ::core-db/session-local-storage))


(rf/reg-event-db
  ::show-alert
  (fn [db [_ {:keys [severity text]}]]
    (assoc db :alert {:open? true
                      :severity severity
                      :text text})))


(rf/reg-event-db
  ::close-alert
  (fn [db _]
    (assoc-in db [:alert :open?] false)))


(rf/reg-event-fx
  ::initialize-db
  [get-session-from-local-storage]
  (fn [{:keys [session-local-storage]} _]
    {:db (assoc core-db/default-db :session session-local-storage)}))
