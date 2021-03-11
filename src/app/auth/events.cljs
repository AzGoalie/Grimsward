(ns app.auth.events
  (:require [re-frame.core :refer [reg-event-db reg-event-fx]]))

(reg-event-fx
  :log-in
  (fn [{:keys [db]} [_ {:keys [user]}]]
    {:db       (-> db
                   (assoc :auth user)
                   (update-in [:errors] dissoc :log-in :sign-up))
     :dispatch [:set-active-nav :campaigns]}))

(reg-event-db
  :sign-up-failure
  (fn [db [_ error]]
    (assoc-in db [:errors :sign-up] error)))

(reg-event-db
  :log-in-failure
  (fn [db [_ error]]
    (assoc-in db [:errors :log-in] error)))

(reg-event-db
  :log-out
  (fn [db [_]]
    (dissoc db :auth)))
