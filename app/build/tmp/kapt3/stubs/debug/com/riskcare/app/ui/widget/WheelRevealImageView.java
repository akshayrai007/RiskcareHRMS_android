package com.riskcare.app.ui.widget;

/**
 * Reveals its image using a PowerPoint-style "Wheel" effect.
 * Spokes sweep in one by one (4 spokes = 4 segments of 90°) until fully revealed.
 *
 * Usage in code:
 *  wheelView.startWheelReveal(durationMs = 900L) {
 *      // optional: called when reveal is complete
 *  }
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000V\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B%\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\b\u0010\u001d\u001a\u00020\u001eH\u0014J\u0010\u0010\u001f\u001a\u00020\u001e2\u0006\u0010 \u001a\u00020\u0012H\u0014J\"\u0010!\u001a\u00020\u001e2\b\b\u0002\u0010\"\u001a\u00020#2\u0010\b\u0002\u0010$\u001a\n\u0012\u0004\u0012\u00020\u001e\u0018\u00010%R\u0010\u0010\t\u001a\u0004\u0018\u00010\nX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001b\u0010\u000b\u001a\u00020\f8BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\u000f\u0010\u0010\u001a\u0004\b\r\u0010\u000eR\u001b\u0010\u0011\u001a\u00020\u00128BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0015\u0010\u0010\u001a\u0004\b\u0013\u0010\u0014R\u000e\u0010\u0016\u001a\u00020\u0017X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u0019X\u0082D\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u0007X\u0082D\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001b\u001a\u00020\u0019X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001c\u001a\u00020\u0017X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006&"}, d2 = {"Lcom/riskcare/app/ui/widget/WheelRevealImageView;", "Landroidx/appcompat/widget/AppCompatImageView;", "context", "Landroid/content/Context;", "attrs", "Landroid/util/AttributeSet;", "defStyleAttr", "", "(Landroid/content/Context;Landroid/util/AttributeSet;I)V", "animatorJob", "Landroid/animation/ValueAnimator;", "maskBitmap", "Landroid/graphics/Bitmap;", "getMaskBitmap", "()Landroid/graphics/Bitmap;", "maskBitmap$delegate", "Lkotlin/Lazy;", "maskCanvas", "Landroid/graphics/Canvas;", "getMaskCanvas", "()Landroid/graphics/Canvas;", "maskCanvas$delegate", "maskPaint", "Landroid/graphics/Paint;", "segmentAngle", "", "spokeCount", "sweepAngle", "sweepPaint", "onDetachedFromWindow", "", "onDraw", "canvas", "startWheelReveal", "durationMs", "", "onDone", "Lkotlin/Function0;", "app_debug"})
public final class WheelRevealImageView extends androidx.appcompat.widget.AppCompatImageView {
    @org.jetbrains.annotations.NotNull()
    private final android.graphics.Paint maskPaint = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy maskBitmap$delegate = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy maskCanvas$delegate = null;
    @org.jetbrains.annotations.NotNull()
    private final android.graphics.Paint sweepPaint = null;
    private float sweepAngle = 0.0F;
    private final int spokeCount = 4;
    private final float segmentAngle = 0.0F;
    @org.jetbrains.annotations.Nullable()
    private android.animation.ValueAnimator animatorJob;
    
    @kotlin.jvm.JvmOverloads()
    public WheelRevealImageView(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.Nullable()
    android.util.AttributeSet attrs, int defStyleAttr) {
        super(null);
    }
    
    private final android.graphics.Bitmap getMaskBitmap() {
        return null;
    }
    
    private final android.graphics.Canvas getMaskCanvas() {
        return null;
    }
    
    @java.lang.Override()
    protected void onDraw(@org.jetbrains.annotations.NotNull()
    android.graphics.Canvas canvas) {
    }
    
    /**
     * Kick off the wheel reveal animation.
     * @param durationMs  total duration of the reveal (default 800ms)
     * @param onDone      optional callback when fully revealed
     */
    public final void startWheelReveal(long durationMs, @org.jetbrains.annotations.Nullable()
    kotlin.jvm.functions.Function0<kotlin.Unit> onDone) {
    }
    
    @java.lang.Override()
    protected void onDetachedFromWindow() {
    }
    
    @kotlin.jvm.JvmOverloads()
    public WheelRevealImageView(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super(null);
    }
    
    @kotlin.jvm.JvmOverloads()
    public WheelRevealImageView(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.Nullable()
    android.util.AttributeSet attrs) {
        super(null);
    }
}