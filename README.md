<img src="https://upload.wikimedia.org/wikipedia/commons/thumb/d/d7/Android_robot.svg/872px-Android_robot.svg.png" width="96"> <img src="https://uber.github.io/img/h3Logo-color.svg" width="96">

# Android Uber H3 Sample
A sample Android project that uses [Uber's h3-java library](https://github.com/uber/h3-java).

Initially, following the intended [usage][1] as seen in their README should make it work. If it doesn't see the setup below.

## Setup

Known Issue: [Android, can't use library][2]

`UnsatisfiedLinkError`: This can be encountered when the corresponding native library is not copied/detected in the project. Following [NickRadu's workaround][3] should make it work.
Below is a step-by-step guide.

1. Add a JNI folder in your project app folder and rename it `jniLibs` (`app/src/main/jniLibs`) (for some reason, having it named `jni` only doesn't work for me).
2. [Get the H3 JAR][4] (make sure you use the same version) and extract the JAR contents.
3. Copy the folders prefixed with `android-` and insert them in the `jniLibs` folder (from step 1).
4. Rename the copied folders, remove the `android-` prefix.
5. Add `splits { abi { enable false } }` to your app's build.gradle file (within `android`).

Done. In general, the library should now work as expected.

If during the app installation you encounter:


 - [`INSTALL_FAILED_NO_MATCHING_ABIS`][5],
then depending on your *test* device, create a copy of the folder (along with its contents) and rename it as needed.
For example, a device running on arm64-v8a, I just made a copy of the `arm64` folder and renamed it to `arm64-v8a`. Or if you're using an emulator, make sure that you're not using one with an `x86` CPU.

 - `D8 errors: Invoke-customs are only supported starting with Android O (--min-api 26)`, add these compile options in your app's build.gradle (within `android` -- note that it may change depending on your system's Java version)

        compileOptions {
            sourceCompatibility JavaVersion.VERSION_1_8
            targetCompatibility JavaVersion.VERSION_1_8
        }

***Note: It is best to test the app on multiple CPU architecture types first to see it's behavior.***

To quickly see the CPU Architecture of the device, you could install [Droid Hardware Info][6], or run a quick [test code][7] yourself.

## Test

Here's a test block I used and its corresponding result logs:

    private fun testH3() {
        val h3 = H3Core.newSystemInstance()

        val lat = 37.775938728915946
        val lng = -122.41795063018799
        val resolution = 8

        val hexAddr = h3.geoToH3Address(lat, lng, resolution)
        val hex = h3.stringToH3(hexAddr)
        val kRingsResult = h3.kRings(hexAddr, 1)

        Log.d("H3Test", "geoToH3Address: $hexAddr")
        Log.d("H3Test", "stringToH3: $hex")
        Log.d("H3Test", "kRings: $kRingsResult")
    }

Result:

    D/H3Test: geoToH3Address: 8828308281fffff
    D/H3Test: stringToH3: 613196570331971583
    D/H3Test: kRings: [[8828308281fffff], [8828308281fffff, 882830828dfffff, 8828308285fffff, 8828308287fffff, 8828308283fffff, 882830828bfffff, 8828308289fffff]]

Using the test app:

  ![](/test/android-h3-sample-screenshot.jpg)

  [1]: https://github.com/uber/h3-java#usage
  [2]: https://github.com/uber/h3-java/issues/23
  [3]: https://github.com/uber/h3-java/issues/23#issuecomment-417966410
  [4]: http://central.maven.org/maven2/com/uber/h3/
  [5]: https://stackoverflow.com/q/24572052/4625829
  [6]: https://play.google.com/store/apps/details?id=com.inkwired.droidinfo
  [7]: https://stackoverflow.com/q/41605726/4625829