(ns app.auth.views.log-in
  (:require [reagent.core :as r]
            [re-frame.core :as rf]
            [app.components.page-nav :refer [page-nav]]
            [app.components.form-group :refer [form-group]]
            [app.components.error-message :refer [error-message]]
            ["@material-ui/core/Container" :default Container]
            ["@material-ui/core/Button" :default Button]
            ["@material-ui/core/Link" :default Link]))

(defn log-in
  []
  (let [initial-values {:email "" :password ""}
        values (r/atom initial-values)
        error (rf/subscribe [:log-in-failure])]
    (fn []
      [:<>
       [page-nav {:center "Log In"}]
       [:form {:on-submit (fn [e] (.preventDefault e) (rf/dispatch [:log-in @values]))}
        [:> Container {:maxWidth "xs"}
         [error-message @error]
         [form-group {:label  "Email Address"
                      :id     :email
                      :type   "email"
                      :error  (boolean @error)
                      :values values}]
         [form-group {:label  "Password"
                      :type   "password"
                      :id     :password
                      :error  (boolean @error)
                      :values values}]
         [:> Button {:variant   "contained"
                     :color     "primary"
                     :size      "large"
                     :type      "submit"
                     :fullWidth true
                     :style     {:marginTop    16
                                 :marginBottom 16}}
          "Log In"]
         [:> Link {:href    "/sign-up"
                   :variant "body2"
                   :color   "inherit"}
          "Don't have an account? Sign Up!"]]]])))
