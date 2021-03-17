(ns app.firebase.init
  (:require ["firebase/app" :default firebase]
            ["firebase/auth"]
            [app.firebase.auth :refer [on-auth-state-changed]]))

(defn firebase-init
  []
  (.initializeApp firebase #js {:apiKey            "AIzaSyBBGwGdOfB5vxz1tCisL6dvMSFvB75NcqY"
                                :authDomain        "grimsward-dea5a.firebaseapp.com"
                                :projectId         "grimsward-dea5a"
                                :storageBucket     "grimsward-dea5a.appspot.com"
                                :messagingSenderId "59394048436"
                                :appId             "1:59394048436:web:5b2d842b72630df889b2e1"})
  (on-auth-state-changed))
