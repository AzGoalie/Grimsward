(ns app.components.form-group
  (:require [reagent.core :as r]
            ["@chakra-ui/react" :refer [FormControl FormErrorMessage FormLabel Input Textarea]]))

(def r-input
  (r/reactify-component
   (fn [props]
     [:input props])))

(def r-textarea
  (r/reactify-component
   (fn [props]
     [:textarea props])))

(defn form-group
  [{:keys [id label type values error element]
    :or   {element Input}}]
  (let [input    (= element Input)
        textarea (= element Textarea)]
    [:> FormControl {:id         id
                     :is-invalid error}
     [:> FormLabel label]
     [:> element {:as        (cond
                               input r-input
                               textarea r-textarea)
                  :id        id
                  :type      type
                  :value     (id @values)
                  :on-change #(swap! values assoc id (.. % -target -value))}]
     [:> FormErrorMessage error]]))
