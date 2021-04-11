(ns app.campaign.views.campaigns
  (:require [re-frame.core :as rf]
            [app.campaign.views.campaign-card :refer [campaign-card]]
            ["@chakra-ui/react" :refer [Button Center Container Heading Stack]]))

(defn campaigns
  []
  (let [campaigns (rf/subscribe [:campaigns])]
    [:> Container {:center-content true}
     [:> Stack {:spacing 8}
      [:> Heading {:align "center"}
       "Campaigns"]
      (for [campaign @campaigns]
        ^{:key (:id campaign)}
        [campaign-card campaign])
      [:> Button {:size "lg"
                  :is-full-width true}
       "Create a new campaign"]]]))
