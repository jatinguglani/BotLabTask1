#include <jni.h>
#include <string>
#include <opencv2/opencv.hpp>
#include <android/bitmap.h>

using namespace cv;

extern "C"
JNIEXPORT void JNICALL
Java_world_tally_botlabtask2_MainActivity_processImage(JNIEnv *pEnv, jobject pObj, jobject pBitmap, jint pFilterType)
{
    AndroidBitmapInfo info;
    void *pixels;

    if (AndroidBitmap_getInfo(pEnv, pBitmap, &info) < 0) return;
    if (AndroidBitmap_lockPixels(pEnv, pBitmap, &pixels) < 0) return;

    Mat src(info.height, info.width, CV_8UC4, pixels);
    Mat dst;

    if (pFilterType == 0) {

        cvtColor(src, dst, COLOR_RGBA2GRAY);  // Grayscale
    } else if (pFilterType == 1) {

        cvtColor(src, dst, COLOR_RGBA2GRAY);
        Canny(dst, dst, 100, 200);  // Edge Detection
    }

    cvtColor(dst, src, COLOR_GRAY2RGBA);
    AndroidBitmap_unlockPixels(pEnv, pBitmap);
}