(ns my-app.db
 (:require [clojure.java.jdbc :as j]))

(def demo-settings
  {:classname   "org.h2.Driver"
   :subprotocol "h2:mem"
   :subname     "demo;DB_CLOSE_DELAY=-1"
   :user        "sa"
   :password    ""})

(j/db-do-commands demo-settings
                  (j/create-table-ddl :filetable
                                      [:name "varchar(3200)"]
                                      [:path "varchar(3200)"]
                                      [:origname "varchar(3200)"]))

(defn create-table 
  []
  (j/db-do-commands))