(ns app.theme
  (:require ["@material-ui/core/styles" :refer [createMuiTheme]]))

(def grimsward-theme
  (createMuiTheme
    (clj->js {:palette {:type "dark"}})))


