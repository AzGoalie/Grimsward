(ns app.auth.views.sign-up
  (:require [reagent.core :as r]
            [re-frame.core :as rf]
            [app.components.page-nav :refer [page-nav]]
            [app.components.form-group :refer [form-group]]
            [app.components.error-message :refer [error-message]]
            [app.auth.utils :refer [passwords-mismatch? invalid-password? dispatch-error]]
            ["@material-ui/core" :as mui]))

(def sign-up-error (partial dispatch-error :sign-up-failure))

(defn on-create-account
  [sign-up-form]
  (cond
    (invalid-password? sign-up-form) (sign-up-error
                                      :weak-password
                                      "Password must be at least 6 characters long")
    (passwords-mismatch? sign-up-form) (sign-up-error
                                        :mismatch
                                        "Passwords do not match")
    :else (rf/dispatch [:sign-up sign-up-form])))

(defn sign-up
  []
  (let [initial-values {:email            ""
                        :password         ""
                        :confirm-password ""}
        values (r/atom initial-values)
        error (rf/subscribe [:sign-up-failure])]
    (fn []
      [:<>
       [page-nav {:center "Sign Up"}]
       [:form {:on-submit (fn [e] (.preventDefault e) (on-create-account @values))}
        [:> mui/Container {:maxWidth "xs"}
         [error-message @error]
         [form-group {:label  "Email Address"
                      :id     :email
                      :type   "email"
                      :error  (or (= :invalid-email (:code error))
                                  (= :email-in-use (:code error)))
                      :values values}]
         [form-group {:label  "Password"
                      :type   "password"
                      :id     :password
                      :error  (invalid-password? @values)
                      :values values}]
         [form-group {:label  "Confirm Password"
                      :type   "password"
                      :id     :confirm-password
                      :error  (passwords-mismatch? @values)
                      :values values}]
         [:> mui/Button {:variant   "contained"
                         :color     "primary"
                         :size      "large"
                         :type      "submit"
                         :fullWidth true
                         :style     {:marginTop    16
                                     :marginBottom 16}}
          "Create Account"]
         [:> mui/Link {:href    "/log-in"
                       :variant "body2"
                       :color   "inherit"}
          "Already have an account? Log In!"]]]])))
