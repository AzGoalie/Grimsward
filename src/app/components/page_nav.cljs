(ns app.components.page-nav
  (:require ["@material-ui/core" :as mui]
            ["@material-ui/icons" :refer [ChevronLeft]]))

(defn page-nav
  [{:keys [left center right]}]
  [:> mui/Grid {:container true
                :justify   "space-between"
                :style     {:padding 16}}
   [:> mui/Grid {:item true
                 :xs   4}
    (when left
      [:> mui/IconButton {:aria-label "Back"}
       [:> ChevronLeft]])]
   [:> mui/Grid {:item true
                 :xs   4}
    [:> mui/Typography {:variant      "h2"
                        :align        "center"
                        :gutterBottom true}
     center]]
   [:> mui/Grid {:item true
                 :xs   4}
    (when right
      right)]])
