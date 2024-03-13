(ns onboarding.subs
  (:require
    [re-frame.core :as rf])
  (:require-macros
    [reagent.ratom :refer [reaction]]))


(rf/reg-sub-raw
  :name
  (fn [db]
    (reaction (:name @db))))


(rf/reg-sub-raw
  :current-page
  (fn [db _]
    (reaction (:current-page @db))))
