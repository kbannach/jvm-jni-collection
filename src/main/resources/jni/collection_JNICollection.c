#include <jni.h>
#include <stdio.h>
#include <stdlib.h>
#include "collection_JNICollection.h"

// private methods

int getSize(JNIEnv *env, jobject this) {
	jclass cls = (*env)->GetObjectClass(env, this);
	jfieldID iField = (*env)->GetFieldID(env, cls, "size", "I");
	jint i = (*env)->GetIntField(env, this, iField);
	return (int) i;
}

int* getPointer(JNIEnv *env, jobject this) {
	jclass cls = (*env)->GetObjectClass(env, this);
	jfieldID iField = (*env)->GetFieldID(env, cls, "pointer", "J");
	jlong i = (*env)->GetLongField(env, this, iField);
	return (int*) i;
}

// methods used in JNI

JNIEXPORT jlong JNICALL Java_collection_JNICollection_allocate(JNIEnv *env,
		jobject this, jint size) {
	return (jlong) calloc(size, sizeof(int));
}


JNIEXPORT jlong JNICALL Java_collection_JNICollection_enlarge(JNIEnv *env,
		jobject this) {
	int size = getSize(env, this);
	int* newTab = (int*) calloc(size * 5 / 3, sizeof(int));
	int* tab = getPointer(env, this);

	int i;
	for(i=0; i<size;i++){
		newTab[i] = tab[i];
	}

	free(tab);
	return (jlong) newTab;
}

JNIEXPORT void JNICALL Java_collection_JNICollection_free(JNIEnv *env,
		jobject this) {
	free(getPointer(env, this));
}

JNIEXPORT jint JNICALL Java_collection_JNICollection_get(JNIEnv *env,
		jobject this, jint index) {
	return getPointer(env, this)[(int) index];
}

JNIEXPORT jint JNICALL Java_collection_JNICollection_set(JNIEnv *env,
		jobject this, jint element, jint index) {
	int idx = (int) index;
	int* tab = getPointer(env, this);
	int oldVal = tab[idx];
	tab[idx] = (int) element;
	return oldVal;
}
