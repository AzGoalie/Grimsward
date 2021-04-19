(ns app.db
  (:require [re-frame.core :as rf]))

(def initial-app-db {:auth          {:uid          nil
                                     :email        nil
                                     :initialized? false}
                     :errors        {}
                     :loading       {}
                     :current-route nil})

(rf/reg-event-db
 :initialize-db
 (fn [_ _]
   initial-app-db))

(rf/reg-sub
 :loading
 (fn [db _]
   (:loading db)))

(rf/reg-sub
 :errors
 (fn [db _]
   (:errors db)))