(ns app.components.page-nav
  (:require ["@material-ui/core" :as mui]
            ["@material-ui/core/Grid" :default Grid]
            ["@material-ui/core/IconButton" :default IconButton]
            ["@material-ui/core/Typography" :default Typography]
            ["@material-ui/icons/ChevronLeft" :default ChevronLeft]))

(defn page-nav
  [{:keys [left center right]}]
  [:> Grid {:container   true
            :align-items "center"
            :style       {:padding 16}}
   [:> Grid {:item      true
             :container true
             :justify   "flex-start"
             :sm        true}
    (when left
      [:> IconButton {:aria-label "Back"}
       [:> ChevronLeft]])]
   [:> Grid {:item true
             :sm   true}
    [:> Typography {:variant      "h2"
                    :align        "center"
                    :gutterBottom true}
     center]]
   [:> Grid {:item      true
             :container true
             :justify   "flex-end"
             :sm        true}
    (when right
      right)]])
