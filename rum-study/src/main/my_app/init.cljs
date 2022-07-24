(ns my-app.init
  (:require [rum.core :as rum]
            [my-app.db :as db]))

(def input (atom "")) ;;入力値を保存する

(def todo-list (atom ()))

(defn handle-on-change[] 
  (str (.-value (.getElementById js/document "input_m")))) ;;入力値を返すだけ。関数化する必要はないかも 

(defn delete-todo [list todo]
  (remove (fn[x](= x todo)) list)) 
  

(defn handle-on-delete [todo]
  (swap! todo-list delete-todo todo))
  

(defn get-max-index []
  ;; (max (get @todo-list 0))
  ;; (let [temp-indice '(0)]
    ;; (for [temp @todo-list]
    ;;   ;; (conj temp-indice (get temp 0))
    ;;   )
    ;; (apply max temp-indice) 
    ;; )
  (get (first @todo-list) 0))
  

(defn handle-on-save []
  (swap! todo-list conj [(inc (get-max-index)) @input])) ;;最大ID＋１をIDとする
  ;;(println @todo-list) ;;確認用
  

(rum/defc button < rum/reactive [name func]
          [:button {:type "button" :on-click func} name])

(rum/defc input-area < rum/reactive
  [] 
  [:div
   [:form {:action "" :method "get"}
    [:input {:type "text" :name "name" :id "input_m" :value (rum/react input) :on-change (fn[] (swap! input handle-on-change))}]
  ;;  [:button {:type "button" :on-click (fn [_] (handle-on-save)) } "ADD"]
    (button "ADD" handle-on-save)]])


(rum/defc header []
  [:h1 {:class "head-bar"} "ToDO App With Rum"])

;; (rum/defc button2
;;   [f]
;;   [:button {:on-click f} "私をクリックして！"])

;; (rum/defc base
;;   []
;;   (let [[click-count set-click-count!] (rum/use-state 0)
;;         on-click (fn [event]
;;                    (js/console.log event)
;;                    (set-click-count! (inc click-count)))]
;;     [:div "ボタンクリックテスト"
;;      (button2 on-click)]))

(rum/defc disp-area < rum/reactive []
  [:ul
     (for [todo (rum/react todo-list)]
       [:li {:key (get todo 0)} (get todo 1)
        (let [on-delete (fn[](handle-on-delete todo))]
         (button "DEL" on-delete)
         )])])


(defn show-all-todo []
  (header) 
  (input-area)) ;;こっちで更新されてしまう。。

(rum/defc top-page []
          [:div {:class "tesss"} (header)
           [:div {:class "input-area"} (input-area)]
           [:div {:class "disp-area"} (disp-area)]])
          


(defn init []
  ;; (db/create-table) ;;DB接続の仕方がわからない。。
  (rum/mount (top-page) (.getElementById js/document "app")))

