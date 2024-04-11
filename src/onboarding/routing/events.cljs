(ns onboarding.routing.events
  (:require
    [onboarding.routing.db :as db]
    [re-frame.core :as re-frame]))


(re-frame/reg-event-fx
  ::navigate
  (fn [cofx [event route]]
    (println [cofx event route])
    {::db/navigate! route}))


(re-frame/reg-event-db
  ::set-current-route
  (fn [db [_ current-route]]
    (assoc db :current-route current-route)))
