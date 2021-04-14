(ns app.firebase.init
  (:require [app.firebase.auth :refer [on-auth-state-changed]]))

(defn firebase-init
  []
  (on-auth-state-changed))
