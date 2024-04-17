(ns onboarding.routes.login.events
  (:require
    [ajax.core :as ajax]
    [day8.re-frame.http-fx]
    [onboarding.db :refer [add-to-local-storage]]
    [onboarding.routes.login.db :refer [login-route-db]]
    [onboarding.routing.events :as routing-events]
    [re-frame.core :as rf]))


(def add-session-to-local-storage
  (rf/after (fn [{:keys [session]}]
              (add-to-local-storage "session" session))))


(rf/reg-event-fx
  ::log-in
  (fn [{:keys [db]} [_ {:keys [email password]}]]
    {:db (assoc-in db [:current-route-db :form-status] "loading")
     :fx [[:http-xhrio {:method :post
                        :uri "https://aidbox.zabelin.hz.aidbox.dev/auth/token"
                        :format (ajax/json-request-format)
                        :response-format (ajax/json-response-format {:keywords? true})
                        :params {:client_id "password-client"
                                 :grant_type "password"
                                 :username email
                                 :password password}
                        :on-success [::success-login]
                        :on-failure [::failure-login]}]]}))


(rf/reg-event-fx
  ::success-login
  [add-session-to-local-storage]
  (fn [{:keys [db]} [_ data]]
    {:db (-> db
             (assoc-in [:current-route-db :form-status] "success")
             (assoc-in [:current-route-db :form-errors] nil)
             (assoc :session (:access_token data)))
     :fx [[:dispatch [::routing-events/navigate :home]]]}))


(rf/reg-event-db
  ::failure-login
  (fn [db [_ error]]
    (-> db
        (assoc-in [:current-route-db :form-status] "failure")
        (assoc-in [:current-route-db :form-errors] (-> error :response :error_description)))))


(rf/reg-event-db
  ::index
  (fn [db _]
    (assoc db :current-route-db login-route-db)))
