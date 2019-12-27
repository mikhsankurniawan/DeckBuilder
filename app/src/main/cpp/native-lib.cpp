#include <jni.h>

extern "C" JNIEXPORT jint JNICALL Java_com_example_tktplproject_AppAdapter_increment
        (JNIEnv * env, jobject obj, jint x) {
    return x+1;
}

extern "C" JNIEXPORT jint JNICALL Java_com_example_tktplproject_AppAdapter_decrement
        (JNIEnv * env, jobject obj, jint x) {
    return x-1;
}
