package com.riskcare.app.ui.chat;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u0000 \u001a2\u0012\u0012\u0004\u0012\u00020\u0002\u0012\b\u0012\u00060\u0003R\u00020\u00000\u0001:\u0002\u001a\u001bB5\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0012\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\b0\u0007\u0012\u0012\u0010\t\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\b0\u0007\u00a2\u0006\u0002\u0010\nJ\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0002J\u0012\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0010H\u0002J\u0012\u0010\u0012\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0010H\u0002J\u001c\u0010\u0013\u001a\u00020\b2\n\u0010\u0014\u001a\u00060\u0003R\u00020\u00002\u0006\u0010\u0015\u001a\u00020\u0005H\u0016J\u001c\u0010\u0016\u001a\u00060\u0003R\u00020\u00002\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u0005H\u0016R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\b0\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\t\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\b0\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001c"}, d2 = {"Lcom/riskcare/app/ui/chat/ChatGroupAdapter;", "Landroidx/recyclerview/widget/ListAdapter;", "Lcom/riskcare/app/data/models/ChatGroup;", "Lcom/riskcare/app/ui/chat/ChatGroupAdapter$VH;", "myId", "", "onClick", "Lkotlin/Function1;", "", "onLongClick", "(ILkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function1;)V", "buildRow", "Landroid/widget/LinearLayout;", "ctx", "Landroid/content/Context;", "formatLastSeen", "", "iso", "formatTime", "onBindViewHolder", "holder", "position", "onCreateViewHolder", "parent", "Landroid/view/ViewGroup;", "viewType", "Companion", "VH", "app_debug"})
public final class ChatGroupAdapter extends androidx.recyclerview.widget.ListAdapter<com.riskcare.app.data.models.ChatGroup, com.riskcare.app.ui.chat.ChatGroupAdapter.VH> {
    private final int myId = 0;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.jvm.functions.Function1<com.riskcare.app.data.models.ChatGroup, kotlin.Unit> onClick = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.jvm.functions.Function1<com.riskcare.app.data.models.ChatGroup, kotlin.Unit> onLongClick = null;
    @org.jetbrains.annotations.NotNull()
    private static final androidx.recyclerview.widget.DiffUtil.ItemCallback<com.riskcare.app.data.models.ChatGroup> DIFF = null;
    private static final int WA_TEAL = -15561602;
    private static final int WA_GREEN = -14298266;
    private static final int WA_WHITE = -1;
    private static final int WA_LIGHT_GRAY = -986379;
    private static final int WA_GRAY = -10061951;
    private static final int WA_TEXT = -15656159;
    private static final int WA_DIVIDER = -1446417;
    private static final int WA_ONLINE_GREEN = -14298266;
    private static final int WA_GROUP_PURPLE = -8630785;
    @org.jetbrains.annotations.NotNull()
    public static final com.riskcare.app.ui.chat.ChatGroupAdapter.Companion Companion = null;
    
