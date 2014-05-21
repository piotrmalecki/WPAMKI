LOCAL_PATH := $(call my-dir)
 
include $(CLEAR_VARS)
 
LOCAL_MODULE     := echoprint-jni
 
LOCAL_SRC_FILES  := AndroidCodegen.cpp \
            /codegen/src/Codegen.cpp \
            /codegen/src/Whitening.cpp \
            /codegen/src/SubbandAnalysis.cpp \
            /codegen/src/MatrixUtility.cpp \
            /codegen/src/Fingerprint.cpp \
            /codegen/src/Base64.cpp \
            /codegen/src/AudioStreamInput.cpp \
            /codegen/src/AudioBufferInput.cpp

LOCAL_LDLIBS     := -llog -lz

LOCAL_C_INCLUDES := jni/codegen/src \
            jni/boost/boost

LOCAL_CPPFLAGS   += -fexceptions

include $(BUILD_SHARED_LIBRARY)

