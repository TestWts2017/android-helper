Android Helper
==============

Download
--------
To get a Git project into your build:
Add the JitPack repository to your build file
Add it in your root build.gradle at the end of repositories:
```groovy
allprojects {
  repositories {
    ...
    maven { url 'https://jitpack.io' }
  }	
}
```
Add the dependency
```groovy
dependencies {
  implementation 'com.github.TestWts2017:android-helper:0.0.2'
}  
```
