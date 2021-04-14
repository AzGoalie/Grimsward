(ns app.core
  (:require [reagent.dom :as rdom]
            [re-frame.core :as rf]
            [app.db]
            [app.theme :refer [grimsward-theme]]
            [app.router :as router]
    ;; -- firebase --
            [app.firebase.init :refer [firebase-init]]
    ;; -- auth --
            [app.auth.events]
            [app.auth.subs]
    ;; -- nav --
            [app.nav.views :refer [nav]]
            [app.nav.events]
            [app.nav.subs]
    ;; -- campaigns --
            [app.campaign.subs]
    ;; -- npm deps --
            ["@chakra-ui/react" :refer [ChakraProvider]]))

(rf/reg-sub
 :loading
 (fn [db _]
   (:loading db)))

(defn app
  []
  (when-let [current-route @(rf/subscribe [:current-route])]
    [:> ChakraProvider {:theme grimsward-theme}
     [nav (-> current-route :data :name)]
     [(-> current-route :data :view)]]))

(defn ^:dev/after-load start
  []
  (rf/clear-subscription-cache!)
  (rdom/render [#'app]
               (.getElementById js/document "app")))

(defn ^:export init
  []
  (rf/dispatch-sync [:initialize-db])
  (router/init-routes!)
  (firebase-init)
  (start))
