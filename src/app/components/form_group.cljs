(ns app.components.form-group
  (:require ["@chakra-ui/react" :refer [FormControl FormErrorMessage FormLabel Input]]))

(defn form-group
  [{:keys [id label type values placeholder error]}]
  [:> FormControl {:id         id
                   :is-invalid error}
   [:> FormLabel label]
   [:> Input {:id          id
              :type        type
              :value       (id @values)
              :on-change   #(swap! values assoc id (.. % -target -value))
              :placeholder placeholder}]
   [:> FormErrorMessage error]])
