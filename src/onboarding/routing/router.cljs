(ns onboarding.routing.router
  (:require
    [onboarding.routes.home.route :refer [home-route]]
    [onboarding.routes.login.route :refer [login-route]]
    [onboarding.routes.root.route :refer [root-route]]
    [onboarding.routing.events :as routing-events]
    [onboarding.routing.subs :as routing-subs]
    [re-frame.core :as rf]
    [reitit.frontend :as rt]
    [reitit.frontend.easy :as rtfe]))


(defn- on-navigate
  [route]
  (when route
    (rf/dispatch [::routing-events/set-current-route route])))


(def ^:private routes
  [["/" root-route
    ["" home-route]]
   ["/login" login-route]])


(def router
  (rt/router routes))


(defn init-routes!
  []
  (rtfe/start!
    router
    on-navigate
    {:use-fragment true}))


(defn current-page
  []
  (let [current-route @(rf/subscribe [::routing-subs/current-route])]
    (when current-route
      [(-> current-route :data :view)])))
