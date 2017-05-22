# jvm-jni-collection

JNI training. The goal is to create a collection (java.util.List) which uses a JVM native memory block.

## Details

Java code uses shared C library (located in ${project root}/jni) which creates and manages an integer array in JVM native memory. That way it is not analyzed by GC and free of garbage collection processes (like stop-the-world or moving objects to "older" sections od Java heap).

## How to run

You have to specify a path to your Java installation directory in a jni/java_path.txt file. It is used in makefile to specify include paths.
After doing this run ` mvn test ` to execute JUnit tests.
