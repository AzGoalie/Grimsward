(ns app.router
  (:require [reitit.frontend :as reitit]
            [reitit.frontend.easy :as rfe]
            [reitit.frontend.controllers :as rfc]
            [reitit.coercion.spec :as rss]
            [re-frame.core :as rf]
            [app.auth.views.log-in :refer [log-in]]
            [app.auth.views.sign-up :refer [sign-up]]
            [app.auth.views.profile :refer [profile]]
            [app.campaign.views.campaigns :refer [campaigns]]
            [app.campaign.views.campaign :refer [campaign]]
            ["@chakra-ui/react" :refer [Heading]]))

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

(defn frontpage
  []
  [:> Heading {:align "center"} "Frontpage"])

(def routes
  ["/"
   [""
    {:name ::frontpage
     :view #'frontpage}]
   ["sign-in"
    {:name ::log-in
     :view #'log-in}]
   ["sign-up"
    {:name ::sign-up
     :view #'sign-up}]
   ["profile"
    {:name ::profile
     :view #'profile}]
   ["campaigns"
    [""
    {:name ::campaigns
     :view #'campaigns}] 
    ["/:id"
     {:name ::campaign
      :view #'campaign
       :parameters {:path  {:id string?}}}]]])

(def router
  (reitit/router
   routes
   {:data {:coercion rss/coercion}}))

(defn init-routes! []
  (js/console.log "initializing routes")
  (rfe/start!
   router
   on-navigate
   {:use-fragment false}))
