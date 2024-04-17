(ns onboarding.routes.home.events
  (:require
    [ajax.core :as ajax]
    [clojure.string :as str]
    [day8.re-frame.http-fx]
    [onboarding.db :refer [remove-from-local-storage]]
    [onboarding.routes.home.db :refer [home-route-db]]
    [onboarding.routing.events :as routing-events]
    [re-frame.core :as rf]))


(defn build-url
  [resource-type params]
  (let [query-params (->> params
                          (map (fn [[k v]] (str (name k) "=" v)))
                          (str/join "&"))]
    (if (empty? params)
      (str "https://aidbox.zabelin.hz.aidbox.dev/" resource-type)
      (str "https://aidbox.zabelin.hz.aidbox.dev/" resource-type "?" query-params))))


(defn normalize-records
  [records]
  (reduce
    (fn [acc record]
      (-> acc
          (update :ids conj (:id record))
          (assoc-in [:entities (:id record)] record)))
    {:ids []
     :entities {}}
    records))


(def remove-session-from-local-storage
  (rf/after (partial remove-from-local-storage "session")))


(rf/reg-event-fx
  ::log-out
  [remove-session-from-local-storage]
  (fn [{:keys [db]} _]
    {:db (assoc-in db [:current-route-db :logout-status] "loading")
     :fx [[:http-xhrio {:method :delete
                        :uri (build-url "Session" {})
                        :headers {"Authorization" (str "Bearer " (:session db))}
                        :format (ajax/json-request-format)
                        :response-format (ajax/json-response-format {:keywords? true})
                        :on-success [::success-logout]}]]}))


(rf/reg-event-fx
  ::success-logout
  (fn [{:keys [db]}]
    {:db (-> db
             (assoc :session nil)
             (assoc-in [:current-route-db :logout-status] "idle"))
     :fx [[:dispatch [::routing-events/navigate :login]]]}))


(rf/reg-event-fx
  ::fetch
  (fn [{:keys [db]} [_ data]]
    {:db (assoc-in db [:current-route-db :store :status] "loading")
     :fx [[:http-xhrio {:method :get
                        :uri (build-url "$query/patients" {:count (:records-per-page data)
                                                           :page (:current-page data)})
                        :format (ajax/json-request-format)
                        :response-format (ajax/json-response-format {:keywords? true})
                        :headers {"Authorization" (str "Bearer " (:session db))}
                        :on-success [::success-fetch]
                        :on-failure [::failure-fetch]}]]}))


(rf/reg-event-db
  ::success-fetch
  (fn [db [_ response]]
    (-> db
        (assoc-in [:current-route-db :store :status] "idle")
        (assoc-in [:current-route-db :store :total-count] (:total response))
        (assoc-in [:current-route-db :store :data] (normalize-records (:data response))))))


(rf/reg-event-db
  ::failure-fetch
  (fn [db]
    (assoc-in db [:current-route-db :store :status] "idle")))


(rf/reg-event-fx
  ::change-page
  (fn [{:keys [db]} [_ value]]
    {:db (assoc-in db [:current-route-db :store :current-page] value)
     :fx [[:dispatch [::fetch {:current-page value
                               :records-per-page (get-in db [:current-route-db :store :records-per-page])}]]]}))


(rf/reg-event-fx
  ::change-records-per-page
  (fn [{:keys [db]} [_ value]]
    {:db (-> db
             (assoc-in [:current-route-db :store :records-per-page] value)
             (assoc-in [:current-route-db :store :current-page] 0))
     :fx [[:dispatch [::fetch {:current-page 0
                               :records-per-page value}]]]}))


(rf/reg-event-fx
  ::reload-page
  (fn [{:keys [db]} _]
    {:fx [[:dispatch [::fetch (select-keys
                                (get-in db [:current-route-db :store])
                                [:current-page :records-per-page])]]]}))


(rf/reg-event-fx
  ::index
  (fn [{:keys [db]} _]
    {:db (assoc db :current-route-db home-route-db)
     :fx [[:dispatch [::fetch (select-keys (:store home-route-db) [:current-page :records-per-page])]]]}))
