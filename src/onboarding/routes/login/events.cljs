(ns onboarding.routes.login.events
  (:require
    [ajax.core :as ajax]
    [day8.re-frame.http-fx]
    [onboarding.db :refer [add-to-local-storage]]
    [onboarding.routes.home.route :as home-route]
    [onboarding.routes.login.db :refer [login-page-db]]
    [onboarding.routing.events :as routing-events]
    [re-frame.core :as re-frame]))


(def add-session-to-local-storage
  (re-frame/after (fn [{:keys [session]}]
                    (add-to-local-storage "session" session))))


(re-frame/reg-event-fx
  ::login
  (fn [{:keys [db]} [_ {:keys [email password]}]]
    {:db (assoc-in db [:current-route-db :form-status] "loading")
     :http-xhrio {:method :post
                  :uri "https://aidbox.zabelin.hz.aidbox.dev/auth/token"
                  :format (ajax/json-request-format)
                  :response-format (ajax/json-response-format {:keywords? true})
                  :params {:client_id "password-client"
                           :grant_type "password"
                           :username email
                           :password password}
                  :on-success [::success-login]
                  :on-failure [::failure-login]}}))


(re-frame/reg-event-fx
  ::success-login
  [add-session-to-local-storage]
  (fn [{:keys [db]} [_ data]]
    {:db (-> db
             (assoc-in [:current-route-db :form-status] "success")
             (assoc-in [:current-route-db :form-errors] nil)
             (assoc :session (:access_token data)))
     :dispatch [::routing-events/navigate ::home-route/home]}))


(re-frame/reg-event-db
  ::failure-login
  (fn [db [_ error]]
    (-> db
        (assoc-in [:current-route-db :form-status] "failure")
        (assoc-in [:current-route-db :form-errors] (-> error :response :error_description)))))


(re-frame/reg-event-db
  ::index
  (fn [db _]
    (assoc db :current-route-db login-page-db)))
