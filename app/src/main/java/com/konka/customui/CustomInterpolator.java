package com.konka.customui;

import android.view.animation.BounceInterpolator;

/**
 * Created by wangjie on 16-9-22.
 */

public class CustomInterpolator extends BounceInterpolator {
    public CustomInterpolator() {
    }

    private static float bounce(float t, float factor) {
        return t * t * factor;
    }

    public float getInterpolation(float t) {
        // _b(t) = t * t * 8
        // bs(t) = _b(t) for t < 0.3535
        // bs(t) = _b(t - 0.54719) + 0.7 for t < 0.7408
        // bs(t) = _b(t - 0.8526) + 0.9 for t < 0.9644
        // bs(t) = _b(t - 1.0435) + 0.95 for t <= 1.0
        // b(t) = bs(t * 1.1226)
        t *= 1.1226f;
        if (t < 0.3535f) return bounce(t, 16.00483346f);
        else if (t < 0.54719f) return bounce(t - 0.54719f, 39.983136606f) + 0.5f;
        else if (t < 0.7408f) return bounce(t - 0.54719f, 26.677457093f) + 0.5f;
        else if (t < 0.8526f) return bounce(t - 0.8526f, 64.003891437f) + 0.7f;
        else if (t < 0.9644f) return bounce(t - 0.8526f, 48.002918577f) + 0.7f;
        else if (t < 1.0435f) return bounce(t - 1.0435f, 63.9303415f) + 0.9f;
        else return bounce(t - 1.0435f, 15.982585375f) + 0.9f;
    }
}