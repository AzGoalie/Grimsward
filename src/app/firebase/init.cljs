(ns app.firebase.init
  (:require ["firebase/app" :default firebase]
            ["firebase/auth"]))

(defn firebase-init
  []
  (.initializeApp firebase #js {:apiKey "API_KEY"
                                :authDomain "PROJECT_ID.firebase.com"
                                :projectId "PROJECT_ID"
                                :storageBucket "PROJECT_ID.appspot.com"
                                :messagingSenderId "SENDER_ID"
                                :appId "APP_ID"}))
