(ns app.nav.views
  (:require [reagent.core :as r]
            [re-frame.core :as rf]
            ["@material-ui/core/Typography" :default Typography]
            ["@material-ui/core/Link" :default Link]
            ["@material-ui/core/Paper" :default Paper]
            ["@material-ui/core/ClickAwayListener" :default ClickAwayListener]
            ["@material-ui/core/MenuList" :default MenuList]
            ["@material-ui/core/MenuItem" :default MenuItem]
            ["@material-ui/core/Popper" :default Popper]
            ["@material-ui/core/IconButton" :default IconButton]
            ["@material-ui/core/AppBar" :default AppBar]
            ["@material-ui/core/Toolbar" :default Toolbar]
            ["@material-ui/icons/AccountCircle" :default AccountCircle]
            ["@material-ui/icons/Menu" :default Menu]))

(defn nav-item
  [{:keys [id href name current-route]}]
  (:> Typography
    [:> Link {:href      href
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
  [:> Popper {:open      (boolean anchor-el)
              :anchor-el anchor-el}
   [:> Paper
    [:> ClickAwayListener {:on-click-away handle-close}
     [:> MenuList
      [:> MenuItem {:on-click (fn [_] (handle-close) (rf/dispatch [:navigate :app.router/profile]))}
       "Profile"]
      [:> MenuItem {:on-click #(rf/dispatch [:log-out])}
       "Sign Out"]]]]])

(defn profile-button
  []
  (let [anchor-el (r/atom nil)
        handle-click #(reset! anchor-el (.-currentTarget %))
        handle-close #(reset! anchor-el nil)]
    (fn []
      [:<>
       [:> IconButton {:on-click handle-click}
        [:> AccountCircle]]
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
  [:> AppBar {:position "static"}
   [:> Toolbar
    [:> IconButton {:edge "start" :color "inherit"}
     [:> Menu]]
    [:> Typography {:variant "h6" :style {:flexGrow 1}}
     "Grimsward"]
    (if @(rf/subscribe [:logged-in?])
      [nav-authenticated {:current-route current-route}]
      [nav-public {:current-route current-route}])]])
