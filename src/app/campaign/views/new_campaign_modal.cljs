(ns app.campaign.views.new-campaign-modal
  (:require [reagent.core :as r]
            [re-frame.core :as rf]
            [app.campaign.events :as events]
            [app.components.form-group :refer [form-group r-input]]
            [app.components.modal :refer [modal]]
            ["@chakra-ui/react" :refer [Button Flex FormLabel Input InputGroup InputRightElement Stack Tag TagLabel TagCloseButton Textarea]]))

(defn player-tag [email on-remove]
  [:> Tag {:key           email
           :border-radius "full"
           :variant       "solid"
           :m             1}
   [:> TagLabel email]
   [:> TagCloseButton {:on-click #(on-remove email)}]])

(defn player-input [{:keys [players on-remove-player on-add-player]}]
  (let [email-input (r/atom "")
        on-change   #(reset! email-input (.. % -target -value))
        on-submit   #(when-not (empty? @email-input)
                       (on-add-player @email-input)
                       (reset! email-input ""))]
    (fn [{:keys [players on-remove-player on-add-player]}]
      [:<>
       [:> FormLabel "Players:"]
       [:> Flex {:justify "flex-start"
                 :wrap    "wrap"}
        (for [email players]
          ^{:key email}
          [player-tag email on-remove-player])]
       [:> InputGroup {:size "md"}
        [:> Input {:type        "email"
                   :as          r-input
                   :value       @email-input
                   :placeholder "Email for player invite"
                   :on-change   on-change}]
        [:> InputRightElement {:width "6.5rem"}
         [:> Button {:on-click on-submit
                     :type     "submit"
                     :size     "sm"
                     :height   "1.75rem"}
          "Add player"]]]])))

(defn campaign-form [values]
  [:form {:on-submit #(.preventDefault %)}
   [:> Stack {:spacing 4}
    [form-group {:label  "Title"
                 :id     :title
                 :type   "text"
                 :values values}]
    [form-group {:label   "Description"
                 :id      :description
                 :element Textarea
                 :values  values}]
    [player-input {:players          (:players @values)
                   :on-add-player    #(swap! values update :players conj %)
                   :on-remove-player #(swap! values update :players disj %)}]]])

(defn new-campaign-modal
  []
  (let [open?          (r/atom false)
        initial-values {:title       ""
                        :description ""
                        :players     #{}}
        values         (r/atom initial-values)]
    (fn []
      [:<>
       [:> Button {:type         "submit"
                   :color-scheme "blue"
                   :size         "lg"
                   :font-size    "md"
                   :on-click     #(reset! open? true)}
        "Create a new campaign"]
       [modal {:open     @open?
               :on-close #(reset! open? false)
               :header   "Create a new campaign"
               :body     [campaign-form values]
               :footer   [:<>
                          [:> Button {:color-scheme "blue"
                                      :mr           4
                                      :on-click     (fn [_]
                                                      (rf/dispatch [::events/create-campaign @values])
                                                      (reset! open? false))}
                           "Create"]
                          [:> Button {:on-click #(reset! open? false)}
                           "Cancel"]]}]])))