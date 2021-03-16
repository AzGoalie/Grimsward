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
    ;; -- npm deps --
            ["@material-ui/core" :as mui]
            ["@material-ui/core/styles" :refer [ThemeProvider]]))

(defn app
  []
  (when-let [current-route @(rf/subscribe [:current-route])]
    [:> ThemeProvider {:theme grimsward-theme}
     [:> mui/CssBaseline]
     [nav (-> current-route :data :name)]
     [:> mui/Container
      [(-> current-route :data :view)]]]))

(defn ^:dev/after-load start
  []
  (rdom/render [app]
               (.getElementById js/document "app")))

(defn ^:export init
  []
  (rf/dispatch-sync [:initialize-db])
  (router/init-routes!)
  (firebase-init)
  (start))
