(ns onboarding.routing.router
  (:require
    [onboarding.routes.home.route :refer [home-route]]
    [onboarding.routes.login.route :refer [login-route]]
    [onboarding.routes.root.route :refer [root-route]]
    [onboarding.routing.events :as routing-events]
    [onboarding.routing.subs :as routing-subs]
    [re-frame.core :as re-frame]
    [reitit.frontend :as reitit-frontend]
    [reitit.frontend.controllers :as reitit-frontend-controllers]
    [reitit.frontend.easy :as reitit-frontend-easy]))


(defn- on-navigate
  [next-route]
  (let [current-route @(re-frame/subscribe [::routing-subs/current-route])]
    (when next-route
      (let [controllers (reitit-frontend-controllers/apply-controllers (:controllers current-route) next-route)
            route  (assoc next-route :controllers controllers)]
        (re-frame/dispatch [::routing-events/set-current-route route])))))


(def ^:private routes
  [["/" root-route
    ["" home-route]]
   ["/login" login-route]])


(def router
  (reitit-frontend/router routes))


(defn init-routes!
  []
  (reitit-frontend-easy/start!
    router
    on-navigate
    {:use-fragment true}))


(defn current-page
  []
  (let [current-route @(re-frame/subscribe [::routing-subs/current-route])]
    (when current-route
      [(-> current-route :data :view)])))
