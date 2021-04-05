(ns app.components.error-message
  (:require ["@material-ui/core/Typography" :default Typography]))

(defn error-message
  [error]
  (when-let [error-message (:message error)]
    [:> Typography {:variant "caption"
                    :color   "error"}
     error-message]))
