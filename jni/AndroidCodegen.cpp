#include <android/log.h>
#include <string.h>
#include <jni.h>
#include <com_renatocunha_echoprint_Codegen.h>
#include <codegen/src/Codegen.h>

JNIEXPORT jstring JNICALL Java_com_renatocunha_echoprint_Codegen_codegen (JNIEnv *env, jobject thiz, jfloatArray pcmData, jint numSamples) {
    // get the contents of the java array as native floats
    float *data = (float *)env->GetFloatArrayElements(pcmData, 0);

    // invoke codegen
    Codegen c = Codegen(data, (unsigned int)numSamples, 0);
    const char *code = c.getCodeString().c_str();

    // release the native array
    env->ReleaseFloatArrayElements(pcmData, data, 0);

    return env->NewStringUTF(code);
}

