(ns app.campaign.views.campaigns
  (:require [reagent.core :as r]
            [re-frame.core :as rf]
            [app.components.page-nav :refer [page-nav]]
            [app.campaign.views.campaign-card :refer [campaign-card]]
            ["@material-ui/core" :as mui]))

(defn campaigns
  []
  (let [campaigns (rf/subscribe [:campaigns])]
    [:<>
     [:> mui/Container {:max-width "md"}
      [page-nav {:center "Campaigns" :right (r/as-element
                                             [:> mui/Button {:variant "contained"
                                                             :color   "primary"}
                                              "New Campaign"])}]
      [:> mui/Grid {:container true
                    :spacing   2
                    :direction "column"}
       (for [campaign @campaigns]
         ^{:key (:id campaign)}
         [campaign-card campaign])]]]))
