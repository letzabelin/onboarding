(ns onboarding.components.textfield
  (:require
    ["@mui/icons-material" :as mui-icons]
    ["@mui/material" :as mui]
    [reagent.core :as reagent]))


(defn textfield
  []
  (let [show-password? (reagent/atom false)]
    (fn [props]
      [:> mui/TextField
       {:label (:label props)
        :id (:label props)
        :name (:name props)
        :fullWidth true
        :error (:error props)
        :type (if (= (:type props) "password")
                (if @show-password? "text" "password")
                "text")
        :helperText (:helper-text props)
        :required (:required props)
        :autoFocus (:auto-focus props)
        :InputProps
        (when (= (:type props) "password")
          {:endAdornment (reagent/as-element
                           [:> mui/InputAdornment {:position "end"}
                            [:> mui/IconButton {:onClick #(swap! show-password? not)
                                                :onMouseDown #(.preventDefault %)
                                                :onMouseUp #(.preventDefault %)}
                             [:> (if @show-password?
                                   mui-icons/VisibilityOff
                                   mui-icons/Visibility)]]])})}])))
