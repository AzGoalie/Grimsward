(ns app.auth.views.sign-up
  (:require [reagent.core :as r]
            [re-frame.core :as rf]
            [app.components.page-nav :refer [page-nav]]
            [app.components.form-group :refer [form-group]]
            [app.firebase.auth :refer [create-user-with-email-and-password]]
            ["@material-ui/core" :as mui]))

(defn validate-passwords
  [{:keys [password confirm-password]}]
  (= password confirm-password))

(defn on-create-account
  [sign-up-form]
  (if (validate-passwords sign-up-form)
    (create-user-with-email-and-password sign-up-form)
    (rf/dispatch [:sign-up-failure "Passwords do not match"])))

(defn sign-up
  []
  (let [initial-values {:email "" :password "" :confirm-password ""}
        values (r/atom initial-values)]
    (fn []
      (let [error @(rf/subscribe [:sign-up-failure])]
        [:<>
         [page-nav {:center "Sign Up"}]
         [:form
          [:> mui/Container {:maxWidth "xs"}
           (when error
             [:> mui/Typography {:variant "caption"
                                 :color   "error"}
              (:message error)])
           [form-group {:label  "Email Address"
                        :id     :email
                        :type   "email"
                        :error  (or (= :invalid-email (:code error))
                                    (= :email-in-use (:code error)))
                        :values values}]
           [form-group {:label  "Password"
                        :type   "password"
                        :id     :password
                        :error  (and (= :weak-password (:code error))
                                     (< (count (:password @values)) 6))
                        :values values}]
           [form-group {:label  "Confirm Password"
                        :type   "password"
                        :id     :confirm-password
                        :error  (not (validate-passwords @values))
                        :values values}]
           [:> mui/Button {:variant   "contained"
                           :color     "primary"
                           :size      "large"
                           :type      "submit"
                           :fullWidth true
                           :on-click  #(on-create-account @values)
                           :style     {:marginTop    16
                                       :marginBottom 16}}
            "Create Account"]
           [:> mui/Link {:href     "#sign-up"
                         :variant  "body2"
                         :color    "inherit"
                         :on-click #(rf/dispatch [:set-active-nav :log-in])}
            "Already have an account? Log In!"]]]]))))
