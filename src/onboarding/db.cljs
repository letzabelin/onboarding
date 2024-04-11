(ns onboarding.db
  (:require
    [re-frame.core :as rf]))


(def default-db
  {:session nil
   :current-route nil
   :current-route-db nil})


(defn add-to-local-storage
  [key value]
  (.setItem js/localStorage (str key) (str value)))


(defn remove-from-local-storage
  [key]
  (.removeItem js/localStorage (str key)))


(defn get-from-local-storage
  [key]
  (.getItem js/localStorage (str key)))


(rf/reg-cofx
  ::session-local-storage
  (fn [cofx _]
    (assoc cofx :session-local-storage (get-from-local-storage "session"))))


(rf/reg-sub
  ::session
  :-> :session)
