(ns onboarding.subs
  (:require
    [re-frame.core :as rf]))


(rf/reg-sub
  ::session
  :-> :session)
