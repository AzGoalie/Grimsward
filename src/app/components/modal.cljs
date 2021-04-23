(ns app.components.modal
  (:require ["@chakra-ui/react" :refer [Modal ModalBody ModalCloseButton ModalContent ModalFooter ModalHeader ModalOverlay]]))

(defn modal
  [{:keys [open on-close body footer header]}]
  [:> Modal {:is-open  open
             :on-close on-close}
   [:> ModalOverlay]
   [:> ModalContent
    [:> ModalHeader header]
    [:> ModalCloseButton]
    [:> ModalBody body]
    [:> ModalFooter footer]]])

