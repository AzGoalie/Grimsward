(ns app.campaign.subs
  (:require [re-frame.core :as rf]))

(rf/reg-sub
 :campaigns/owned
 (fn [db _]
   (vals (:campaigns/owned db))))

(rf/reg-sub
 :campaigns/joined
 (fn [db _]
   (vals (:campaigns/joined db))))

(rf/reg-sub
 :campaigns
 :<- [:campaigns/owned]
 :<- [:campaigns/joined]
 (fn [[owned joined]]
   (concat owned joined)))
