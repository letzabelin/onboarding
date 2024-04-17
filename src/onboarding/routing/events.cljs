(ns onboarding.routing.events
  (:require
    [onboarding.routing.db :as routing-db]
    [re-frame.core :as rf]
    [reitit.frontend.controllers :as rtfc]))


(rf/reg-event-fx
  ::navigate
  (fn [_ [_ route]]
    {::routing-db/navigate! route}))


(rf/reg-event-fx
  ::set-current-route
  (fn [{:keys [db]} [_ next-route]]
    (let [current-route (:current-route db)
          controllers (rtfc/apply-controllers (:controllers current-route) next-route)
          route (assoc next-route :controllers controllers)]
      {:db (assoc db :current-route route)})))
