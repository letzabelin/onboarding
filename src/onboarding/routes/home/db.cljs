(ns onboarding.routes.home.db)


(def home-route-db
  {:logout-status "idle"

   :store
   {:status "idle"
    :data nil
    :total-count nil
    :records-per-page 10
    :current-page 0}})
