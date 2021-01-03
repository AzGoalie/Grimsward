(ns app.campaign.views.campaigns
  (:require [app.components.page-nav :refer [page-nav]]))

(defn campaigns
  []
  [page-nav {:center "Campaigns"}])
