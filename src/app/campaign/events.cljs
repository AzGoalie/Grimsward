(ns app.campaign.events
  (:require [re-frame.core :as rf]
            [app.firebase.firestore :as firestore]))

(rf/reg-event-fx
 ::fetch-owned-campaigns
 (fn [{:keys [db]} _]
   {::firestore/query {:collection-name "campaigns"
                       :db-path         :campaigns/owned
                       :where           {:field "owner"
                                         :op    "=="
                                         :val   (get-in db [:auth :email])}}}))

(rf/reg-event-fx
 ::fetch-joined-campaigns
 (fn [{:keys [db]} _]
   {::firestore/query {:collection-name "campaigns"
                       :db-path         :campaigns/joined
                       :where           {:field "players"
                                         :op    "array-contains"
                                         :val   (get-in db [:auth :email])}}}))

(rf/reg-event-fx
 ::create-campaign
 (fn [{:keys [db]} [_ {:keys [title description players]}]]
   {::firestore/create {:collection-name "campaigns"
                        :document        {:title       title
                                          :description description
                                          :owner       (get-in db [:auth :email])
                                          :players     players}}}))