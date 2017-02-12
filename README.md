
# Wheat Project

Libraries to develop Android apps on Scala.

## wheat-harvest

Provides sbt tasks that generate Java sources from XML layout files.

```
> harvestViewHolder foo_bar.xml
```

## wheat-splicer

Provides sbt tasks that expand local aar files.

For example, from build definitions like below:

```
$ cat ./targets.gradle
dependencies {
    compile 'com.android.support:recyclerview-v7:25.1.0'
    compile 'com.android.support:appcompat-v7:25.1.0'
    compile 'com.android.support:design:25.1.0'
    compile 'com.android.support:cardview-v7:25.1.0'
}
```

By running task `splicerExpand`:

```
> your-project/splicerExpand
```

Following files are generated:

```
$ find ./your-project -name '*.java' -or -name '*.jar'
 ./your-project/libs-expanded/animated-vector-drawable/classes.jar
 ./your-project/libs-expanded/appcompat-v7/classes.jar
 ./your-project/libs-expanded/appcompat-v7/src-generated/android/support/v7/appcompat/R.java
 ./your-project/libs-expanded/cardview-v7/classes.jar
 ./your-project/libs-expanded/cardview-v7/src-generated/android/support/v7/cardview/R.java
 ./your-project/libs-expanded/design/classes.jar
 ./your-project/libs-expanded/design/src-generated/android/support/design/R.java
 ./your-project/libs-expanded/recyclerview-v7/classes.jar
 ./your-project/libs-expanded/recyclerview-v7/src-generated/android/support/v7/recyclerview/R.java
 ./your-project/libs-expanded/support-compat/classes.jar
 ./your-project/libs-expanded/support-core-ui/classes.jar
 ./your-project/libs-expanded/support-core-utils/classes.jar
 ./your-project/libs-expanded/support-fragment/classes.jar
 ./your-project/libs-expanded/support-media-compat/classes.jar
 ./your-project/libs-expanded/support-v4/classes.jar
 ./your-project/libs-expanded/support-vector-drawable/classes.jar
 ./your-project/libs-expanded/transition/classes.jar
 ./your-project/libs-expanded/transition/src-generated/android/support/transition/R.java
```

## License

This repository is published under the MIT LICENSE

 * https://github.com/x7c1/Wheat/blob/master/LICENSE
