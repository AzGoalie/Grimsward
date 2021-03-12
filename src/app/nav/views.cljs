(ns app.nav.views
  (:require [reagent.core :as r]
            [re-frame.core :as rf]
            ["@material-ui/core" :as mui]
            ["@material-ui/icons" :as icons]))

(defn nav-item
  [{:keys [id href dispatch name active-nav]}]
  (:> mui/Typography
    [:> mui/Link {:href      href
                  :on-click  dispatch
                  :color     "inherit"
                  :style     {:padding 10}
                  :underline (if (= id active-nav) "always" "hover")}
     name]))

(defn nav-public
  [{:keys [active-nav]}]
  [:<>
   [nav-item {:id         :sign-up
              :name       "Sign Up"
              :href       "#sign-up"
              :active-nav active-nav
              :dispatch   #(rf/dispatch [:set-active-nav :sign-up])}]
   [nav-item {:id         :log-in
              :name       "Log In"
              :href       "#log-in"
              :active-nav active-nav
              :dispatch   #(rf/dispatch [:set-active-nav :log-in])}]])

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
  [{:keys [active-nav]}]
  [:<>
   [nav-item {:id         :campaigns
              :name       "Campaigns"
              :href       "#campaigns"
              :active-nav active-nav
              :dispatch   #(rf/dispatch [:set-active-nav :campaigns])}]
   [profile-button]])

(defn nav
  []
  (let [active-nav @(rf/subscribe [:active-nav])]
    [:> mui/AppBar {:position "static"}
     [:> mui/Toolbar
      [:> mui/IconButton {:edge "start" :color "inherit"}
       [:> icons/Menu]]
      [:> mui/Typography {:variant "h6" :style {:flexGrow 1}}
       "Grimsward"]
      (if @(rf/subscribe [:logged-in?])
        [nav-authenticated {:active-nav active-nav}]
        [nav-public {:active-nav active-nav}])]]))
