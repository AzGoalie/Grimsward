(ns app.auth.views.profile
  (:require [reagent.core :as r]
            [re-frame.core :as rf]
            [app.firebase.auth :as auth]
            [app.components.form-container :refer [form-container]]
            [app.components.form-group :refer [form-group]]
            [app.auth.utils :refer [invalid-email? invalid-password? passwords-mismatch? dispatch-error]]
            ["@chakra-ui/react" :refer [Button Stack]]))

(def update-profile-error (partial dispatch-error :update-profile-failure))

(defn update-password
  [update-profile-form]
  (cond
    (invalid-password? update-profile-form) (update-profile-error
                                             :weak-password
                                             "Password must be at least 6 characters long")
    (passwords-mismatch? update-profile-form) (update-profile-error
                                               :mismatch
                                               "Passwords do not match")
    :else (rf/dispatch [:update-password (:password update-profile-form)])))

(defn on-update-profile
  [original-email update-profile-form]
  (when (:password update-profile-form)
    (update-password update-profile-form))
  (when (not= original-email (:email update-profile-form))
    (rf/dispatch [:update-email (:email update-profile-form)])))

(defn profile-form [email]
  (let [initial-values {:email            email
                        :password         ""
                        :confirm-password ""}
        values         (r/atom initial-values)
        error          (rf/subscribe [:errors/update-profile])
        loading?       (rf/subscribe [:loading/update-profile])]
    (fn []
      [form-container
       "Profile"
       #(on-update-profile email @values)
       [:> Stack {:spacing 6}
        [form-group {:label  "Email Address"
                     :id     :email
                     :type   "email"
                     :error  (when (invalid-email? @error)
                               (:message @error))
                     :values values}]
        [form-group {:label  "Password"
                     :id     :password
                     :type   "password"
                     :error  (when (= :weak-password (:code @error))
                               (:message @error))
                     :values values}]
        [form-group {:label  "Confirm Password"
                     :id     :confirm-password
                     :type   "password"
                     :error  (when (= :mismatch (:code @error))
                               (:message @error))
                     :values values}]
        [:> Button {:type         "submit"
                    :color-scheme "blue"
                    :size         "lg"
                    :font-size    "md"
                    :is-loading   @loading?}
         "Update Account"]]])))

(defn profile []
  (when-let [email @(rf/subscribe [::auth/user-email])]
    [profile-form email]))
