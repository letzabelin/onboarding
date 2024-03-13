(ns onboarding.views
  (:require
    [onboarding.db]
    [onboarding.handlers]
    [onboarding.pages.login.view :refer [login-page]]
    [onboarding.routes :as routes]
    [onboarding.subs]
    [re-frame.core :as rf]))


(defn home-page
  []
  (let [name (rf/subscribe [:name])]
    (fn []
      [:div (str "Hello from " @name ". This is the Home Page.")
       [:div [:a {:href (routes/url-for :about)} "go to About Page"]]
       [:div [:a {:href (routes/url-for :login)} "go to Login Page"]]])))


(defn about-page
  []
  (fn []
    [:div "This is the About Page."
     [:div 
      [:a {:href (routes/url-for :login)} "go to Login Page"]
      [:a {:href (routes/url-for :home)} "go to Home Page"]]]))


(defmulti pages identity)

(defmethod pages :home-page [] [home-page])
(defmethod pages :about-page [] [about-page])
(defmethod pages :login-page [] [login-page])
(defmethod pages :default [] [:div "This is default route"])


(defn current-page
  []
  (let [current-page (rf/subscribe [:current-page])]
    (fn []
      [pages @current-page])))
