#include <jni.h>
#include <string>


extern "C"
JNIEXPORT void JNICALL
Java_dev_mmodrzej_mso_NativeActivity_sortArrayJNI(JNIEnv *env, jobject thiz, jintArray arr, jsize size) {
    jboolean isCopy;
    jint tmp;
    jint *jarr = env->GetIntArrayElements(arr, &isCopy);
    for (int i = 0; i < size-1; i++) {
        for(int j = 0; j < size-i-1; j++) {
            if(jarr[j] > jarr[j+1]){
                tmp = jarr[j];
                jarr[j] = jarr[j+1];
                jarr[j+1] = tmp;
            }
        }
    }
    env->ReleaseIntArrayElements(arr, jarr, 0);
}