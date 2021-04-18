(ns app.auth.subs
  (:require [re-frame.core :refer [reg-sub]]))

(reg-sub
 :errors/log-in
 :<- [:errors]
 (fn [errors]
   (:log-in errors)))

(reg-sub
 :errors/sign-up
 :<- [:errors]
 (fn [errors]
   (:sign-up errors)))

(reg-sub
 :errors/update-profile
 :<- [:errors]
 (fn [errors]
   (:update-profile errors)))

(reg-sub
 :loading/log-in
 :<- [:loading]
 (fn [loading]
   (:log-in loading)))

(reg-sub
 :loading/sign-up
 :<- [:loading]
 (fn [loading]
   (:sign-up loading)))

(reg-sub
 :loading/update-profile
 :<- [:loading]
 (fn [loading]
   (:update-profile loading)))