(ns app.nav.views
  (:require [reagent.core :as r]
            [re-frame.core :as rf]
            ["@material-ui/core" :as mui]
            ["@material-ui/icons" :as icons]))

(defn nav-item
  [{:keys [id href name current-route]}]
  (:> mui/Typography
    [:> mui/Link {:href      href
                  :color     "inherit"
                  :style     {:padding 10}
                  :underline (if (= id current-route) "always" "hover")}
     name]))

(defn nav-public
  [{:keys [current-route]}]
  [:<>
   [nav-item {:id            :app.router/sign-up
              :name          "Sign Up"
              :href          "/sign-up"
              :current-route current-route}]
   [nav-item {:id            :app.router/log-in
              :name          "Log In"
              :href          "/log-in"
              :current-route current-route}]])

(defn profile-menu
  [anchor-el handle-close]
  [:> mui/Popper {:open      (boolean anchor-el)
                  :anchor-el anchor-el}
   [:> mui/Paper
    [:> mui/ClickAwayListener {:on-click-away handle-close}
     [:> mui/MenuList
      [:> mui/MenuItem {:on-click handle-close}
       "Profile"]
      [:> mui/MenuItem {:on-click #(rf/dispatch [:log-out])}
       "Sign Out"]]]]])

(defn profile-button
  []
  (let [anchor-el (r/atom nil)
        handle-click #(reset! anchor-el (.-currentTarget %))
        handle-close #(reset! anchor-el nil)]
    (fn []
      [:<>
       [:> mui/IconButton {:on-click handle-click}
        [:> icons/AccountCircle]]
       [profile-menu @anchor-el handle-close]])))

(defn nav-authenticated
  [{:keys [current-route]}]
  [:<>
   [nav-item {:id            :app.router/campaigns
              :name          "Campaigns"
              :href          "/campaigns"
              :current-route current-route}]
   [profile-button]])

(defn nav
  [current-route]
  [:> mui/AppBar {:position "static"}
   [:> mui/Toolbar
    [:> mui/IconButton {:edge "start" :color "inherit"}
     [:> icons/Menu]]
    [:> mui/Typography {:variant "h6" :style {:flexGrow 1}}
     "Grimsward"]
    (if @(rf/subscribe [:logged-in?])
      [nav-authenticated {:current-route current-route}]
      [nav-public {:current-route current-route}])]])
