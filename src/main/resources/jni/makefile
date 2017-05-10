# makefile fount at http://www.bogotobogo.com/cplusplus/eclipse_CDT_JNI_MinGW_64bit.php

# Define a variables
CLASS_PATH = ../../../../target/classes
JAVA_PATH = /usr/lib/jvm/jdk1.8.0_121

# Debug: -g3=compile with extra debugg infos. -ggdbg3=include things like macro defenitions. -O0=turn off optimizations.
DEBUGFLAGS = -g3 -ggdb3 -O0
CFLAGS = $(DEBUGFLAGS)

# Define a virtual path for .class in the bin directory
vpath %.class $(CLASS_PATH)

all : libcollection.so

# $@ matches the target, $< matches the first dependency
libcollection.so : collection_JNICollection.o
	gcc $(CFLAGS) -W -shared -o $@ $<

# $@ matches the target, $< matches the first dependency
collection_JNICollection.o : collection_JNICollection.c collection_JNICollection.h
	gcc $(CFLAGS) -fPIC -I$(JAVA_PATH)/include -I$(JAVA_PATH)/include/linux -c $< -o $@

# $* matches the target filename without the extension
# manually this would be: javah -classpath ../bin HelloJNI
collection_JNICollection.h :
	javah -jni -classpath $(CLASS_PATH) collection.JNICollection

clean :
	rm -f collection_JNICollection.h collection_JNICollection.o libcollection.so