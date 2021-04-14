(ns app.firebase.core
  (:require ["regenerator-runtime/runtime"]
            ["firebase/app" :refer [initializeApp]]))

(defonce firebase-app
  (initializeApp #js {:apiKey            "AIzaSyBBGwGdOfB5vxz1tCisL6dvMSFvB75NcqY"
                      :authDomain        "grimsward-dea5a.firebaseapp.com"
                      :projectId         "grimsward-dea5a"
                      :storageBucket     "grimsward-dea5a.appspot.com"
                      :messagingSenderId "59394048436"
                      :appId             "1:59394048436:web:5b2d842b72630df889b2e1"}))
