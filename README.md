# water_sort_kotlin
ウォーターソートパズル解答プログラム（Kotlin版）

[「Kotlinで再帰関数（AIに丸投げ）](https://happyclam.github.io/software/2025-06-07/kotlins_recursion)

~~~Kotlin
// ウォーターボトルをBoxに配置
val box = Box(
    mutableListOf(
        Bottle().apply { addAll(listOf(Color.ORANGE, Color.RED, Color.ORANGE, Color.RED)) },
        Bottle().apply { addAll(listOf(Color.BLUE, Color.BLUE, Color.ORANGE, Color.RED)) },
        Bottle().apply { addAll(listOf(Color.RED, Color.ORANGE, Color.BLUE, Color.BLUE)) },
        Bottle(),
        Bottle()
        )
    )
box.display()
~~~

~~~Kotlin
//環境

$ java --version
openjdk 17.0.15 2025-04-15
OpenJDK Runtime Environment (build 17.0.15+6-Ubuntu-0ubuntu124.04)
OpenJDK 64-Bit Server VM (build 17.0.15+6-Ubuntu-0ubuntu124.04, mixed mode, sharing)

$ kotlin -version
Kotlin version 2.1.20-release-217 (JRE 17.0.15+6-Ubuntu-0ubuntu124.04)

$ echo $GRADLE_USER_HOME
/opt/gradle/gradle-8.7

//コンパイル
$ kotlinc -include-runtime -d ./game.jar waterBottles

//実行
$ java -jar game.jar
-----
|赤色|赤色|青色|　　|　　|
|橙色|橙色|青色|　　|　　|
|赤色|青色|橙色|　　|　　|
|橙色|青色|赤色|　　|　　|
cnt = 497
Complete!!
counter = 1
-----
|橙色|青色|　　|赤色|　　|
|橙色|青色|　　|赤色|　　|
|橙色|青色|　　|赤色|　　|
|橙色|青色|　　|赤色|　　|
counter = 2
-----
|橙色|青色|　　|　　|　　|
|橙色|青色|　　|赤色|　　|
|橙色|青色|　　|赤色|　　|
|橙色|青色|赤色|赤色|　　|
counter = 3
-----
|　　|青色|　　|　　|　　|
|橙色|青色|　　|赤色|　　|
|橙色|青色|橙色|赤色|　　|
|橙色|青色|赤色|赤色|　　|
counter = 4
-----
|　　|　　|青色|　　|　　|
|橙色|　　|青色|赤色|　　|
|橙色|青色|橙色|赤色|　　|
|橙色|青色|赤色|赤色|　　|
counter = 5
-----
|　　|橙色|青色|　　|　　|
|　　|橙色|青色|赤色|　　|
|　　|青色|橙色|赤色|　　|
|橙色|青色|赤色|赤色|　　|
counter = 6
-----
|　　|橙色|青色|　　|　　|
|　　|橙色|青色|　　|　　|
|赤色|青色|橙色|赤色|　　|
|橙色|青色|赤色|赤色|　　|
counter = 7
-----
|　　|　　|青色|　　|　　|
|橙色|橙色|青色|　　|　　|
|赤色|青色|橙色|赤色|　　|
|橙色|青色|赤色|赤色|　　|
counter = 8
-----
|　　|赤色|青色|　　|　　|
|橙色|橙色|青色|　　|　　|
|赤色|青色|橙色|　　|　　|
|橙色|青色|赤色|赤色|　　|
-----
手数: 8手
経過時間: 0.158秒
~~~
