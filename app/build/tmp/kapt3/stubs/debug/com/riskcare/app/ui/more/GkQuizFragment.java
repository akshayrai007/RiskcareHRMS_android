package com.riskcare.app.ui.more;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0007\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0013\u001a\u00020\u0014H\u0002J$\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00182\b\u0010\u0019\u001a\u0004\u0018\u00010\u001a2\b\u0010\u001b\u001a\u0004\u0018\u00010\u001cH\u0016J\b\u0010\u001d\u001a\u00020\u001eH\u0016J\b\u0010\u001f\u001a\u00020\u001eH\u0016J\u0010\u0010 \u001a\u00020\u001e2\u0006\u0010!\u001a\u00020\u0006H\u0002J\"\u0010\"\u001a\u00020\u001e2\u0006\u0010!\u001a\u00020\u00062\u0006\u0010#\u001a\u00020$2\b\u0010%\u001a\u0004\u0018\u00010$H\u0002R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\nX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\f\u001a\u0004\u0018\u00010\rX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u000e\u001a\u0004\u0018\u00010\u000fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0010\u001a\u0004\u0018\u00010\u000fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0011\u001a\u0004\u0018\u00010\u000fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0012\u001a\u0004\u0018\u00010\u000fX\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006&"}, d2 = {"Lcom/riskcare/app/ui/more/GkQuizFragment;", "Landroidx/fragment/app/Fragment;", "()V", "countdownTimer", "Landroid/os/CountDownTimer;", "currentQuestion", "Lcom/riskcare/app/data/models/GkQuestion;", "optionsGroupRef", "Landroid/widget/LinearLayout;", "submitting", "", "timerActive", "timerBarRef", "Landroid/widget/ProgressBar;", "tvAboutRef", "Landroid/widget/TextView;", "tvResultRef", "tvStatsRef", "tvTimerRef", "dp", "", "onCreateView", "Landroid/view/View;", "inf", "Landroid/view/LayoutInflater;", "c", "Landroid/view/ViewGroup;", "s", "Landroid/os/Bundle;", "onDestroyView", "", "onPause", "startCountdown", "q", "submitAnswer", "answer", "", "reason", "app_debug"})
public final class GkQuizFragment extends androidx.fragment.app.Fragment {
    @org.jetbrains.annotations.Nullable()
    private com.riskcare.app.data.models.GkQuestion currentQuestion;
    private boolean timerActive = false;
    private boolean submitting = false;
    @org.jetbrains.annotations.Nullable()
    private android.os.CountDownTimer countdownTimer;
    @org.jetbrains.annotations.Nullable()
    private android.widget.LinearLayout optionsGroupRef;
    @org.jetbrains.annotations.Nullable()
    private android.widget.TextView tvTimerRef;
    @org.jetbrains.annotations.Nullable()
    private android.widget.TextView tvResultRef;
    @org.jetbrains.annotations.Nullable()
    private android.widget.TextView tvAboutRef;
    @org.jetbrains.annotations.Nullable()
    private android.widget.TextView tvStatsRef;
    @org.jetbrains.annotations.Nullable()
    private android.widget.ProgressBar timerBarRef;
    
    public GkQuizFragment() {
        super();
    }
    
    @java.lang.Override()
    public void onPause() {
    }
    
    @java.lang.Override()
    public void onDestroyView() {
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public android.view.View onCreateView(@org.jetbrains.annotations.NotNull()
    android.view.LayoutInflater inf, @org.jetbrains.annotations.Nullable()
    android.view.ViewGroup c, @org.jetbrains.annotations.Nullable()
    android.os.Bundle s) {
        return null;
    }
    
    private final void startCountdown(com.riskcare.app.data.models.GkQuestion q) {
    }
    
    private final void submitAnswer(com.riskcare.app.data.models.GkQuestion q, java.lang.String answer, java.lang.String reason) {
    }
    
    private final float dp() {
        return 0.0F;
    }
}