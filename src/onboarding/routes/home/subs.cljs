(ns onboarding.routes.home.subs
  (:require
    [re-frame.core :as rf]))


(rf/reg-sub
  ::logout-status
  :-> (comp :logout-status :current-route-db))


(rf/reg-sub
  ::store
  (fn [{:keys [current-route-db]}]
    (let [store (:store current-route-db)]
      {:records (map (fn [id] (get (:entities (:data store)) id)) (:ids (:data store)))
       :status (:status store)
       :total-count (or (:total-count store) 0)
       :current-page (or (:current-page store) 0)
       :records-per-page (or (:records-per-page store) 0)})))
