(ns app.auth.utils
  (:require [re-frame.core :as rf]))

(defn valid-password?
  [{:keys [password]}]
  (>= (count password) 6))

(def invalid-password? (complement valid-password?))

(defn passwords-match?
  [{:keys [password confirm-password]}]
  (= password confirm-password))

(def passwords-mismatch? (complement passwords-match?))

(defn invalid-email?
  [error]
  (or (= :invalid-email (:code error))
      (= :email-in-use (:code error))))

(defn dispatch-error
  [error-type error-code error-message]
  (rf/dispatch [error-type {:code    error-code
                            :message error-message}]))