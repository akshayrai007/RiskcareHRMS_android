package com.riskcare.app.ui.more;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000J\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\u0010\u0000\n\u0002\b\u0004\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\u001aE\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052(\u0010\u0006\u001a$\b\u0001\u0012\u0016\u0012\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u00050\t0\b\u0012\u0006\u0012\u0004\u0018\u00010\u000b0\u0007\u00a2\u0006\u0002\u0010\f\u001a \u0010\r\u001a\u00020\n2\u0006\u0010\u000e\u001a\u00020\u00052\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0002\u001a\u0012\u0010\u0013\u001a\u00020\u00052\b\u0010\u0014\u001a\u0004\u0018\u00010\u0005H\u0002\u001a\u0010\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0010H\u0002\u001a2\u0010\u0018\u001a\u00020\u0019*\u00020\u00192\b\b\u0002\u0010\u001a\u001a\u00020\u00102\b\b\u0002\u0010\u001b\u001a\u00020\n2\b\b\u0002\u0010\u001c\u001a\u00020\u00102\b\b\u0002\u0010\u001d\u001a\u00020\n\u00a8\u0006\u001e"}, d2 = {"approvalConfirm", "", "ctx", "Landroid/content/Context;", "title", "", "onConfirm", "Lkotlin/Function1;", "Lkotlin/coroutines/Continuation;", "Lkotlin/Pair;", "", "", "(Landroid/content/Context;Ljava/lang/String;Lkotlin/jvm/functions/Function1;)V", "isSeparationMyTurn", "role", "userId", "", "sep", "Lcom/riskcare/app/data/models/SeparationRecord;", "teamTodayBucket", "status", "teamTodayPill", "Landroid/graphics/drawable/GradientDrawable;", "color", "copyAnniv", "Lcom/riskcare/app/data/models/AnniversaryRecord;", "likeCount", "iLiked", "wishCount", "iWished", "app_debug"})
public final class MoreFragmentKt {
    
    @org.jetbrains.annotations.NotNull()
    public static final com.riskcare.app.data.models.AnniversaryRecord copyAnniv(@org.jetbrains.annotations.NotNull()
    com.riskcare.app.data.models.AnniversaryRecord $this$copyAnniv, int likeCount, boolean iLiked, int wishCount, boolean iWished) {
        return null;
    }
    
    public static final void approvalConfirm(@org.jetbrains.annotations.NotNull()
    android.content.Context ctx, @org.jetbrains.annotations.NotNull()
    java.lang.String title, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super kotlin.coroutines.Continuation<? super kotlin.Pair<java.lang.Boolean, java.lang.String>>, ? extends java.lang.Object> onConfirm) {
    }
    
    private static final android.graphics.drawable.GradientDrawable teamTodayPill(int color) {
        return null;
    }
    
    private static final java.lang.String teamTodayBucket(java.lang.String status) {
        return null;
    }
    
    private static final boolean isSeparationMyTurn(java.lang.String role, int userId, com.riskcare.app.data.models.SeparationRecord sep) {
        return false;
    }
}