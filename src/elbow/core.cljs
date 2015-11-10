(ns elbow.core
  (:require [cljs.nodejs :as nodejs]
            [replumb.core :as replumb]))

(js* "var window = global;")
(set! (.. js/window -cljs) #js {})

(nodejs/enable-util-print!)

(defn read-eval-print-loop []
  (let [node-readline (nodejs/require "readline")
        rl (.createInterface node-readline
             #js {:input  (.-stdin js/process)
                  :output (.-stdout js/process)})]
    (doto rl
      (.setPrompt (replumb/get-prompt))
      (.on "line"
        (fn [cmd]
          (replumb/read-eval-call
            {:verbose false}
            (fn [res]
              (-> res
                replumb/result->string
                println)
              (.setPrompt rl (replumb/get-prompt))
              (.prompt rl))
            cmd)))
      (.prompt))))

(defn -main [& args]
  (read-eval-print-loop))

(set! *main-cli-fn* -main)
