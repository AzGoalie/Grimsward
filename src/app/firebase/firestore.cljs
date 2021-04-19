(ns app.firebase.firestore
  (:require [re-frame.core :as rf]
            [app.firebase.core :refer [firebase-app]]
            ["firebase/firestore" :refer [getFirestore collection onSnapshot query where]]))

(defonce db (getFirestore firebase-app))

(rf/reg-fx
 ::query
 (fn [{:keys [collection-name db-path] {:keys [field op val]} :where}]
   (let [coll (collection db collection-name)
         q (query coll (where field op val))
         on-change #(rf/dispatch [::update db-path %])
         on-error #(rf/dispatch [::error db-path %])]
     (onSnapshot q on-change on-error))))

(defn transform-doc
  [doc]
  (let [id   (keyword (.-id doc))
        data (js->clj (.data doc) :keywordize-keys true)]
    [id (assoc data :id id)]))

(rf/reg-event-db
 ::update
 (fn [db [_ db-path querySnapshot]]
   (let [values (->> (.-docs querySnapshot)
                     (map transform-doc)
                     (into {}))]
     (-> db
         (update-in [:errors :firestore] dissoc db-path)
         (assoc db-path values)))))

(rf/reg-event-db
 ::error
 (fn [db [_ db-path error]]
   (assoc-in db [:errors :firestore db-path] {:code    (.-code error)
                                              :message (.-message error)})))