(ns onboarding.components.textfields
  (:require
    ["@mui/icons-material" :as mui-icons]
    ["@mui/material" :as mui]
    [reagent.core :as r]))


(defn textfield
  [props]
  [:> mui/TextField
   {:label (:label props)
    :id (:label props)
    :fullWidth true}])


(defn password-textfield
  [props]
  (let [show-password? (r/atom false)]
    (fn []
      [:> mui/TextField
       {:label (:label props)
        :id (:label props)
        :type (if @show-password? "text" "password")
        :fullWidth true

        :InputProps
        {:endAdornment (r/as-element [:> mui/InputAdornment {:position "end"}
                                      [:> mui/IconButton {:onClick #(swap! show-password? not)
                                                          :onMouseDown #(.preventDefault %)
                                                          :onMouseUp #(.preventDefault %)}
                                       [:> (if @show-password?
                                             mui-icons/VisibilityOff
                                             mui-icons/Visibility)]]])}}])))
