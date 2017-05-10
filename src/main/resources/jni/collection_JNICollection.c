#include <jni.h>
#include <stdio.h>
#include "collection_JNICollection.h"

JNIEXPORT jlong JNICALL Java_collection_JNICollection_allocate(JNIEnv *,
		jobject, jint size) {
	return (jlong) calloc(sizeof(int) * size);
}

JNIEXPORT void JNICALL Java_collection_JNICollection_enlarge
(JNIEnv *env, jobject this) {

	return;
}

JNIEXPORT jlong JNICALL Java_collection_JNICollection_free(JNIEnv *, jobject) {

	return 0;
}

JNIEXPORT jint JNICALL Java_collection_JNICollection_get(JNIEnv *, jobject,
		jint) {

	return 0;
}

JNIEXPORT jint JNICALL Java_collection_JNICollection_set(JNIEnv *, jobject,
		jint, jint) {

	return 0;
}

JNIEXPORT void JNICALL Java_collection_JNICollection_remove
(JNIEnv *, jobject, jint) {

	return;
}
