#include <jni.h>
#include <string>
#include <opencv2/core.hpp>
#include <opencv2/imgcodecs.hpp>
#include <opencv2/highgui.hpp>
#include <android/log.h>

#define LOG_TAG "OpenCV"

extern "C"
JNIEXPORT jboolean JNICALL
Java_world_tally_botlabtask2_MainActivity_IsOpenCVInitialized (JNIEnv *env, jobject thiz)
{
    // In OpenCV 4.x and later, explicit initialization is often not required
    // calling basic OpenCV functions like cv::Mat is enough to check if OpenCV is correctly linked.
    try {

        cv::Mat testMat;
        testMat.create(10, 10, CV_8UC1);
        return true;

    } catch (...) {

        return false;
    }
}