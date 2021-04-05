(ns app.components.form-group
  (:require ["@material-ui/core/TextField" :default TextField]))

(defn form-group
  [{:keys [id label type values error]}]
  [:> TextField {:id        id
                 :type      type
                 :label     label
                 :error     error
                 :value     (id @values)
                 :on-change #(swap! values assoc id (.. % -target -value))
                 :fullWidth true
                 :variant   "outlined"
                 :margin    "normal"}])
