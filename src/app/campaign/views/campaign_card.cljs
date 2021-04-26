(ns app.campaign.views.campaign-card
  (:require ["@chakra-ui/react" :refer [Avatar Box Flex Heading Spacer Text Tooltip]]))

(defn player-icons
  [players]
  [:> Box {:display {:base "none"
                     :md   "block"}}
   (for [player players]
     ^{:key player}
     [:> Tooltip {:label      player
                  :aria-label "Player Name"}
      [:> Avatar {:size "xs"
                  :mx   1}]])])

(defn campaign-card
  [{:keys [title description owner players next-session]}]
  [:> Box {:as            "a"
           :bg            "gray.700"
           :href          "#"
           :overflow      "hidden"
           :p             4
           :box-shadow    "md"
           :border-radius "md"}
   [:> Flex
    [:> Heading {:size "md"}
     title]
    [:> Spacer]
    [player-icons (conj players owner)]]
   [:> Text
    description]
   [:> Text {:font-size "sm"
             :color     "gray.400"}
    (str "Next Session: " (if next-session
                            (.toDate ^js next-session)
                            "Not Scheduled"))]])