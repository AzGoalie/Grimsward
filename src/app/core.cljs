(ns app.core
  (:require [reagent.core :as r]
            [reagent.dom :as rdom]
            [re-frame.core :as rf]
            [app.db]
            [app.theme :refer [grimsward-theme]]
            [app.components.page-nav :refer [page-nav]]
            ;; -- auth --
            [app.auth.views.profile :refer [profile]]
            [app.auth.views.log-in :refer [log-in]]
            [app.auth.views.sign-up :refer [sign-up]]
            [app.auth.events]
            ;; -- campaign --
            [app.campaign.views.campaigns :refer [campaigns]]
            ;; -- nav --
            [app.nav.views :refer [nav]]
            [app.nav.events]
            [app.nav.subs]
            ;; -- npm deps --
            ["@material-ui/core" :as mui]
            ["@material-ui/core/styles" :refer [ThemeProvider]]))

(defn pages
  [page-name]
  (case page-name
    :profile [profile]
    :log-in [log-in]
    :sign-up [sign-up]
    :campaigns [campaigns]
    [page-nav {:center "Home Page"}]))

(defn app
  []
  (let [active-nav @(rf/subscribe [:active-nav])]
    [:> ThemeProvider {:theme grimsward-theme}
     [:> mui/CssBaseline]
     [nav]
     [:> mui/Container
      [pages active-nav]]]))

(defn ^:dev/after-load start
  []
  (rdom/render [app]
               (.getElementById js/document "app")))

(defn ^:export init
  []
  (rf/dispatch-sync [:initialize-db])
  (start))
