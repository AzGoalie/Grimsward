(ns app.db
  (:require [re-frame.core :as rf]
            [cljs-time.core :as cljs-time]))

(defn generate-campaign
  [title description num-players]
  {:title        title
   :owner        (random-uuid)
   :id           (random-uuid)
   :description  description
   :next-session (cljs-time/plus (cljs-time/now) (cljs-time/days (rand-int 30)))
   :players      (map #(.toString %) (take num-players (repeatedly random-uuid)))})

(def initial-app-db {:auth          {}
                     :errors        {}
                     :current-route nil
                     :campaigns     [(generate-campaign "An Awoo Adventure" "$300 fines all around." 5)
                                     (generate-campaign "The Bees Knees" "AAAAAAAAA" 3)
                                     (generate-campaign "Stop Right There Criminal Scum!" "Stop, you’ve violated the law, pay the court of fine or serve your\n\nsentence, your stolen goods are now forfeit!" 4)]})

(rf/reg-event-db
 :initialize-db
 (fn [_ _]
   initial-app-db))
