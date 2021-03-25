(ns app.components.error-message
  (:require ["@material-ui/core" :as mui]))

(defn error-message
  [error]
  (when-let [error-message (:message error)]
    [:> mui/Typography {:variant "caption"
                        :color   "error"}
     error-message]))
