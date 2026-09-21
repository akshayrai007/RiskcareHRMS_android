package com.riskcare.app.ui.more;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000d\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0015\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0002J\u0012\u0010\u0011\u001a\u00020\u000e2\b\u0010\u0012\u001a\u0004\u0018\u00010\u0013H\u0016J$\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00172\b\u0010\u0018\u001a\u0004\u0018\u00010\u00192\b\u0010\u0012\u001a\u0004\u0018\u00010\u0013H\u0016J+\u0010\u001a\u001a\u00020\u000e2\u0006\u0010\u001b\u001a\u00020\u00072\f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u001e0\u001d2\u0006\u0010\u001f\u001a\u00020 H\u0016\u00a2\u0006\u0002\u0010!J\b\u0010\"\u001a\u00020\u000eH\u0002J\u0010\u0010#\u001a\u00020\u000e2\u0006\u0010$\u001a\u00020\u0010H\u0002R\u0016\u0010\u0003\u001a\n\u0012\u0004\u0012\u00020\u0005\u0018\u00010\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\b\u001a\u0004\u0018\u00010\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\n\u001a\u0004\u0018\u00010\u000bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0016\u0010\f\u001a\n\u0012\u0004\u0012\u00020\u0005\u0018\u00010\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006%"}, d2 = {"Lcom/riskcare/app/ui/more/ProfileFragment;", "Landroidx/fragment/app/Fragment;", "()V", "cropLauncher", "Landroidx/activity/result/ActivityResultLauncher;", "Landroid/content/Intent;", "empId", "", "imgViewRef", "Landroid/widget/ImageView;", "initialsViewRef", "Landroid/widget/TextView;", "photoLauncher", "launchCrop", "", "sourceUri", "Landroid/net/Uri;", "onCreate", "s", "Landroid/os/Bundle;", "onCreateView", "Landroid/view/View;", "i", "Landroid/view/LayoutInflater;", "c", "Landroid/view/ViewGroup;", "onRequestPermissionsResult", "req", "perms", "", "", "results", "", "(I[Ljava/lang/String;[I)V", "openPhotoPicker", "uploadPhoto", "uri", "app_debug"})
public final class ProfileFragment extends androidx.fragment.app.Fragment {
    @org.jetbrains.annotations.Nullable()
    private androidx.activity.result.ActivityResultLauncher<android.content.Intent> photoLauncher;
    @org.jetbrains.annotations.Nullable()
    private androidx.activity.result.ActivityResultLauncher<android.content.Intent> cropLauncher;
    @org.jetbrains.annotations.Nullable()
    private android.widget.ImageView imgViewRef;
    @org.jetbrains.annotations.Nullable()
    private android.widget.TextView initialsViewRef;
    private int empId = -1;
    
    public ProfileFragment() {
        super();
    }
    
    @java.lang.Override()
    public void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle s) {
    }
    
    private final void launchCrop(android.net.Uri sourceUri) {
    }
    
    private final void openPhotoPicker() {
    }
    
    @java.lang.Override()
    public void onRequestPermissionsResult(int req, @org.jetbrains.annotations.NotNull()
    java.lang.String[] perms, @org.jetbrains.annotations.NotNull()
    int[] results) {
    }
    
    private final void uploadPhoto(android.net.Uri uri) {
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public android.view.View onCreateView(@org.jetbrains.annotations.NotNull()
    android.view.LayoutInflater i, @org.jetbrains.annotations.Nullable()
    android.view.ViewGroup c, @org.jetbrains.annotations.Nullable()
    android.os.Bundle s) {
        return null;
    }
}