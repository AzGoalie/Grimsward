(ns app.auth.events
  (:require [re-frame.core :refer [reg-event-db reg-event-fx]]))

(reg-event-fx
 :log-in
 (fn [{:keys [db]} [_ {:keys [user]}]]
   {:db (-> db
            (assoc :auth user)
            (update-in [:errors] dissoc :log-in))
    :dispatch [:set-active-nav :campaigns]}))

(reg-event-db
 :log-in-failure
 (fn [db [_ error-message]]
   (assoc-in db [:errors :log-in] error-message)))

(reg-event-db
 :log-out
 (fn [db [_]]
   (dissoc db :auth)))
