(ns app.auth.views.log-in
  (:require [reagent.core :as r]
            [re-frame.core :as rf]
            [app.components.page-nav :refer [page-nav]]
            ["@material-ui/core" :as mui]))

(defn log-in
  []
  (let [initial-values {:email "" :password ""}
        values (r/atom initial-values)]
    (fn []
      [:<>
       [page-nav {:center "Log In"}]
       [:form
        [:> mui/Container { :maxWidth "xs" }
         [:> mui/TextField {:label "Email Address"
                            :id :email
                            :type "email"
                            :variant "outlined"
                            :fullWidth true
                            :margin "normal"
                            :autoFocus true
                            :required true
                            :autoComplete "email"
                            :value (:email @values)
                            :on-change #(swap! values assoc :email (.. % -target -value))}]
         [:> mui/TextField {:label "Password"
                            :type "password"
                            :variant "outlined"
                            :margin "normal"
                            :fullWidth true
                            :required true
                            :autoComplete "current-password"
                            :id :password}]
         [:> mui/Button {:variant "contained"
                         :color "primary"
                         :type "submit"
                         :fullWidth true
                         :style {:marginTop 16
                                 :marginBottom 16}}
          "Log In"]
         [:> mui/Link {:href "#sign-up"
                       :variant "body2"
                       :color "inherit"
                       :on-click #(rf/dispatch [:set-active-nav :sign-up])}
          "Don't have an account? Sign Up!"]]]])))
