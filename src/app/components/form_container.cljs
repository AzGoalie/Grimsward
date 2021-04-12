(ns app.components.form-container
  (:require [app.components.form-group :refer [form-group]]
            ["@chakra-ui/react" :refer [Box Container Heading]]))

(defn form-container
  [heading on-submit form]
  [:> Container
   [:> Heading {:text-align  "center"
                :size        "xl"
                :font-weight "extrabold"}
    heading]
   [:> Box {:bg         "gray.700"
            :mx         "auto"
            :mt         6
            :p          8
            :maxW       "lg"
            :minW       "xs"
            :box-shadow "lg"
            :rounded    "lg"}
    [:form {:on-submit (fn [e] (.preventDefault e) (on-submit))}
     form]]])

