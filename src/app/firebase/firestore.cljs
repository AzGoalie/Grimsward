(ns app.firebase.firestore
  (:require [re-frame.core :as rf]))

(defonce ^js db (.firestore js/firebase))

(rf/reg-fx
 ::create
 (fn [{:keys [collection-name document]}]
   (-> db
       (.collection collection-name)
       (.add (clj->js document))
       (.catch #(rf/dispatch [::error collection-name %])))))

(rf/reg-fx
 ::query
 (fn [{:keys [collection-name db-path] {:keys [field op val]} :where}]
   (-> db
       (.collection collection-name)
       (.where field op val)
       (.onSnapshot #(rf/dispatch [::update db-path %]) #(rf/dispatch [::error db-path %])))))

(defn transform-doc
  [^js doc]
  (let [id   (keyword (.-id doc))
        data (js->clj (.data doc) :keywordize-keys true)]
    [id (assoc data :id id)]))

(rf/reg-event-db
 ::update
 (fn [db [_ db-path ^js querySnapshot]]
   (let [values (->> (.-docs querySnapshot)
                     (map transform-doc)
                     (into {}))]
     (-> db
         (update-in [:errors :firestore] dissoc db-path)
         (assoc db-path values)))))

(rf/reg-event-db
 ::error
 (fn [db [_ db-path ^js error]]
   (js/console.error error)
   (assoc-in db [:errors :firestore db-path] {:code    (.-code error)
                                              :message (.-message error)})))