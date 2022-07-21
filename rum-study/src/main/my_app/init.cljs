(ns my-app.init
  (:require [rum.core :as rum]))


(def input (atom "")) ;;入力値を保存する

(def todo-list (atom ()))

(defn handle-on-change[] 
  (str (.-value (.getElementById js/document "input_m"))) ;;入力値を返すだけ。関数化する必要はないかも
  )

(defn handle-on-save[]
  (swap! todo-list conj @input)
  (println @todo-list) ;;確認用
  )

(rum/defc button < rum/reactive [name func]
          [:button {:type "button" :on-click func} name ])

(rum/defc input-area < rum/reactive []
   [:div 
    [:form {:action "" :method "get"}
     [:input {:type "text" :name "name" :id "input_m" :value (rum/react input) :on-change (fn [_] (swap! input handle-on-change))}]
    ;;  [:button {:type "button" :on-click (fn [_] (handle-on-save)) } "ADD"]
     (button "ADD" handle-on-save)
     ]
    ]
)

(rum/defc header []
  [:h1 {:class "head-bar"} "ToDO App With Rum"])

(rum/defc disp-area < rum/reactive []
  [:ul
  ;;  (for [todo (vals (rum/react todo-list))]
  ;;    [:li todo (println (vals (rum/react todo-list)))]
  ;;    )]
     (for [todo (rum/react todo-list)]
       [:li {:key (.indexOf @todo-list todo)} todo
        (button "DEL" (fn[_](println "未実装")))])])

(defn show-all-todo []
  (header) 
  (input-area)) ;;こっちで更新されてしまう。。

(rum/defc top-page []
          [:div {:class "tesss"} (header)
           [:div {:class "input-area"} (input-area)]
           [:div {:class "disp-area"} (disp-area)]
          ]
)

(defn init []
  (rum/mount (top-page) (.getElementById js/document "app")))

