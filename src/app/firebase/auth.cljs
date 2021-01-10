(ns app.firebase.auth
  (:require [re-frame.core :as rf]
            ["firebase/app" :default firebase]))

(defn parse-error-code
  [error-code]
  (case error-code
    "auth/wrong-password" "Invalid email/password"
    "auth/invalid-email" "Invalid email"
    "auth/user-not-found" "User not found"
    "auth/user-disabled" "Accound disabled"))

(defn sign-in-with-email-and-password
  [{:keys [email password]}]
  (-> (.auth firebase)
      (.signInWithEmailAndPassword email password)
      (.catch #(rf/dispatch [:log-in-failure {:error-message (parse-error-code (.-code %))}]))))

(defn sign-out
  []
  (-> (.auth firebase)
      (.signOut)))

(defn on-auth-state-changed
  []
  (-> (.auth firebase)
      (.onAuthStateChanged
       (fn [user]
         (if user
           (rf/dispatch [:log-in {:user (js->clj
                                         (-> user js/JSON.stringify js/JSON.parse)
                                         :keywordize-keys true)}])
           (rf/dispatch [:log-out])))
       (fn [error]
         (if error
           (rf/dispatch [:log-in-failure {:error-message (parse-error-code (.-code error))}]))))))
