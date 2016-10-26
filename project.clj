(defproject caltrack "0.1.0-SNAPSHOT"
  :description "Calendar-based time tracking"
  :license {:name "MIT License"
            :url "https://opensource.org/licenses/MIT"}
  :dependencies [[commons-logging/commons-logging "1.2"]
                 [compojure "1.5.1"]
                 [hiccup "1.0.5"]
                 [net.sf.biweekly/biweekly "0.6.0"]
                 [org.apache.httpcomponents/httpclient "4.5.2"]
                 [org.clojure/clojure "1.8.0"]
                 [org.clojure/data.xml "0.0.8"]
                 [ring/ring-core "1.5.0"]
                 [ring/ring-json "0.4.0"]
                 [ring/ring-jetty-adapter "1.5.0"]]
  :plugins [[lein-npm "0.6.2"]
            [lein-resource "16.9.1"]]
  :npm {:devDependencies [[babel-preset-es2015-rollup "1.2.0"]
                          [bootstrap "3.3.7"]
                          [rollup "0.36.3"]
                          [rollup-plugin-babel "2.6.1"] 
                          [rollup-plugin-node-resolve "2.0.0"]
                          [d3-collection "1.0.1"]
                          [d3-request "1.0.2"]
                          [d3-selection "1.0.2"]
                          [d3-time-format "2.0.2"]
                          [d3 "4.2.8"]
                          [whatwg-fetch "1.0.0"]]
        :package {:scripts {:build_home   "rollup --config rollup.config.home.js"
                            :build_report "rollup --config rollup.config.report.js"}}}
  :prep-tasks [["npm" "install"]
               ["npm" "run" "build_home"]
               ["npm" "run" "build_report"]
               "resource" "javac" "compile"]
  :resource {:resource-paths ["node_modules/bootstrap/dist"]
             :skip-stencil [#"node_modules/bootstrap/dist/.*"]
             :target-path "resources/public/assets/bootstrap"}
  :main caltrack.web
  :source-paths ["src/clojure"]
  :java-source-paths ["src/java"]
  :target-path "target/%s"
  :javac-options ["-target" "1.8" "-source" "1.8" "-Xlint:-options"]
  :min-lein-version "2.0.0"
  :uberjar-name "caltrack-standalone.jar"
  :profiles {:uberjar {:aot :all}})