    public ChatGroupAdapter(int myId, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super com.riskcare.app.data.models.ChatGroup, kotlin.Unit> onClick, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super com.riskcare.app.data.models.ChatGroup, kotlin.Unit> onLongClick) {
        super(null);
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public com.riskcare.app.ui.chat.ChatGroupAdapter.VH onCreateViewHolder(@org.jetbrains.annotations.NotNull()
    android.view.ViewGroup parent, int viewType) {
        return null;
    }
    
    @java.lang.Override()
    public void onBindViewHolder(@org.jetbrains.annotations.NotNull()
    com.riskcare.app.ui.chat.ChatGroupAdapter.VH holder, int position) {
    }
    
    private final android.widget.LinearLayout buildRow(android.content.Context ctx) {
        return null;
    }
    
    private final java.lang.String formatTime(java.lang.String iso) {
        return null;
    }
    
    private final java.lang.String formatLastSeen(java.lang.String iso) {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0013\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u0017\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0014\u0010\b\u001a\u00020\tX\u0086D\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0014\u0010\f\u001a\u00020\tX\u0086D\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000bR\u0014\u0010\u000e\u001a\u00020\tX\u0086D\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u000bR\u0014\u0010\u0010\u001a\u00020\tX\u0086D\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u000bR\u0014\u0010\u0012\u001a\u00020\tX\u0086D\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u000bR\u0014\u0010\u0014\u001a\u00020\tX\u0086D\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u000bR\u0014\u0010\u0016\u001a\u00020\tX\u0086D\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u000bR\u0014\u0010\u0018\u001a\u00020\tX\u0086D\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u000bR\u0014\u0010\u001a\u001a\u00020\tX\u0086D\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u000b\u00a8\u0006\u001c"}, d2 = {"Lcom/riskcare/app/ui/chat/ChatGroupAdapter$Companion;", "", "()V", "DIFF", "Landroidx/recyclerview/widget/DiffUtil$ItemCallback;", "Lcom/riskcare/app/data/models/ChatGroup;", "getDIFF", "()Landroidx/recyclerview/widget/DiffUtil$ItemCallback;", "WA_DIVIDER", "", "getWA_DIVIDER", "()I", "WA_GRAY", "getWA_GRAY", "WA_GREEN", "getWA_GREEN", "WA_GROUP_PURPLE", "getWA_GROUP_PURPLE", "WA_LIGHT_GRAY", "getWA_LIGHT_GRAY", "WA_ONLINE_GREEN", "getWA_ONLINE_GREEN", "WA_TEAL", "getWA_TEAL", "WA_TEXT", "getWA_TEXT", "WA_WHITE", "getWA_WHITE", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final androidx.recyclerview.widget.DiffUtil.ItemCallback<com.riskcare.app.data.models.ChatGroup> getDIFF() {
            return null;
        }
        
        public final int getWA_TEAL() {
            return 0;
        }
        
        public final int getWA_GREEN() {
            return 0;
        }
        
        public final int getWA_WHITE() {
            return 0;
        }
        
        public final int getWA_LIGHT_GRAY() {
            return 0;
        }
        
        public final int getWA_GRAY() {
            return 0;
        }
        
        public final int getWA_TEXT() {
            return 0;
        }
        
        public final int getWA_DIVIDER() {
            return 0;
        }
        
        public final int getWA_ONLINE_GREEN() {
            return 0;
        }
        
        public final int getWA_GROUP_PURPLE() {
            return 0;
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\r\b\u0086\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004R\u0011\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\t\u001a\u00020\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\r\u001a\u00020\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\u0011\u001a\u00020\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\fR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0011\u0010\u0015\u001a\u00020\u0016\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u0011\u0010\u0019\u001a\u00020\u0016\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0018R\u0011\u0010\u001b\u001a\u00020\u0016\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u0018R\u0011\u0010\u001d\u001a\u00020\u0016\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u0018R\u0011\u0010\u001f\u001a\u00020\u0016\u00a2\u0006\b\n\u0000\u001a\u0004\b \u0010\u0018R\u0011\u0010!\u001a\u00020\u0016\u00a2\u0006\b\n\u0000\u001a\u0004\b\"\u0010\u0018\u00a8\u0006#"}, d2 = {"Lcom/riskcare/app/ui/chat/ChatGroupAdapter$VH;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "root", "Landroid/widget/LinearLayout;", "(Lcom/riskcare/app/ui/chat/ChatGroupAdapter;Landroid/widget/LinearLayout;)V", "avatarFrame", "Landroid/widget/FrameLayout;", "getAvatarFrame", "()Landroid/widget/FrameLayout;", "divider", "Landroid/view/View;", "getDivider", "()Landroid/view/View;", "ivPhoto", "Landroid/widget/ImageView;", "getIvPhoto", "()Landroid/widget/ImageView;", "onlineDot", "getOnlineDot", "getRoot", "()Landroid/widget/LinearLayout;", "tvAvatar", "Landroid/widget/TextView;", "getTvAvatar", "()Landroid/widget/TextView;", "tvBadge", "getTvBadge", "tvMuted", "getTvMuted", "tvName", "getTvName", "tvSub", "getTvSub", "tvTime", "getTvTime", "app_debug"})
    public final class VH extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
        @org.jetbrains.annotations.NotNull()
        private final android.widget.LinearLayout root = null;
        @org.jetbrains.annotations.NotNull()
        private final android.widget.FrameLayout avatarFrame = null;
        @org.jetbrains.annotations.NotNull()
        private final android.widget.TextView tvAvatar = null;
        @org.jetbrains.annotations.NotNull()
        private final android.widget.ImageView ivPhoto = null;
        @org.jetbrains.annotations.NotNull()
        private final android.view.View onlineDot = null;
        @org.jetbrains.annotations.NotNull()
        private final android.widget.TextView tvName = null;
        @org.jetbrains.annotations.NotNull()
        private final android.widget.TextView tvTime = null;
        @org.jetbrains.annotations.NotNull()
        private final android.widget.TextView tvSub = null;
        @org.jetbrains.annotations.NotNull()
        private final android.widget.TextView tvBadge = null;
        @org.jetbrains.annotations.NotNull()
        private final android.widget.TextView tvMuted = null;
        @org.jetbrains.annotations.NotNull()
        private final android.view.View divider = null;
        
        public VH(@org.jetbrains.annotations.NotNull()
        android.widget.LinearLayout root) {
            super(null);
        }
        
        @org.jetbrains.annotations.NotNull()
        public final android.widget.LinearLayout getRoot() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final android.widget.FrameLayout getAvatarFrame() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final android.widget.TextView getTvAvatar() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final android.widget.ImageView getIvPhoto() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final android.view.View getOnlineDot() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final android.widget.TextView getTvName() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final android.widget.TextView getTvTime() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final android.widget.TextView getTvSub() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final android.widget.TextView getTvBadge() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final android.widget.TextView getTvMuted() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final android.view.View getDivider() {
            return null;
        }
    }
}