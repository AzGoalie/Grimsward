(ns app.components.form-group
  (:require [reagent.core :as r]
            ["@chakra-ui/react" :refer [FormControl FormErrorMessage FormLabel Input]]))

(def r-input
  (r/reactify-component
   (fn [props]
     [:input props])))

(defn form-group
  [{:keys [id label type values error]}]
  [:> FormControl {:id         id
                   :is-invalid error}
   [:> FormLabel label]
   [:> Input {:as        r-input
              :id        id
              :type      type
              :value     (id @values)
              :on-change #(swap! values assoc id (.. % -target -value))}]
   [:> FormErrorMessage error]])
