(ns app.auth.events
  (:require [re-frame.core :refer [reg-event-db reg-event-fx dispatch]]))

(reg-event-fx
 :log-in
 (fn [{:keys [db]} [_ credentials]]
   {:db (assoc-in db [:loading :log-in] true)
    :firebase/sign-in-with-email-and-password
    (merge credentials {:on-success #(dispatch [:navigate :app.router/campaigns])})}))

(reg-event-fx
 :log-out
 (fn [_ _]
   {:firebase/sign-out nil
    :dispatch          [:navigate :app.router/frontpage]}))

(reg-event-fx
 :sign-up
 (fn [{:keys [db]} [_ credentials]]
   {:db (assoc-in db [:loading :sign-uo] true)
    :firebase/create-user-with-email-and-password
    (merge credentials {:on-success #(dispatch [:navigate :app.router/campaigns])})}))

(reg-event-fx
 :update-email
 (fn [{:keys [db]} [_ email]]
   {:db (assoc-in db [:loading :update-profile] true)
    :firebase/update-user-email
    {:email      email}
    :on-success #(dispatch [:clear-errors])}))

(reg-event-fx
 :update-password
 (fn [{:keys [db]} [_ password]]
   {:db (assoc-in db [:loading :update-profile] true)
    :firebase/update-user-password
    {:password   password}
    :on-success #(dispatch [:clear-error])}))

(reg-event-db
 :set-current-user
 (fn [db [_ user]]
   (-> db
       (assoc :auth user)
       (dissoc :errors))))

(reg-event-db
 :remove-current-user
 (fn [db _]
   (-> db
       (dissoc :auth)
       (dissoc :errors))))

(reg-event-db
 :sign-up-failure
 (fn [db [_ error]]
   (-> db
       (assoc-in [:loading :sign-up] false)
       (assoc-in [:errors :sign-up] error))))

(reg-event-db
 :log-in-failure
 (fn [db [_ error]]
   (-> db
       (assoc-in [:loading :log-in] false)
       (assoc-in [:errors :log-in] error))))

(reg-event-db
 :update-user-failure
 (fn [db [_ error]]
   (-> db
       (assoc-in [:loading :update-profile] false)
       (assoc-in [:errors :update-user] error))))

(reg-event-db
 :clear-errors
 (fn [db _]
   (-> db
       (dissoc db :errors)
       (dissoc db :loading))))