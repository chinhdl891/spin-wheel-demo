package com.chinchin.spinwheel

import android.animation.Animator
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.animation.Interpolator
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin
import kotlin.random.Random

class RotatingCircleView(context: Context, attrs: AttributeSet?) : View(context, attrs) {

    private var rotationAngle: Float = 0f
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        textAlign = Paint.Align.CENTER
        textSize = 30f
        typeface = Typeface.DEFAULT_BOLD
    }
    private var items: MutableList<ItemWheel> = mutableListOf()
    val wheelBitmap: Bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.ic_wheel)
    val paintBackGround = Paint()

    init {
        // Xóa khởi tạo mặc định nếu setItems luôn được gọi
        paintBackGround.setColor(Color.parseColor("#ababab"))
    }

    fun setItems(newItems: List<ItemWheel>) {
        items.clear()
        items.addAll(newItems)
        invalidate() // Redraw với các item mới
    }

    fun startRotation() {

        val totalDegrees = Random.nextInt(1800, 7200).toFloat()
        val animator = ValueAnimator.ofFloat(0f, totalDegrees)
        animator.duration = 5000 // Thời gian quay 5 giây
        animator.addUpdateListener { animation ->
            rotationAngle = animation.animatedValue as Float % 360
            invalidate() // Yêu cầu vẽ lại giao diện
        }
        animator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationEnd(animation: Animator) {
                updateActiveSegment() // Cập nhật phân đoạn tại góc 0 độ
            }

            override fun onAnimationStart(animation: Animator) {}
            override fun onAnimationCancel(animation: Animator) {}
            override fun onAnimationRepeat(animation: Animator) {}
        })
        animator.start() // Bắt đầu chạy animation
    }



    private fun updateActiveSegment() {
        val segmentAngle = 360f / items.size  // Tính góc mỗi phân đoạn
        val adjustedAngle = rotationAngle % 360 // Xác định góc hiện tại của hình quay
        val segmentIndex =
            ((360 - adjustedAngle) / segmentAngle).toInt() % items.size  // Tính chỉ số của phân đoạn tại góc 0 độ
        val activeItem = items[segmentIndex]  // Lấy item tương ứng với chỉ số đó
        Log.d(
            "ActiveItem",
            "Item at 0 degrees: ${activeItem.name}"
        )  // Log ra tên của item tại góc 0 độ
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val centerX = width / 2f
        val centerY = height / 2f
        val radius = min(centerX, centerY) * 0.9f
        val segmentAngle = 360f / items.size

        // Vẽ nền tròn của vòng quay
        canvas.drawCircle(centerX, centerY, radius + radius * 0.05f, paintBackGround)

        items.forEachIndexed { index, item ->
            val startAngle = segmentAngle * index + rotationAngle
            val sweepAngle = segmentAngle
            paint.color = item.colorBackground

            val arcRect = RectF(centerX - radius, centerY - radius, centerX + radius, centerY + radius)
            canvas.drawArc(arcRect, startAngle, sweepAngle, true, paint)

            // Tính toán tọa độ vẽ chữ
            textPaint.color = item.colorText
            val textAngle = Math.toRadians((startAngle + sweepAngle / 2).toDouble()).toFloat()
            val textRadius = radius * 0.7f // Điều chỉnh bán kính để chữ nằm bên trong hình tròn
            val textX = centerX + textRadius * cos(textAngle)
            val textY = centerY + textRadius * sin(textAngle)

            // Tính chiều rộng và chiều cao của text
            val textWidth = textPaint.measureText(item.name)
            val textHeight = textPaint.fontMetrics.run { descent - ascent }

            // Căn giữa text
            canvas.save()
            canvas.translate(textX, textY)
            canvas.rotate((startAngle + sweepAngle / 2), 0f, 0f) // Xoay text theo góc
            canvas.drawText(item.name, -textWidth / 2, -textPaint.fontMetrics.ascent / 2, textPaint) // Căn giữa theo chiều cao và chiều ngang
            canvas.restore()
        }

        // Vẽ ảnh ở giữa vòng quay
        val imageX = centerX - wheelBitmap.width / 2f
        val imageY = centerY - wheelBitmap.height / 2f
        canvas.drawBitmap(wheelBitmap, imageX, imageY, null)
    }

}







