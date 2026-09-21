package com.riskcare.app.ui.more;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000^\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\u0018\u00002\f\u0012\b\u0012\u00060\u0002R\u00020\u00000\u0001:\u0001&B)\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t\u00a2\u0006\u0002\u0010\u000bJ=\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\b\u0010\u0012\u001a\u0004\u0018\u00010\u00132\u0006\u0010\u0014\u001a\u00020\u00132\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\n0\tH\u0002\u00a2\u0006\u0002\u0010\u0016J\b\u0010\u0017\u001a\u00020\u0013H\u0016J\u001c\u0010\u0018\u001a\u00020\n2\n\u0010\u0019\u001a\u00060\u0002R\u00020\u00002\u0006\u0010\u001a\u001a\u00020\u0013H\u0016J\u001c\u0010\u001b\u001a\u00060\u0002R\u00020\u00002\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u0013H\u0016J,\u0010\u001f\u001a\u00020\n2\u0006\u0010 \u001a\u00020\u00132\u0006\u0010!\u001a\u00020\"2\u0012\u0010#\u001a\u000e\u0012\u0004\u0012\u00020%\u0012\u0004\u0012\u00020\n0$H\u0002R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\'"}, d2 = {"Lcom/riskcare/app/ui/more/BirthdayAdapter;", "Landroidx/recyclerview/widget/RecyclerView$Adapter;", "Lcom/riskcare/app/ui/more/BirthdayAdapter$VH;", "items", "", "Lcom/riskcare/app/data/models/BirthdayRecord;", "ctx", "Landroid/content/Context;", "onRefresh", "Lkotlin/Function0;", "", "(Ljava/util/List;Landroid/content/Context;Lkotlin/jvm/functions/Function0;)V", "buildWishCard", "Landroid/widget/LinearLayout;", "dp", "", "wish", "Lcom/riskcare/app/data/models/BirthdayWish;", "myId", "", "birthdayEmpId", "onDeleted", "(FLcom/riskcare/app/data/models/BirthdayWish;Ljava/lang/Integer;ILkotlin/jvm/functions/Function0;)Landroid/widget/LinearLayout;", "getItemCount", "onBindViewHolder", "h", "pos", "onCreateViewHolder", "p", "Landroid/view/ViewGroup;", "t", "showWishDialog", "empId", "name", "", "onResult", "Lkotlin/Function1;", "", "VH", "app_debug"})
public final class BirthdayAdapter extends androidx.recyclerview.widget.RecyclerView.Adapter<com.riskcare.app.ui.more.BirthdayAdapter.VH> {
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.riskcare.app.data.models.BirthdayRecord> items = null;
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context ctx = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.jvm.functions.Function0<kotlin.Unit> onRefresh = null;
    
    public BirthdayAdapter(@org.jetbrains.annotations.NotNull()
    java.util.List<com.riskcare.app.data.models.BirthdayRecord> items, @org.jetbrains.annotations.NotNull()
    android.content.Context ctx, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onRefresh) {
        super();
    }
    
    @java.lang.Override()
    public int getItemCount() {
        return 0;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public com.riskcare.app.ui.more.BirthdayAdapter.VH onCreateViewHolder(@org.jetbrains.annotations.NotNull()
    android.view.ViewGroup p, int t) {
        return null;
    }
    
    @java.lang.Override()
    public void onBindViewHolder(@org.jetbrains.annotations.NotNull()
    com.riskcare.app.ui.more.BirthdayAdapter.VH h, int pos) {
    }
    
    private final android.widget.LinearLayout buildWishCard(float dp, com.riskcare.app.data.models.BirthdayWish wish, java.lang.Integer myId, int birthdayEmpId, kotlin.jvm.functions.Function0<kotlin.Unit> onDeleted) {
        return null;
    }
    
    private final void showWishDialog(int empId, java.lang.String name, kotlin.jvm.functions.Function1<? super java.lang.Boolean, kotlin.Unit> onResult) {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0086\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\u0007"}, d2 = {"Lcom/riskcare/app/ui/more/BirthdayAdapter$VH;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "card", "Landroidx/cardview/widget/CardView;", "(Lcom/riskcare/app/ui/more/BirthdayAdapter;Landroidx/cardview/widget/CardView;)V", "getCard", "()Landroidx/cardview/widget/CardView;", "app_debug"})
    public final class VH extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
        @org.jetbrains.annotations.NotNull()
        private final androidx.cardview.widget.CardView card = null;
        
        public VH(@org.jetbrains.annotations.NotNull()
        androidx.cardview.widget.CardView card) {
            super(null);
        }
        
        @org.jetbrains.annotations.NotNull()
        public final androidx.cardview.widget.CardView getCard() {
            return null;
        }
    }
}