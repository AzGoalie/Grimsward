(ns app.core
  (:require [reagent.core :as r]
            [reagent.dom :as rdom]
            [re-frame.core :as rf]
            [app.db]
            [app.theme :refer [grimsward-theme]]
            ;; -- nav --
            [app.nav.views :refer [nav]]
            [app.nav.events]
            [app.nav.subs]
            ;; -- npm deps --
            ["@material-ui/core" :as mui]
            ["@material-ui/core/styles" :refer [ThemeProvider]]))

(defn app
  []
  [:> ThemeProvider {:theme grimsward-theme}
   [:> mui/CssBaseline]
   [nav]])

(defn start
  []
  (rf/dispatch-sync [:initialize-db])
  (rdom/render [app]
               (.getElementById js/document "app")))

(start)
