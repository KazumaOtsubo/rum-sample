(ns my-app.init
  (:require [rum.core :as rum]))


(def input (atom "")) ;;入力値を保存する

(defn handle-on-change[] 
  (str (.-value (.getElementById js/document "input_m"))) ;;入力値を返すだけ。関数化する必要はないかも
  )

(rum/defc input-area < rum/reactive []
  [:div {:class "input-area"}
   [:form {:action "" :method "get"}
    [:input {:type "text" :name "name" :id "input_m" :value (rum/react input) :on-change (fn [_] (swap! input handle-on-change))}]]
  [:p (rum/react input)] ;;変更を監視
])

(rum/defc header []
  [:h1 {:class "head-bar"} "ToDO App With Rum"])


(defn show-all-todo []
  (header) 
  (input-area)) ;;こっちで更新されてしまう。。

(defn init []
  (rum/mount (show-all-todo) (.getElementById js/document "app")))

