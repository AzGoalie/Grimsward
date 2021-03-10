(ns app.nav.views
  (:require [re-frame.core :as rf]
            ["@material-ui/core" :as mui]
            ["@material-ui/icons" :as icons]))

(defn nav-item
  [{:keys [id href dispatch name active-nav]}]
  (:> mui/Typography
      [:> mui/Link {:href href
                    :on-click dispatch
                    :color "inherit"
                    :style {:padding 10}
                    :underline (if (= id active-nav) "always" "hover")}
       name]))

(defn nav-public
  [{:keys [active-nav]}]
  [:<>
   [nav-item {:id :sign-up
              :name "Sign Up"
              :href "#sign-up"
              :active-nav active-nav
              :dispatch #(rf/dispatch [:set-active-nav :sign-up])}]
   [nav-item {:id :log-in
              :name "Log In"
              :href "#log-in"
              :active-nav active-nav
              :dispatch #(rf/dispatch [:set-active-nav :log-in])}]])

(defn nav-authenticated
  [{:keys [active-nav]}]
  [:<>
   [nav-item {:id :campaigns
              :name "Campaigns"
              :href "#campaigns"
              :active-nav active-nav
              :dispatch #(rf/dispatch [:set-active-nav :campaigns])}]
   [nav-item {:id :profile
              :name "Profile"
              :href "#profile"
              :active-nav active-nav
              :dispatch #(rf/dispatch [:set-active-nav :profile])}]])

(defn nav
  []
  (let [active-nav @(rf/subscribe [:active-nav])]
    [:> mui/AppBar {:position "static"}
     [:> mui/Toolbar
      [:> mui/IconButton {:edge "start" :color "inherit"}
       [:> icons/Menu]]
      [:> mui/Typography {:variant "h6" :style {:flexGrow 1}}
       "Grimsward"]
      (if-let [logged-in? @(rf/subscribe [:logged-in?])]
        [nav-authenticated {:active-nav active-nav}]
        [nav-public {:active-nav active-nav}])]]))
