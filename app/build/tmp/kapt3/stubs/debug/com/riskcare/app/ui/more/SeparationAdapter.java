package com.riskcare.app.ui.more;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B1\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000b\u00a2\u0006\u0002\u0010\rJe\u0010\u000e\u001a\u00020\f2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00072.\u0010\u0018\u001a*\b\u0001\u0012\u0004\u0012\u00020\u0007\u0012\u0006\u0012\u0004\u0018\u00010\u0007\u0012\u000e\u0012\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u001b0\u001a\u0012\u0006\u0012\u0004\u0018\u00010\u001c0\u0019H\u0002\u00a2\u0006\u0002\u0010\u001dJ\b\u0010\u001e\u001a\u00020\u0016H\u0016J\u0018\u0010\u001f\u001a\u00020\f2\u0006\u0010 \u001a\u00020\u00022\u0006\u0010!\u001a\u00020\u0016H\u0016J\u0018\u0010\"\u001a\u00020\u00022\u0006\u0010#\u001a\u00020$2\u0006\u0010%\u001a\u00020\u0016H\u0016R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006&"}, d2 = {"Lcom/riskcare/app/ui/more/SeparationAdapter;", "Landroidx/recyclerview/widget/RecyclerView$Adapter;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "items", "", "Lcom/riskcare/app/data/models/SeparationRecord;", "role", "", "session", "Lcom/riskcare/app/utils/SessionManager;", "onRefresh", "Lkotlin/Function0;", "", "(Ljava/util/List;Ljava/lang/String;Lcom/riskcare/app/utils/SessionManager;Lkotlin/jvm/functions/Function0;)V", "addApproveRejectButtons", "ctx", "Landroid/content/Context;", "dp", "", "parent", "Landroid/widget/LinearLayout;", "sepId", "", "level", "apiCall", "Lkotlin/Function3;", "Lkotlin/coroutines/Continuation;", "Lretrofit2/Response;", "", "(Landroid/content/Context;FLandroid/widget/LinearLayout;ILjava/lang/String;Lkotlin/jvm/functions/Function3;)V", "getItemCount", "onBindViewHolder", "h", "pos", "onCreateViewHolder", "p", "Landroid/view/ViewGroup;", "t", "app_debug"})
public final class SeparationAdapter extends androidx.recyclerview.widget.RecyclerView.Adapter<androidx.recyclerview.widget.RecyclerView.ViewHolder> {
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.riskcare.app.data.models.SeparationRecord> items = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String role = null;
    @org.jetbrains.annotations.NotNull()
    private final com.riskcare.app.utils.SessionManager session = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.jvm.functions.Function0<kotlin.Unit> onRefresh = null;
    
    public SeparationAdapter(@org.jetbrains.annotations.NotNull()
    java.util.List<com.riskcare.app.data.models.SeparationRecord> items, @org.jetbrains.annotations.NotNull()
    java.lang.String role, @org.jetbrains.annotations.NotNull()
    com.riskcare.app.utils.SessionManager session, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onRefresh) {
        super();
    }
    
    @java.lang.Override()
    public int getItemCount() {
        return 0;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public androidx.recyclerview.widget.RecyclerView.ViewHolder onCreateViewHolder(@org.jetbrains.annotations.NotNull()
    android.view.ViewGroup p, int t) {
        return null;
    }
    
    @java.lang.Override()
    public void onBindViewHolder(@org.jetbrains.annotations.NotNull()
    androidx.recyclerview.widget.RecyclerView.ViewHolder h, int pos) {
    }
    
    private final void addApproveRejectButtons(android.content.Context ctx, float dp, android.widget.LinearLayout parent, int sepId, java.lang.String level, kotlin.jvm.functions.Function3<? super java.lang.String, ? super java.lang.String, ? super kotlin.coroutines.Continuation<? super retrofit2.Response<?>>, ? extends java.lang.Object> apiCall) {
    }
}