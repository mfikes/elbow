(ns elbow.load
  (:require [cljs.nodejs :as nodejs]))

(defn- extension->lang
  [extension]
  (if (= ".js" extension)
    :js
    :clj))

(defn- try-load
  [fs [{:keys [full-path lang]} & more-files] cb]
  (if full-path
    (.readFile fs full-path "utf-8"
      (fn [err source]
        (if-not err
          (cb {:lang   lang
               :source source})
          (try-load fs more-files cb))))
    (cb nil)))

(defn- files-to-try
  [src-paths path extensions]
  (for [src-path src-paths
        extension extensions]
    {:full-path (str src-path "/" path extension)
     :lang      (extension->lang extension)}))

(defn node-load
  [src-paths {:keys [macros path]} cb]
  (let [fs (nodejs/require "fs")
        extensions (if macros
                     [".clj" ".cljc"]
                     [".cljs" ".cljc" ".js"])]
    (try-load fs (files-to-try src-paths path extensions) cb)))