(ns app.campaign.views.campaigns
  (:require [re-frame.core :as rf]
            [app.campaign.events :as events]
            [app.campaign.views.campaign-card :refer [campaign-card]]
            ["@chakra-ui/react" :refer [Button Container Heading Stack]]))

(defn campaigns
  []
  ; Probably not the best place to put this
  (rf/dispatch [::events/fetch-owned-campaigns])
  (rf/dispatch [::events/fetch-joined-campaigns])
  (fn []
    (let [campaigns @(rf/subscribe [:campaigns])]
      [:> Container {:maxW "4xl"}
       [:> Heading {:text-align  "center"
                    :size        "xl"
                    :font-weight "extrabold"}
        "Campaigns"]
       [:> Stack {:spacing 6
                  :my      6}
        (for [campaign campaigns]
          ^{:key (:id campaign)}
          [campaign-card campaign])
        [:> Button {:type         "submit"
                    :color-scheme "blue"
                    :size         "lg"
                    :font-size    "md"}
         "Create a new campaign"]]])))
