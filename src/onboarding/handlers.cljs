(ns onboarding.handlers
  (:require
    [onboarding.db :as db]
    [re-frame.core :as rf]))


(rf/reg-event-db
  :initialize-db
  (fn [_ _]
    db/default-db))


(rf/reg-event-db
  :set-current-page
  (fn [db [_ current-page]]
    (assoc db :current-page current-page)))
