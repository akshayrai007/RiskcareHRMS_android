package com.riskcare.app.ui.tickets;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000p\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J.\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00152\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00180\u0017H\u0002J\b\u0010\u0019\u001a\u00020\u0018H\u0002J,\u0010\u001a\u001a\u00020\u00182\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u001b\u001a\u00020\u001c2\u0012\u0010\u001d\u001a\u000e\u0012\u0004\u0012\u00020\u0015\u0012\u0004\u0012\u00020\u00180\u001eH\u0002J$\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020\"2\b\u0010#\u001a\u0004\u0018\u00010$2\b\u0010%\u001a\u0004\u0018\u00010&H\u0016J\u0010\u0010\'\u001a\u00020\u00182\u0006\u0010(\u001a\u00020\bH\u0002J&\u0010)\u001a\u00020\u00182\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u001b\u001a\u00020\u001c2\f\u0010*\u001a\b\u0012\u0004\u0012\u00020\u00180\u0017H\u0002J\u001e\u0010+\u001a\u00020\u00182\u0006\u0010\u0010\u001a\u00020\u00112\f\u0010,\u001a\b\u0012\u0004\u0012\u00020\u00180\u0017H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\nX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\nX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\nX\u0082.\u00a2\u0006\u0002\n\u0000\u00a8\u0006-"}, d2 = {"Lcom/riskcare/app/ui/tickets/TicketsFragment;", "Landroidx/fragment/app/Fragment;", "()V", "progress", "Landroid/widget/ProgressBar;", "rv", "Landroidx/recyclerview/widget/RecyclerView;", "tab", "Lcom/riskcare/app/ui/tickets/TicketTab;", "tabAssigned", "Landroid/widget/TextView;", "tabMine", "tabSupervising", "tvEmpty", "buildTicketCard", "Landroidx/cardview/widget/CardView;", "ctx", "Landroid/content/Context;", "dp", "", "t", "Lcom/riskcare/app/data/models/Ticket;", "onClick", "Lkotlin/Function0;", "", "load", "loadDetail", "ticketId", "", "onLoaded", "Lkotlin/Function1;", "onCreateView", "Landroid/view/View;", "i", "Landroid/view/LayoutInflater;", "c", "Landroid/view/ViewGroup;", "s", "Landroid/os/Bundle;", "setActive", "newTab", "showDetailDialog", "onChanged", "showRaiseDialog", "onDone", "app_debug"})
public final class TicketsFragment extends androidx.fragment.app.Fragment {
    @org.jetbrains.annotations.NotNull()
    private com.riskcare.app.ui.tickets.TicketTab tab = com.riskcare.app.ui.tickets.TicketTab.ASSIGNED;
    private androidx.recyclerview.widget.RecyclerView rv;
    private android.widget.ProgressBar progress;
    private android.widget.TextView tvEmpty;
    private android.widget.TextView tabMine;
    private android.widget.TextView tabAssigned;
    private android.widget.TextView tabSupervising;
    
    public TicketsFragment() {
        super();
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public android.view.View onCreateView(@org.jetbrains.annotations.NotNull()
    android.view.LayoutInflater i, @org.jetbrains.annotations.Nullable()
    android.view.ViewGroup c, @org.jetbrains.annotations.Nullable()
    android.os.Bundle s) {
        return null;
    }
    
    private final void setActive(com.riskcare.app.ui.tickets.TicketTab newTab) {
    }
    
    private final void load() {
    }
    
    private final androidx.cardview.widget.CardView buildTicketCard(android.content.Context ctx, float dp, com.riskcare.app.data.models.Ticket t, kotlin.jvm.functions.Function0<kotlin.Unit> onClick) {
        return null;
    }
    
    private final void showRaiseDialog(android.content.Context ctx, kotlin.jvm.functions.Function0<kotlin.Unit> onDone) {
    }
    
    private final void showDetailDialog(android.content.Context ctx, int ticketId, kotlin.jvm.functions.Function0<kotlin.Unit> onChanged) {
    }
    
    private final void loadDetail(android.content.Context ctx, int ticketId, kotlin.jvm.functions.Function1<? super com.riskcare.app.data.models.Ticket, kotlin.Unit> onLoaded) {
    }
}