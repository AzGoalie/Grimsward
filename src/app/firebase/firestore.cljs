(ns app.firebase.firestore
  (:require [re-frame.core :as rf]
            ["firebase/app" :default firebase]))

(comment
  (-> (.firestore firebase)
      (.collection "campaigns")
      (.where "owner" "==" "GIDjOicFJ5hMdBaOctebJCP6AVq1")
      (.get)
      (.then (fn [snapshot] (.forEach snapshot #(println (.data %)))))))

(rf/reg-fx
 :firebase/firestore-query
 (fn [{:keys [collection query on-success on-failure]}]
   (-> (.firestore firebase)
       (.collection collection)
       (.where (:field query) (:op query) (:value query))
       (.then on-success)
       (.catch on-failure))))