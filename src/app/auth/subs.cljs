(ns app.auth.subs
  (:require [re-frame.core :refer [reg-sub]]))

(reg-sub
 :logged-in?
 (fn [db _]
   (:auth db)))

(reg-sub
 :log-in-failure
 (fn [db _]
   (get-in db [:errors :log-in])))

(reg-sub
 :sign-up-failure
 (fn [db _]
   (get-in db [:errors :sign-up])))

(reg-sub
 :update-user-failure
 (fn [db _]
   (get-in db [:errors :update-user])))

(reg-sub
 :user
 (fn [db _]
   (get-in db [:auth :user])))