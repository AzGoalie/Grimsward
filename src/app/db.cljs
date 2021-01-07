(ns app.db
  (:require [re-frame.core :as rf]))

(def initial-app-db {:auth {:uid nil}
                     :errors {}
                     :nav {:active-page :home
                           :active-nav :home}
                     :users {"example@grimsward.com" {:uid "example@grimsward.com"
                                                      :profile {:first-name "Example"
                                                                :last-name "User"
                                                                :email "example@grimsward.com"
                                                                :password "password"}
                                                      :role :user}}})

(rf/reg-event-db
 :initialize-db
 (fn [_ _]
   initial-app-db))
