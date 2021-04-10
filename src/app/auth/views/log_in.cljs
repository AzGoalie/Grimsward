(ns app.auth.views.log-in
  (:require [reagent.core :as r]
            [re-frame.core :as rf]
            [app.components.form-container :refer [form-container]]
            [app.components.form-group :refer [form-group]]
            ["@chakra-ui/react" :refer [Button Link Stack]]))

(defn log-in
  []
  (let [initial-values {:email "" :password ""}
        values (r/atom initial-values)
        error (rf/subscribe [:log-in-failure])]
    (fn []
      [form-container
       "Sign In"
       #(rf/dispatch [:log-in @values])
       [:> Stack {:spacing 4}
        [form-group
         {:label       "Email Address"
          :id          :email
          :type        "email"
          :error       (when @error "Invalid email/password")
          :placeholder "eaxmple@grimsward.com"
          :values      values}]
        [form-group
         {:label       "Password"
          :id          :password
          :type        "password"
          :placeholder "******"
          :error       (when @error "Invalid email/password")
          :values      values}]
        [:> Button {:type "submit"}
         "Sign In"]
        [:> Link {:font-size "sm"
                  :href      "/sign-up"}
         "Don't have an account? Sign Up!"]]])))
