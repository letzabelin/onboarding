(ns user
  (:require
    [ring.middleware.resource :refer [wrap-resource]]
    [shadow.cljs.devtools.api :as shadow]))


(defn cljs
  []
  (shadow/repl :app))
