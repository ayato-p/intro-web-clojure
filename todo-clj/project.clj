(defproject todo-clj "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [ring "1.4.0"]
                 [compojure "1.4.0"]
                 [environ "1.0.1"]]
  :plugins [[lein-environ "1.0.1"]]
  :profiles
  {:dev {:dependencies [[prone "0.8.2"]]
         :env {:dev true}}})
