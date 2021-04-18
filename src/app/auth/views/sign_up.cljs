(ns app.auth.views.sign-up
  (:require [reagent.core :as r]
            [re-frame.core :as rf]
            [app.components.form-container :refer [form-container]]
            [app.components.form-group :refer [form-group]]
            [app.auth.utils :refer [passwords-mismatch? invalid-password? invalid-email? dispatch-error]]
            ["@chakra-ui/react" :refer [Button Link Stack]]))

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
        values         (r/atom initial-values)
        error          (rf/subscribe [:errors/sign-up])
        loading?       (rf/subscribe [:loading/sign-up])]
    (fn []
      [form-container
       "Sign Up"
       #(on-create-account @values)
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
         "Create Account"]
        [:> Link {:font-size "sm"
                  :href      "/sign-in"}
         "Already have an account? Sign In!"]]])))
