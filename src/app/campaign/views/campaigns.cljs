(ns app.campaign.views.campaigns
  (:require [re-frame.core :as rf]
            [app.campaign.views.campaign-card :refer [campaign-card]]
            ["@chakra-ui/react" :refer [Button Center Flex Heading Stack]]))

(defn campaigns
  []
  (let [campaigns (rf/subscribe [:campaigns])]
    [:> Flex {:align   "center"
              :justify "center"}
     [:> Stack {:spacing 8}
      [:> Heading {:align "center"}
       "Campaigns"]
      (for [campaign @campaigns]
        ^{:key (:id campaign)}
        [campaign-card campaign])
      [:> Button {:full-width true}
       "Create a new campaign"]]]))
