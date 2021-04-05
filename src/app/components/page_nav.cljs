(ns app.components.page-nav
  (:require ["@material-ui/core" :as mui]
            ["@material-ui/icons" :refer [ChevronLeft]]))

(defn page-nav
  [{:keys [left center right]}]
  [:> mui/Grid {:container   true
                :align-items "center"
                :style       {:padding 16}}
   [:> mui/Grid {:item      true
                 :container true
                 :justify   "flex-start"
                 :sm        true}
    (when left
      [:> mui/IconButton {:aria-label "Back"}
       [:> ChevronLeft]])]
   [:> mui/Grid {:item true
                 :sm   true}
    [:> mui/Typography {:variant      "h2"
                        :align        "center"
                        :gutterBottom true}
     center]]
   [:> mui/Grid {:item      true
                 :container true
                 :justify   "flex-end"
                 :sm        true}
    (when right
      right)]])
