(ns app.core
  (:require [reagent.core :as r]
            [reagent.dom :as rdom]))

(defn app
  []
  [:div "Grimsward"])

(defn start
  []
  (rdom/render [app]
               (.getElementById js/document "app")))

(start)
