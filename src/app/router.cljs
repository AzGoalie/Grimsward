(ns app.router
  (:require [reitit.frontend :as reitit]
            [reitit.frontend.easy :as rfe]
            [reitit.frontend.controllers :as rfc]
            [reitit.coercion.malli :as malli]
            [re-frame.core :as rf]
            [app.components.page-nav :refer [page-nav]]
            [app.auth.views.log-in :refer [log-in]]
            [app.auth.views.sign-up :refer [sign-up]]
            [app.auth.views.profile :refer [profile]]
            [app.campaign.views.campaigns :refer [campaigns]]))

(rf/reg-event-db
 :navigated
 (fn [db [_ new-match]]
   (let [old-match (:current-route db)
         controllers (rfc/apply-controllers (:controllers old-match) new-match)]
     (assoc db :current-route (assoc new-match :controllers controllers)))))

(defn on-navigate [new-match]
  (when new-match
    (rf/dispatch [:navigated new-match])))

(rf/reg-event-fx
 :navigate
 (fn [_ [_ & route]]
   {:navigate! route}))

(rf/reg-fx
 :navigate!
 (fn [route]
   (apply rfe/push-state route)))

(rf/reg-sub
 :current-route
 (fn [db]
   (:current-route db)))

(def routes
  ["/"
   [""
    {:name ::frontpage
     :view #(page-nav {:center "Frontpage"})}]
   ["log-in"
    {:name ::log-in
     :view #'log-in}]
   ["sign-up"
    {:name ::sign-up
     :view #'sign-up}]
   ["profile"
    {:name ::profile
     :view #'profile}]
   ["campaigns"
    {:name ::campaigns
     :view #'campaigns}]])

(def router
  (reitit/router
   routes
   {:data {:coercion malli/coercion}}))

(defn init-routes! []
  (js/console.log "initializing routes")
  (rfe/start!
   router
   on-navigate
   {:use-fragment false}))
