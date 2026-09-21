package com.riskcare.app.ui.more;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000`\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u001e\u001a\u00020\u001fH\u0002J$\u0010 \u001a\u00020\u00042\u0006\u0010!\u001a\u00020\"2\b\u0010#\u001a\u0004\u0018\u00010$2\b\u0010%\u001a\u0004\u0018\u00010&H\u0016J\b\u0010\'\u001a\u00020\u001fH\u0016J\u001a\u0010(\u001a\u00020\u001f2\u0006\u0010)\u001a\u00020\u00042\b\u0010*\u001a\u0004\u0018\u00010&H\u0016J\u0010\u0010+\u001a\u00020\u001f2\u0006\u0010,\u001a\u00020\u0006H\u0002R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0007\u001a\u00020\b8BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\t\u0010\nR\u000e\u0010\u000b\u001a\u00020\fX\u0082.\u00a2\u0006\u0002\n\u0000R\u0014\u0010\r\u001a\u00020\u00068BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\u000e\u0010\u000fR\u000e\u0010\u0010\u001a\u00020\u0011X\u0082.\u00a2\u0006\u0002\n\u0000R\u001b\u0010\u0012\u001a\u00020\u00138BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0016\u0010\u0017\u001a\u0004\b\u0014\u0010\u0015R\u000e\u0010\u0018\u001a\u00020\u0019X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u001bX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001c\u001a\u00020\u001bX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001d\u001a\u00020\u001bX\u0082.\u00a2\u0006\u0002\n\u0000\u00a8\u0006-"}, d2 = {"Lcom/riskcare/app/ui/more/ReimbursementFragment;", "Landroidx/fragment/app/Fragment;", "()V", "_view", "Landroid/view/View;", "activeTab", "", "canApprove", "", "getCanApprove", "()Z", "fabApply", "Lcom/google/android/material/floatingactionbutton/FloatingActionButton;", "role", "getRole", "()Ljava/lang/String;", "rvList", "Landroidx/recyclerview/widget/RecyclerView;", "session", "Lcom/riskcare/app/utils/SessionManager;", "getSession", "()Lcom/riskcare/app/utils/SessionManager;", "session$delegate", "Lkotlin/Lazy;", "swipeRefresh", "Landroidx/swiperefreshlayout/widget/SwipeRefreshLayout;", "tabMine", "Landroid/widget/TextView;", "tabPending", "tvEmpty", "loadList", "", "onCreateView", "i", "Landroid/view/LayoutInflater;", "c", "Landroid/view/ViewGroup;", "s", "Landroid/os/Bundle;", "onDestroyView", "onViewCreated", "view", "savedInstanceState", "switchTab", "tab", "app_debug"})
public final class ReimbursementFragment extends androidx.fragment.app.Fragment {
    @org.jetbrains.annotations.NotNull()
    private java.lang.String activeTab = "mine";
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy session$delegate = null;
    private androidx.recyclerview.widget.RecyclerView rvList;
    private android.widget.TextView tvEmpty;
    private androidx.swiperefreshlayout.widget.SwipeRefreshLayout swipeRefresh;
    private android.widget.TextView tabMine;
    private android.widget.TextView tabPending;
    private com.google.android.material.floatingactionbutton.FloatingActionButton fabApply;
    @org.jetbrains.annotations.Nullable()
    private android.view.View _view;
    
    public ReimbursementFragment() {
        super();
    }
    
    private final com.riskcare.app.utils.SessionManager getSession() {
        return null;
    }
    
    private final java.lang.String getRole() {
        return null;
    }
    
    private final boolean getCanApprove() {
        return false;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public android.view.View onCreateView(@org.jetbrains.annotations.NotNull()
    android.view.LayoutInflater i, @org.jetbrains.annotations.Nullable()
    android.view.ViewGroup c, @org.jetbrains.annotations.Nullable()
    android.os.Bundle s) {
        return null;
    }
    
    @java.lang.Override()
    public void onViewCreated(@org.jetbrains.annotations.NotNull()
    android.view.View view, @org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    private final void switchTab(java.lang.String tab) {
    }
    
    private final void loadList() {
    }
    
    @java.lang.Override()
    public void onDestroyView() {
    }
}