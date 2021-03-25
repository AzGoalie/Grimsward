(ns app.auth.events
  (:require [re-frame.core :refer [reg-event-db reg-event-fx dispatch]]))

(reg-event-fx
 :log-in
 (fn [_ [_ credentials]]
   {:firebase/sign-in-with-email-and-password
    (merge credentials {:on-success #(dispatch [:navigate :app.router/campaigns])})}))

(reg-event-fx
 :log-out
 (fn [_ _]
   {:firebase/sign-out nil
    :dispatch          [:navigate :app.router/frontpage]}))

(reg-event-fx
 :sign-up
 (fn [_ [_ credentials]]
   {:firebase/create-user-with-email-and-password
    (merge credentials {:on-success #(dispatch [:navigate :app.router/campaigns])})}))

(reg-event-fx
 :update-email
 (fn [_ [_ email]]
   {:firebase/update-user-email
    {:email      email
     :on-success #(dispatch [:clear-errors])}}))

(reg-event-fx
 :update-password
 (fn [_ [_ password]]
   {:firebase/update-user-password
    {:password   password
     :on-success #(dispatch [:clear-error])}}))

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
   (assoc-in db [:errors :sign-up] error)))

(reg-event-db
 :log-in-failure
 (fn [db [_ error]]
   (assoc-in db [:errors :log-in] error)))

(reg-event-db
 :update-user-failure
 (fn [db [_ error]]
   (assoc-in db [:errors :update-user] error)))

(reg-event-db
 :clear-errors
 (fn [db _]
   (dissoc db :errors)))
