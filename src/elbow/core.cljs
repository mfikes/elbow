(ns elbow.core
  (:require [clojure.string :as string]
            [cljs.nodejs :as nodejs]
            [replumb.core :as replumb]))

(nodejs/enable-util-print!)

(def fs (nodejs/require "fs"))

(defn- node-read-file
  "Accepts a filename to read and a callback. Upon success, invokes
  callback with the source. Otherwise invokes the callback with nil."
  [filename cb]
  (.readFile fs filename "utf-8"
    (fn [err source]
      (cb (when-not err
            source)))))

(defn read-eval-print-loop
  [src-paths]
  (let [node-readline (nodejs/require "readline")
        rl (.createInterface node-readline
             #js {:input  (.-stdin js/process)
                  :output (.-stdout js/process)})]
    (doto rl
      (.setPrompt (replumb/get-prompt))
      (.on "line"
        (fn [cmd]
          (replumb/read-eval-call
            (replumb/nodejs-options src-paths node-read-file)
            (fn [res]
              (-> res
                replumb/result->string
                println)
              (.setPrompt rl (replumb/get-prompt))
              (.prompt rl))
            cmd)))
      (.prompt))))

(defn arg->src-paths
  [arg]
  (string/split arg #":"))

(defn -main [& args]
  (read-eval-print-loop
    (if-not (empty? args)
      (-> (first args)
        arg->src-paths)
      [])))

(set! *main-cli-fn* -main)
