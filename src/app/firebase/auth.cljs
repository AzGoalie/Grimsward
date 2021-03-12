(ns app.firebase.auth
  (:require [re-frame.core :as rf]
            ["firebase/app" :default firebase]))

(def error-codes
  {"auth/wrong-password"       {:code    :wrong-password
                                :message "Invalid email/password"}
   "auth/invalid-email"        {:code    :invalid-email
                                :message "Invalid email"}
   "auth/user-not-found"       {:code    :user-not-found
                                :message "Account not found"}
   "auth/user-disabled"        {:code    :user-disabled
                                :message "Account disabled"}
   "auth/email-already-in-use" {:code    :email-in-use
                                :message "An account already exists with this email address"}
   "auth/weak-password"        {:code    :weak-password
                                :message "Password is too weak"}})

(rf/reg-fx
  :firebase/sign-in-with-email-and-password
  (fn
    [{:keys [email password]}]
    (-> (.auth firebase)
        (.signInWithEmailAndPassword email password)
        (.catch #(rf/dispatch [:log-in-failure (get error-codes (.-code %))])))))

(rf/reg-fx
  :firebase/create-user-with-email-and-password
  (fn
    [{:keys [email password]}]
    (-> (.auth firebase)
        (.createUserWithEmailAndPassword email password)
        (.catch #(rf/dispatch [:sign-up-failure (get error-codes (.-code %))])))))

(rf/reg-fx
  :firebase/sign-out
  (fn
    []
    (-> (.auth firebase)
        (.signOut))))

(defn parse-user
  [user]
  (js->clj
    (-> user js/JSON.stringify js/JSON.parse)
    :keywordize-keys true))

(defn on-auth-state-changed
  []
  (-> (.auth firebase)
      (.onAuthStateChanged
        (fn [user]
          (if user
            (rf/dispatch [:set-current-user {:user (parse-user user)}])
            (rf/dispatch [:remove-current-user])))
        (fn [error]
          (if error
            (rf/dispatch [:log-in-failure (get error-codes (.-code error))]))))))
