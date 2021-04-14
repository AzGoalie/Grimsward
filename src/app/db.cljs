(ns app.db
  (:require [re-frame.core :as rf]
            [cljs-time.core :as cljs-time]))

(defn random-future-date
  []
  (cljs-time/plus (cljs-time/now) (cljs-time/days (rand-int 30))))

(def initial-app-db {:auth          {}
                     :errors        {}
                     :current-route nil
                     :campaigns     {:21f1e04a-a18d-469f-a29e-52c106c3bc5f
                                     {:id           "21f1e04a-a18d-469f-a29e-52c106c3bc5f"
                                      :title        "An Awoo Adventure"
                                      :description  "$300 fines all around."
                                      :next-session (random-future-date)
                                      :owner        "bdd8170d-5478-43e9-a93f-320b79afb084"
                                      :players      ["51d6f20f-2a1e-4b1b-83c8-dbca1c2104fc"
                                                     "8a043ec0-3052-4098-b16c-57693d4f0ac8"
                                                     "74d64844-3ce2-47e4-96ba-d88d2bc87898"]}

                                     :eaf577e4-0da0-44cc-9dc6-cce1c4277ebd
                                     {:id           "eaf577e4-0da0-44cc-9dc6-cce1c4277ebd"
                                      :title        "The Bees Knees"
                                      :description  "AAAAAAAAA"
                                      :next-session (random-future-date)
                                      :owner        "93d39674-18cb-4de0-9ac4-2c4dd9860afd"
                                      :players      ["7ada5dc3-11a2-4da0-a631-d5afb1a47f38"
                                                     "0480f62a-a806-4192-83c2-a69d9bf34029"
                                                     "6fd0454d-6f46-4fc0-8d4f-f2166a72a0a8"
                                                     "53a2053b-8fd4-4bb2-9d63-6c34d750c01f"]}

                                     :67c9ce1c-18f4-4087-a911-f8f2d143e845
                                     {:id           "67c9ce1c-18f4-4087-a911-f8f2d143e845"
                                      :title        "Stop Right There Criminal Scum!"
                                      :description  "Stop, you’ve violated the law, pay the court of fine or serve your sentence, your stolen goods are now forfeit!"
                                      :next-session (random-future-date)
                                      :owner        "9436e5d0-e680-487c-8614-a6fe127c1e70"
                                      :players      ["756a1d3c-d416-405a-8a63-6c0302f36b27"
                                                     "ecbd1ebd-fdd2-4468-966c-726588b3b852"
                                                     "869cf359-d15b-4b4a-942b-da968242426c"
                                                     "5f5111a9-683e-41aa-aad4-65f99ee99d42"
                                                     "1453b796-8d34-4d38-b99a-334e5a91d4d1"]}}})

(rf/reg-event-db
 :initialize-db
 (fn [_ _]
   initial-app-db))

(rf/reg-sub
 :loading
 (fn [db _]
   (:loading db)))
