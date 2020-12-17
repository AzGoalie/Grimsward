(ns app.core
  (:require [reagent.core :as r]
            [reagent.dom :as rdom]
            [app.theme :refer [grimsward-theme]]
            ["@material-ui/core" :as mui]
            ["@material-ui/core/styles" :refer [ThemeProvider]]))

(defn app
  []
  [:> ThemeProvider {:theme grimsward-theme}
   [:> mui/CssBaseline]
   [:> mui/Typography "Grimsward"]])

(defn start
  []
  (rdom/render [app]
               (.getElementById js/document "app")))

(start)
