(ns app.campaign.views.campaign)

(defn campaign [match]
  (let [id (get-in match [:parameters :path :id])]
    [:div (str "The campaign has ID: " id)]))