package com.riskcare.app.ui.leave;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\n\u001a\u00020\u000bH\u0002J\b\u0010\f\u001a\u00020\u000bH\u0002J\b\u0010\r\u001a\u00020\u000bH\u0002J\b\u0010\u000e\u001a\u00020\u000bH\u0002J\b\u0010\u000f\u001a\u00020\u000bH\u0002J\b\u0010\u0010\u001a\u00020\u000bH\u0002J$\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00142\b\u0010\u0015\u001a\u0004\u0018\u00010\u00162\b\u0010\u0017\u001a\u0004\u0018\u00010\u0018H\u0016J\b\u0010\u0019\u001a\u00020\u000bH\u0016J\b\u0010\u001a\u001a\u00020\u000bH\u0016J\u001a\u0010\u001b\u001a\u00020\u000b2\u0006\u0010\u001c\u001a\u00020\u00122\b\u0010\u001d\u001a\u0004\u0018\u00010\u0018H\u0016J\b\u0010\u001e\u001a\u00020\u000bH\u0002J\u0018\u0010\u001f\u001a\u00020\u000b2\u0006\u0010 \u001a\u00020!2\u0006\u0010\"\u001a\u00020#H\u0002J\u0010\u0010$\u001a\u00020\u000b2\u0006\u0010%\u001a\u00020\u0006H\u0002R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0007\u001a\u00020\u00048BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\b\u0010\t\u00a8\u0006&"}, d2 = {"Lcom/riskcare/app/ui/leave/LeaveFragment;", "Landroidx/fragment/app/Fragment;", "()V", "_b", "Lcom/riskcare/app/databinding/FragmentLeaveBinding;", "activeTab", "", "binding", "getBinding", "()Lcom/riskcare/app/databinding/FragmentLeaveBinding;", "loadAllLeaves", "", "loadApplications", "loadBalance", "loadCompOffBalance", "loadCompOffCredits", "loadLeaveSummary", "onCreateView", "Landroid/view/View;", "i", "Landroid/view/LayoutInflater;", "c", "Landroid/view/ViewGroup;", "s", "Landroid/os/Bundle;", "onDestroyView", "onResume", "onViewCreated", "view", "savedInstanceState", "setupLeaveTransactionsUi", "showEmployeeLeaveDetail", "r", "Lcom/riskcare/app/data/models/LeaveSummaryRow;", "year", "", "switchTab", "tab", "app_debug"})
public final class LeaveFragment extends androidx.fragment.app.Fragment {
    @org.jetbrains.annotations.Nullable()
    private com.riskcare.app.databinding.FragmentLeaveBinding _b;
    @org.jetbrains.annotations.NotNull()
    private java.lang.String activeTab = "leave";
    
    public LeaveFragment() {
        super();
    }
    
    private final com.riskcare.app.databinding.FragmentLeaveBinding getBinding() {
        return null;
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
    
    private final void loadAllLeaves() {
    }
    
    private final void loadLeaveSummary() {
    }
    
    private final void showEmployeeLeaveDetail(com.riskcare.app.data.models.LeaveSummaryRow r, int year) {
    }
    
    private final void setupLeaveTransactionsUi() {
    }
    
    private final void loadBalance() {
    }
    
    private final void loadApplications() {
    }
    
    private final void loadCompOffBalance() {
    }
    
    private final void loadCompOffCredits() {
    }
    
    @java.lang.Override()
    public void onResume() {
    }
    
    @java.lang.Override()
    public void onDestroyView() {
    }
}