package bel

import groovy.transform.CompileStatic


// gradle plugin: https://graalvm.github.io/native-build-tools/0.9.4/gradle-plugin.html

// install graalvm native-image
// ----------------------------
// sdk install java 22.0.0.2.r11-grl
// gu install native-image
// sudo apt-get install build-essential libz-dev zlib1g-dev

// graalvm and groovy
// ------------------
// install native-image  https://www.graalvm.org/22.0/reference-manual/native-image/
// graal and groovy https://e.printstacktrace.blog/graalvm-and-groovy-how-to-start/
// 2021 https://croz.net/news/packaging-groovy-application-as-a-graalvm-native-image/


/*
either
gradle nativeBuild

OR
*/

/* STEP 0
gradle build
*/


/* STEP 1: dynamically called code will be missing...
a) change to the build/libs dir
then b) ~/.sdkman/candidates/java/22.0.0.2.r11-grl/lib/svm/bin/native-image
native-image --allow-incomplete-classpath \
--report-unsupported-elements-at-runtime \
--initialize-at-build-time \
--initialize-at-run-time=org.codehaus.groovy.control.XStreamUtils,groovy.grape.GrapeIvy \
--no-server \
 -jar test-graalvm-all.jar helloBenno --no-fallback
 */

/* STEP 2: create configuration file with
java -agentlib:native-image-agent=config-output-dir=conf/ \
    -jar test-graalvm-all.jar bel.Main
 */

/* STEP 3: use conf/ folder to
native-image --allow-incomplete-classpath \
--report-unsupported-elements-at-runtime \
--initialize-at-build-time \
--initialize-at-run-time=org.codehaus.groovy.control.XStreamUtils,groovy.grape.GrapeIvy \
--no-fallback \
--no-server \
 -jar test-graalvm-all.jar \
-H:ConfigurationFileDirectories=conf/ \
helloBenno
 */


@CompileStatic
class Main {

    static void rand() {
        def random = new Random().nextInt(1000)
        println "The random number is: $random"
        def sum = (0..random).sum { int num -> num * 2 }
        println "The doubled sum of numbers between 0 and $random is $sum"
    }

    static Closure<String> aClosure = {" --> get another string..."}

    static void main(String[] args) {
        println ("hello Benno" + aClosure())
        rand()
    }
}
