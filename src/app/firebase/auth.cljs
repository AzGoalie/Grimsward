(ns app.firebase.auth
  (:require [re-frame.core :as rf]
            ["firebase/app" :default firebase]))

(defn parse-error-code
  [error]
  (case error
    "auth/wrong-password" {:code    :wrong-password
                           :message "Invalid email/password"}
    "auth/invalid-email" {:code    :invalid-email
                          :message "Invalid email"}
    "auth/user-not-found" {:code    :user-not-found
                           :message "Account not found"}
    "auth/user-disabled" {:code    :user-disabled
                          :message "Account disabled"}
    "auth/email-already-in-use" {:code    :email-in-use
                                 :message "An account already exists with this email address"}
    "auth/weak-password" {:code    :weak-password
                          :message "Password is too weak"}
    "auth/requires-recent-login" {:code    :reauthenticate
                                  :message "This action requires you to reauthenticate"}
    "auth/too-many-requests" {:code    :too-many-requests
                              :message "Too many requests, please wait before trying again."}))

(rf/reg-fx
 :firebase/sign-in-with-email-and-password
 (fn
   [{:keys [email password on-success on-failure]}]
   (-> (.auth firebase)
       (.signInWithEmailAndPassword email password)
       (.then on-success)
       (.catch #(on-failure (parse-error-code (.-code %)))))))

(rf/reg-fx
 :firebase/create-user-with-email-and-password
 (fn
   [{:keys [email password on-success on-failure]}]
   (-> (.auth firebase)
       (.createUserWithEmailAndPassword email password)
       (.then on-success)
       (.catch #(on-failure (parse-error-code (.-code %)))))))

(rf/reg-fx
 :firebase/update-user-email
 (fn
   [{:keys [email on-success on-failure]}]
   (-> (.auth firebase)
       (.-currentUser)
       (.updateEmail email)
       (.then on-success)
        ;; TODO Implement reauthentication when needed
       (.catch #(on-failure (parse-error-code (.-code %)))))))

(rf/reg-fx
 :firebase/update-user-password
 (fn
   [{:keys [password on-success on-failure]}]
   (-> (.auth firebase)
       (.-currentUser)
       (.updatePassword password)
       (.then on-success)
        ;; TODO Implement reauthentication when needed
       (.catch #(on-failure (parse-error-code (.-code %)))))))

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
         (when error
           (rf/dispatch [:log-in-failure (parse-error-code (.-code error))]))))))
