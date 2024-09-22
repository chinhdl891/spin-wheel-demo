package com.chinchin.spinwheel

import android.animation.TimeInterpolator
import android.view.animation.Interpolator
import kotlin.math.cos
import kotlin.math.sin

class CustomInterpolator : TimeInterpolator {
    override fun getInterpolation(input: Float): Float {
        // Sử dụng hàm toán học để mô phỏng sự thay đổi tốc độ theo các giai đoạn khác nhau
        return when {
            input <= 0.125 -> input * 4f    // Tăng chậm ở 1/8 đầu tiên
            input <= 0.375 -> 0.5f + (input - 0.125f) * 4f   // Nhanh dần đến cực nhanh
            input <= 0.625 -> 1f           // Cực nhanh ở giữa
            input <= 0.875 -> 1f - (input - 0.625f) * 4f  // Giảm tốc từ cực nhanh
            else -> (1f - input) * 4f      // Chậm dần khi đến cuối
        }
    }
}
