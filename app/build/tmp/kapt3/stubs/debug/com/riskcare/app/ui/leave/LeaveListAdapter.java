package com.riskcare.app.ui.leave;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\f\u0012\b\u0012\u00060\u0002R\u00020\u00000\u0001:\u0001\u001dB5\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007\u0012\b\b\u0002\u0010\b\u001a\u00020\u0007\u0012\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\n\u00a2\u0006\u0002\u0010\fJ\u0018\u0010\r\u001a\u00020\u000b2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0002J \u0010\u0012\u001a\u00020\u000b2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0013\u001a\u00020\u0014H\u0002J\b\u0010\u0015\u001a\u00020\u0011H\u0016J\u001c\u0010\u0016\u001a\u00020\u000b2\n\u0010\u0017\u001a\u00060\u0002R\u00020\u00002\u0006\u0010\u0018\u001a\u00020\u0011H\u0016J\u001c\u0010\u0019\u001a\u00060\u0002R\u00020\u00002\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u0011H\u0016R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001e"}, d2 = {"Lcom/riskcare/app/ui/leave/LeaveListAdapter;", "Landroidx/recyclerview/widget/RecyclerView$Adapter;", "Lcom/riskcare/app/ui/leave/LeaveListAdapter$VH;", "items", "", "Lcom/riskcare/app/data/models/LeaveApplication;", "showName", "", "showAction", "onAction", "Lkotlin/Function0;", "", "(Ljava/util/List;ZZLkotlin/jvm/functions/Function0;)V", "cancelLeave", "ctx", "Landroid/content/Context;", "id", "", "doAction", "action", "", "getItemCount", "onBindViewHolder", "h", "pos", "onCreateViewHolder", "p", "Landroid/view/ViewGroup;", "t", "VH", "app_debug"})
public final class LeaveListAdapter extends androidx.recyclerview.widget.RecyclerView.Adapter<com.riskcare.app.ui.leave.LeaveListAdapter.VH> {
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.riskcare.app.data.models.LeaveApplication> items = null;
    private final boolean showName = false;
    private final boolean showAction = false;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.jvm.functions.Function0<kotlin.Unit> onAction = null;
    
    public LeaveListAdapter(@org.jetbrains.annotations.NotNull()
    java.util.List<com.riskcare.app.data.models.LeaveApplication> items, boolean showName, boolean showAction, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onAction) {
        super();
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public com.riskcare.app.ui.leave.LeaveListAdapter.VH onCreateViewHolder(@org.jetbrains.annotations.NotNull()
    android.view.ViewGroup p, int t) {
        return null;
    }
    
    @java.lang.Override()
    public int getItemCount() {
        return 0;
    }
    
    @java.lang.Override()
    public void onBindViewHolder(@org.jetbrains.annotations.NotNull()
    com.riskcare.app.ui.leave.LeaveListAdapter.VH h, int pos) {
    }
    
    private final void doAction(android.content.Context ctx, int id, java.lang.String action) {
    }
    
    private final void cancelLeave(android.content.Context ctx, int id) {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0086\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\u0007"}, d2 = {"Lcom/riskcare/app/ui/leave/LeaveListAdapter$VH;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "root", "Landroid/widget/FrameLayout;", "(Lcom/riskcare/app/ui/leave/LeaveListAdapter;Landroid/widget/FrameLayout;)V", "getRoot", "()Landroid/widget/FrameLayout;", "app_debug"})
    public final class VH extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
        @org.jetbrains.annotations.NotNull()
        private final android.widget.FrameLayout root = null;
        
        public VH(@org.jetbrains.annotations.NotNull()
        android.widget.FrameLayout root) {
            super(null);
        }
        
        @org.jetbrains.annotations.NotNull()
        public final android.widget.FrameLayout getRoot() {
            return null;
        }
    }
}