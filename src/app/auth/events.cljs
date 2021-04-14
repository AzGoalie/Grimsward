(ns app.auth.events
  (:require [re-frame.core :refer [reg-event-db reg-event-fx dispatch]]))

(reg-event-fx
 :log-in
 (fn [{:keys [db]} [_ {:keys [email password]}]]
   {:db                                       (assoc-in db [:loading :log-in] true)
    :firebase/sign-in-with-email-and-password {:email      email
                                               :password   password
                                               :on-success #(dispatch [:navigate :app.router/campaigns])
                                               :on-failure #(dispatch [:log-in-failure %])}}))

(reg-event-fx
 :log-out
 (fn [_ _]
   {:firebase/sign-out nil
    :dispatch          [:navigate :app.router/frontpage]}))

(reg-event-fx
 :sign-up
 (fn [{:keys [db]} [_ {:keys [email password]}]]
   {:db                                           (assoc-in db [:loading :sign-up] true)
    :firebase/create-user-with-email-and-password {:email      email
                                                   :password   password
                                                   :on-success #(dispatch [:navigate :app.router/campaigns])
                                                   :on-failure #(dispatch [:sign-up-failure %])}}))

(reg-event-fx
 :update-email
 (fn [{:keys [db]} [_ email]]
   {:db         (assoc-in db [:loading :update-profile] true)
    :firebase/update-user-email
    {:email email}
    :on-success #(dispatch [:clear-errors])
    :on-failure #(dispatch [:update-user-failure %])}))

(reg-event-fx
 :update-password
 (fn [{:keys [db]} [_ password]]
   {:db         (assoc-in db [:loading :update-profile] true)
    :firebase/update-user-password
    {:password password}
    :on-success #(dispatch [:clear-error])
    :on-failure #(dispatch [:update-user-failure %])}))

(reg-event-db
 :set-current-user
 (fn [db [_ user]]
   (-> db
       (assoc :auth user)
       (dissoc :errors)
       (dissoc :loading))))

(reg-event-db
 :remove-current-user
 (fn [db _]
   (-> db
       (dissoc :auth)
       (dissoc :errors)
       (dissoc :loading))))

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