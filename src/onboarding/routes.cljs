(ns onboarding.routes
  (:require
    [bidi.bidi :as bidi]
    [pushy.core :as pushy]
    [re-frame.core :as rf]))


;; first step is to define the routes we want.
(def routes
  ["/" {"" :home
        "about" :about
        "login" :login}])


;; parse-url function uses bidi/match-route to turn a URL into a ds
(defn- parse-url
  [url]
  (bidi/match-route routes url))


;; dispatch-route is called with that structure:
(defn- dispatch-route
  [matched-route]
  (let [page-name (keyword (str (name (:handler matched-route)) "-page"))]
    (rf/dispatch [:set-current-page page-name])))


;; The app-routes function that used to define functions is replaced by one that sets up pushy:
(defn app-routes
  []
  (pushy/start! (pushy/pushy dispatch-route parse-url)))


(def url-for (partial bidi/path-for routes))
