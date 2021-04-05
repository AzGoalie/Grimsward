(ns app.campaign.views.campaigns
  (:require [reagent.core :as r]
            [re-frame.core :as rf]
            [app.components.page-nav :refer [page-nav]]
            [app.campaign.views.campaign-card :refer [campaign-card]]
            ["@material-ui/core/Container" :default Container]
            ["@material-ui/core/Button" :default Button]
            ["@material-ui/core/Grid" :default Grid]))

(defn campaigns
  []
  (let [campaigns (rf/subscribe [:campaigns])]
    [:<>
     [:> Container {:max-width "md"}
      [page-nav {:center "Campaigns" :right (r/as-element
                                              [:> Button {:variant "contained"
                                                          :color   "primary"}
                                               "New Campaign"])}]
      [:> Grid {:container true
                :spacing   2
                :direction "column"}
       (for [campaign @campaigns]
         ^{:key (:id campaign)}
         [campaign-card campaign])]]]))
