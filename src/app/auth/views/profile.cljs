(ns app.auth.views.profile
  (:require [reagent.core :as r]
            [re-frame.core :as rf]
            [app.components.page-nav :refer [page-nav]]
            [app.components.form-group :refer [form-group]]
            [app.components.error-message :refer [error-message]]
            [app.auth.utils :refer [invalid-email? invalid-password? passwords-mismatch? dispatch-error]]
            ["@material-ui/core/Container" :default Container]
            ["@material-ui/core/Button" :default Button]))

(def update-user-error (partial dispatch-error :update-user-failure))

(defn update-password
  [update-user-form]
  (cond
    (invalid-password? update-user-form) (update-user-error
                                           :weak-password
                                           "Password must be at least 6 characters long")
    (passwords-mismatch? update-user-form) (update-user-error
                                             :mismatch
                                             "Passwords do not match")
    :else (rf/dispatch [:update-password (:password update-user-form)])))

(defn on-update-user
  [original-email update-user-form]
  (when (:password update-user-form)
    (update-password update-user-form))
  (when (not= original-email (:email update-user-form))
    (rf/dispatch [:update-email (:email update-user-form)])))

(defn profile-form [email]
  (let [initial-values {:email            email
                        :password         ""
                        :confirm-password ""}
        values (r/atom initial-values)
        error (rf/subscribe [:update-user-failure])]
    (fn []
      [:<>
       [page-nav {:center "Profile"}]
       [:form {:on-submit (fn [e] (.preventDefault e) (on-update-user email @values))}
        [:> Container {:maxWidth "xs"}
         [error-message @error]
         [form-group {:label  "Email Address"
                      :id     :email
                      :type   "email"
                      :error  (invalid-email? @error)
                      :values values}]
         [form-group {:label  "Password"
                      :id     :password
                      :type   "password"
                      :error  (invalid-password? @values)
                      :values values}]
         [form-group {:label  "Confirm Password"
                      :id     :confirm-password
                      :type   "password"
                      :error  (passwords-mismatch? @values)
                      :values values}]
         [:> Button {:variant   "contained"
                     :color     "primary"
                     :size      "large"
                     :type      "submit"
                     :fullWidth true
                     :style     {:marginTop    16
                                 :marginBottom 16}}
          "Update Account"]]]])))

(defn profile []
  (if-let [user @(rf/subscribe [:user])]
    [profile-form (:email user)]))
