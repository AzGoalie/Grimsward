(ns app.components.form-container
  (:require [app.components.form-group :refer [form-group]]
            ["@chakra-ui/react" :refer [Box Flex Heading Stack]]))

(defn form-container
  [heading on-submit form]
  [:> Flex {:align   "center"
            :justify "center"}
   [:> Stack {:spacing 8}
    [:> Heading {:font-size "4xl"
                 :align     "center"}
     heading]
    [:> Box {:rounded    "lg"
             :bg         "gray.700"
             :maxW       "xs"
             :box-shadow "lg"
             :p          8}
     [:form {:on-submit (fn [e] (.preventDefault e) (on-submit))}
      form]]]])
