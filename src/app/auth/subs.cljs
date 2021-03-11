(ns app.auth.subs
  (:require [re-frame.core :refer [reg-sub]]))

(reg-sub
  :logged-in?
  (fn [db _]
    (boolean (get-in db [:auth :uid]))))

(reg-sub
  :log-in-failure
  (fn [db _]
    (get-in db [:errors :log-in])))

(reg-sub
  :sign-up-failure
  (fn [db _]
    (get-in db [:errors :sign-up])))
