(ns onboarding.routing.events
  (:require
    [onboarding.routing.db :as routing-db]
    [re-frame.core :as rf]))


(rf/reg-event-fx
  ::navigate
  (fn [cofx [event route]]
    (println [cofx event route])
    {::routing-db/navigate! route}))


(rf/reg-event-db
  ::set-current-route
  (fn [db [_ current-route]]
    (assoc db :current-route current-route)))
