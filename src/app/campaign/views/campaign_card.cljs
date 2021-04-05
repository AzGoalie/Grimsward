(ns app.campaign.views.campaign-card
  (:require [reagent.core :as r]
            [cljs-time.format :refer [unparse formatters]]
            ["@material-ui/core" :as mui]
            ["@material-ui/icons" :as icons]))

(defn player-icons
  [players]
  [:<>
   (for [player players]
     ^{:key player}
     [:> mui/Tooltip {:title player}
      [:> icons/AccountCircle]])])

(defn campaign-card
  [{:keys [title description players next-session]}]
  [:> mui/Grid {:item true :xs 12}
   [:> mui/Card
    [:> mui/CardActionArea
     [:> mui/CardHeader {:title     title
                         :subheader (r/as-element [:<>
                                                   description
                                                   [:br]
                                                   (str "Next Session: " (unparse (formatters :date) next-session))])
                         :action    (r/as-element [player-icons players])}]]]])
