package com.riskcare.app.ui.more;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\\\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B)\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t\u00a2\u0006\u0002\u0010\u000bJ\b\u0010\u0010\u001a\u00020\u0011H\u0016J@\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00072\u0006\u0010\u0019\u001a\u00020\u00112\b\b\u0002\u0010\u001a\u001a\u00020\u00172\f\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\n0\tH\u0002J\u0018\u0010\u001c\u001a\u00020\n2\u0006\u0010\u001d\u001a\u00020\u00022\u0006\u0010\u001e\u001a\u00020\u0011H\u0016J\u0018\u0010\u001f\u001a\u00020\u00022\u0006\u0010 \u001a\u00020!2\u0006\u0010\"\u001a\u00020\u0011H\u0016J(\u0010#\u001a\u00020\n2\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010$\u001a\u00020\u00112\u0006\u0010%\u001a\u00020\u00072\u0006\u0010&\u001a\u00020\u0007H\u0002J \u0010\'\u001a\u00020\n2\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010$\u001a\u00020\u00112\u0006\u0010%\u001a\u00020\u0007H\u0002J \u0010(\u001a\u00020\n2\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010)\u001a\u00020\u0005H\u0002J \u0010*\u001a\u00020+2\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010,\u001a\u00020\u0007H\u0002R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\rX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\rX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006-"}, d2 = {"Lcom/riskcare/app/ui/more/ProvisionAdapter;", "Landroidx/recyclerview/widget/RecyclerView$Adapter;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "items", "", "Lcom/riskcare/app/data/models/ProvisionEmployee;", "role", "", "onRefresh", "Lkotlin/Function0;", "", "(Ljava/util/List;Ljava/lang/String;Lkotlin/jvm/functions/Function0;)V", "isAdmin", "", "isHR", "isMgr", "getItemCount", "", "makeBtn", "Lcom/google/android/material/button/MaterialButton;", "ctx", "Landroid/content/Context;", "dp", "", "label", "color", "flex", "onClick", "onBindViewHolder", "h", "pos", "onCreateViewHolder", "p", "Landroid/view/ViewGroup;", "t", "showApproveDialog", "empId", "empName", "defaultAction", "showInitiateDialog", "showStatusDialog", "emp", "statusChip", "Landroid/widget/TextView;", "status", "app_debug"})
public final class ProvisionAdapter extends androidx.recyclerview.widget.RecyclerView.Adapter<androidx.recyclerview.widget.RecyclerView.ViewHolder> {
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.riskcare.app.data.models.ProvisionEmployee> items = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String role = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.jvm.functions.Function0<kotlin.Unit> onRefresh = null;
    private final boolean isHR = false;
    private final boolean isAdmin = false;
    private final boolean isMgr = false;
    
    public ProvisionAdapter(@org.jetbrains.annotations.NotNull()
    java.util.List<com.riskcare.app.data.models.ProvisionEmployee> items, @org.jetbrains.annotations.NotNull()
    java.lang.String role, @org.jetbrains.annotations.NotNull()
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
    
    private final com.google.android.material.button.MaterialButton makeBtn(android.content.Context ctx, float dp, java.lang.String label, int color, float flex, kotlin.jvm.functions.Function0<kotlin.Unit> onClick) {
        return null;
    }
    
    private final android.widget.TextView statusChip(android.content.Context ctx, float dp, java.lang.String status) {
        return null;
    }
    
    private final void showInitiateDialog(android.content.Context ctx, int empId, java.lang.String empName) {
    }
    
    private final void showApproveDialog(android.content.Context ctx, int empId, java.lang.String empName, java.lang.String defaultAction) {
    }
    
    private final void showStatusDialog(android.content.Context ctx, float dp, com.riskcare.app.data.models.ProvisionEmployee emp) {
    }
}