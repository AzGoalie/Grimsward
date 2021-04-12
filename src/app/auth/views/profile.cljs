(ns app.auth.views.profile
  (:require [reagent.core :as r]
            [re-frame.core :as rf]
            [app.components.form-container :refer [form-container]]
            [app.components.form-group :refer [form-group]]
            [app.auth.utils :refer [invalid-email? invalid-password? passwords-mismatch? dispatch-error]]
            ["@chakra-ui/react" :refer [Button Link Stack]]))

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
      [form-container
       "Profile"
       #(on-update-user email @values)
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
                    :font-size    "md"}
         "Update Account"]]])))

(defn profile []
  (if-let [user @(rf/subscribe [:user])]
    [profile-form (:email user)]))
