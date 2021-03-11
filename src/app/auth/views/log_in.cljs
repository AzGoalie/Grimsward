(ns app.auth.views.log-in
  (:require [reagent.core :as r]
            [re-frame.core :as rf]
            [app.components.page-nav :refer [page-nav]]
            [app.components.form-group :refer [form-group]]
            [app.firebase.auth :refer [sign-in-with-email-and-password]]
            ["@material-ui/core" :as mui]))

(defn log-in
  []
  (let [initial-values {:email "" :password ""}
        values (r/atom initial-values)]
    (fn []
      (let [error @(rf/subscribe [:log-in-failure])]
        [:<>
         [page-nav {:center "Log In"}]
         [:form
          [:> mui/Container {:maxWidth "xs"}
           (when error
             [:> mui/Typography {:variant "caption"
                                 :color   "error"}
              (:message error)])
           [form-group {:label  "Email Address"
                        :id     :email
                        :type   "email"
                        :error  (boolean error)
                        :values values}]
           [form-group {:label  "Password"
                        :type   "password"
                        :id     :password
                        :error  (boolean error)
                        :values values}]
           [:> mui/Button {:variant   "contained"
                           :color     "primary"
                           :size      "large"
                           :type      "submit"
                           :fullWidth true
                           :on-click  #(sign-in-with-email-and-password @values)
                           :style     {:marginTop    16
                                       :marginBottom 16}}
            "Log In"]
           [:> mui/Link {:href     "#sign-up"
                         :variant  "body2"
                         :color    "inherit"
                         :on-click #(rf/dispatch [:set-active-nav :sign-up])}
            "Don't have an account? Sign Up!"]]]]))))
