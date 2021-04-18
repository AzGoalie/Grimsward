(ns app.theme
  (:require ["@chakra-ui/react" :refer [extendTheme]]))

(def grimsward-theme
  (extendTheme
   (clj->js {:config {:initialColorMode "dark"}})))