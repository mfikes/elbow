(ns elbow.core
  (:require [clojure.string :as string]
            [cljs.nodejs :as nodejs]
            [elbow.load :as load]
            [replumb.core :as replumb]))

(js* "var window = global;")
(set! (.. js/window -cljs) #js {})

(nodejs/enable-util-print!)

(defn read-eval-print-loop
  [load-fn!]
  (let [node-readline (nodejs/require "readline")
        rl (.createInterface node-readline
             #js {:input  (.-stdin js/process)
                  :output (.-stdout js/process)})]
    (doto rl
      (.setPrompt (replumb/get-prompt))
      (.on "line"
        (fn [cmd]
          (replumb/read-eval-call
            {:verbose  false
             :load-fn! load-fn!}
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
  (if-not (empty? args)
    (read-eval-print-loop (-> (first args)
                            arg->src-paths
                            load/make-node-load-fn))
    (read-eval-print-loop [])))

(set! *main-cli-fn* -main)
