# dreimultiplatform
Shared Repository for [KMM](https://kotlinlang.org/lp/mobile/) projects

# use it in your KMM Project
* In your `build.gradle` add repository url for bintray
```groovy
maven {
    url  "https://dl.bintray.com/dreipol/dreimultiplatform"
}
```
* In your `shared/build.gradle` add the dependency
```groovy
dependencies {
    implementation "ch.dreipol:dreimultiplatform:<tag or latest commit-short on develop>"
}
```
* Sync your gradle project and you should be able to import packages from dreimultiplatform
* Enjoy

# Local Development inside KMM Project
* Clone repo into `/SOME-DIR/dreimultiplatform` (outside of your KMM repository)
* In `local.properties` add:
```groovy
dreimultiplatform.dir=/SOME-DIR/dreimultiplatform
```
* In your `settings.gradle` dynamically include the project if the setting exists
```groovy
Properties properties = new Properties()
File localProperties = new File(rootProject.projectDir.absolutePath + '/local.properties')
if (localProperties.exists()) {
    properties.load(localProperties.newDataInputStream())
    def dreimultiplatformDir = properties.getProperty('dreimultiplatform.dir')
    if (dreimultiplatformDir != null) {
        include ':dreimultiplatform'
        project(':dreimultiplatform').projectDir = new File(dreimultiplatformDir)
    }
}
```
* In your `build.gradle` add repository url for bintray
```groovy
maven {
    url  "https://dl.bintray.com/dreipol/dreimultiplatform"
}
```
* In your `shared/build.gradle` add the dependency if the local property is set otherwise fetch from JitPack
```groovy
dependencies {

    Properties properties = new Properties()
    File localProperties = new File(rootProject.projectDir.absolutePath + '/local.properties')
    String dreimultiplatformDir = null
    if (localProperties.exists()) {
        properties.load(localProperties.newDataInputStream())
        dreimultiplatformDir = properties.getProperty('dreimultiplatform.dir')
    }
    if (dreimultiplatformDir != null) {
        implementation project (":dreimultiplatform")
    } else {
        // if the dreimultiplatform local directory is not set we will fetch from bintray
        implementation "ch.dreipol:dreimultiplatform:<tag or latest commit-short on develop>"
    }
}
```
* Sync your gradle project and you should be able to import packages from dreimultiplatform
* Enjoy