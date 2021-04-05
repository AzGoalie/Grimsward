(ns app.campaign.views.campaign-card
  (:require [reagent.core :as r]
            [cljs-time.format :refer [unparse formatters]]
            ["@material-ui/core/Tooltip" :default Tooltip]
            ["@material-ui/core/Grid" :default Grid]
            ["@material-ui/core/Card" :default Card]
            ["@material-ui/core/CardActionArea" :default CardActionArea]
            ["@material-ui/core/CardHeader" :default CardHeader]
            ["@material-ui/icons/AccountCircle" :default AccountCircle]))

(defn player-icons
  [players]
  [:<>
   (for [player players]
     ^{:key player}
     [:> Tooltip {:title player}
      [:> AccountCircle]])])

(defn campaign-card
  [{:keys [title description players next-session]}]
  [:> Grid {:item true :xs 12}
   [:> Card
    [:> CardActionArea
     [:> CardHeader {:title     title
                     :subheader (r/as-element [:<>
                                               description
                                               [:br]
                                               (str "Next Session: " (unparse (formatters :date) next-session))])
                     :action    (r/as-element [player-icons players])}]]]])
