(ns app.components.form-group
  (:require ["@material-ui/core" :as mui]))

(defn form-group
  [{:keys [id label type values]}]
  [:> mui/TextField {:id id
                     :type type
                     :label label
                     :value (id @values)
                     :on-change #(swap! values assoc id (.. % -target -value))
                     :fullWidth true
                     :variant "outlined"
                     :margin "normal"}])
