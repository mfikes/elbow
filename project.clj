(defproject elbow "0.1.0-SNAPSHOT"
  :description "Use Replumb in Node"
  :url "https://github.com/mfikes/elbow"
  :license {:name "Eclipse Public License"
            :url "https://github.com/mfikes/elbow/blob/master/LICENSE"}
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [org.clojure/clojurescript "1.8.51"]
                 [replumb/replumb "0.2.1"]]
  :clean-targets [:target-path "out"]
  :plugins [[lein-cljsbuild "1.1.1"]]
  :cljsbuild {
    :builds [{
        :source-paths ["src"]
        :compiler {
          :target        :nodejs
          :main          elbow.core
          :output-to     "out/main.js"
          :output-dir    "out"
          :optimizations :none}}]})
