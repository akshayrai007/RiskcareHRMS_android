package com.riskcare.app.ui.more;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000t\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u0018H\u0002J\b\u0010\u001c\u001a\u00020\u001aH\u0002J$\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020 2\b\u0010!\u001a\u0004\u0018\u00010\"2\b\u0010#\u001a\u0004\u0018\u00010$H\u0016J\u001a\u0010%\u001a\u00020\u001a2\u0006\u0010&\u001a\u00020\u001e2\b\u0010\'\u001a\u0004\u0018\u00010$H\u0016J\b\u0010(\u001a\u00020\u001aH\u0002J\u0010\u0010)\u001a\u00020\u001a2\u0006\u0010*\u001a\u00020+H\u0002J\u0010\u0010,\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u0018H\u0002R,\u0010\u0003\u001a \u0012\u0004\u0012\u00020\u0005\u0012\u0016\u0012\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00050\u00070\u00060\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\b\u001a\u00020\t8BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u000e\u0010\f\u001a\u00020\rX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001c\u0010\u000e\u001a\u0010\u0012\f\u0012\n \u0011*\u0004\u0018\u00010\u00100\u00100\u000fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0005X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0005X\u0082\u000e\u00a2\u0006\u0002\n\u0000R \u0010\u0016\u001a\u0014\u0012\u0004\u0012\u00020\u0005\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00180\u00060\u0017X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006-"}, d2 = {"Lcom/riskcare/app/ui/more/MyDocumentsFragment;", "Landroidx/fragment/app/Fragment;", "()V", "DOC_CHECKLIST", "Ljava/util/LinkedHashMap;", "", "", "Lkotlin/Pair;", "api", "Lcom/riskcare/app/data/api/ApiService;", "getApi", "()Lcom/riskcare/app/data/api/ApiService;", "employeeId", "", "pickFileLauncher", "Landroidx/activity/result/ActivityResultLauncher;", "Landroid/content/Intent;", "kotlin.jvm.PlatformType", "session", "Lcom/riskcare/app/utils/SessionManager;", "uploadDocKey", "uploadDocLabel", "uploadedDocs", "", "Lcom/riskcare/app/data/models/EmpDocument;", "confirmDelete", "", "doc", "loadDocuments", "onCreateView", "Landroid/view/View;", "i", "Landroid/view/LayoutInflater;", "c", "Landroid/view/ViewGroup;", "s", "Landroid/os/Bundle;", "onViewCreated", "view", "savedInstanceState", "renderChecklist", "uploadFile", "uri", "Landroid/net/Uri;", "viewDocument", "app_debug"})
public final class MyDocumentsFragment extends androidx.fragment.app.Fragment {
    private com.riskcare.app.utils.SessionManager session;
    private int employeeId = 0;
    @org.jetbrains.annotations.NotNull()
    private java.lang.String uploadDocKey = "";
    @org.jetbrains.annotations.NotNull()
    private java.lang.String uploadDocLabel = "";
    @org.jetbrains.annotations.NotNull()
    private java.util.Map<java.lang.String, ? extends java.util.List<com.riskcare.app.data.models.EmpDocument>> uploadedDocs;
    @org.jetbrains.annotations.NotNull()
    private final java.util.LinkedHashMap<java.lang.String, java.util.List<kotlin.Pair<java.lang.String, java.lang.String>>> DOC_CHECKLIST = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.activity.result.ActivityResultLauncher<android.content.Intent> pickFileLauncher = null;
    
    public MyDocumentsFragment() {
        super();
    }
    
    private final com.riskcare.app.data.api.ApiService getApi() {
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
    
    private final void loadDocuments() {
    }
    
    private final void renderChecklist() {
    }
    
    private final void uploadFile(android.net.Uri uri) {
    }
    
    private final void viewDocument(com.riskcare.app.data.models.EmpDocument doc) {
    }
    
    private final void confirmDelete(com.riskcare.app.data.models.EmpDocument doc) {
    }
}