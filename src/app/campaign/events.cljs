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
                                         :val   (get-in db [:auth :uid])}}}))

(rf/reg-event-fx
 ::fetch-joined-campaigns
 (fn [{:keys [db]} _]
   {::firestore/query {:collection-name "campaigns"
                       :db-path         :campaigns/joined
                       :where           {:field "players"
                                         :op    "array-contains"
                                         :val   (get-in db [:auth :uid])}}}))

(rf/reg-event-fx
 ::create-campaign
 (fn [_ [_ {:keys [title description players]}]]
   (println title description players)))