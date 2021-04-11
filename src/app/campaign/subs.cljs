(ns app.campaign.subs
  (:require [re-frame.core :as rf]))

(rf/reg-sub
 :campaigns
 (fn [db _]
   (:campaigns db)))
