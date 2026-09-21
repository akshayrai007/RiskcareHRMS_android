package com.riskcare.app.data.models;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0014\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001BM\u0012\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007\u0012\b\b\u0002\u0010\b\u001a\u00020\u0007\u0012\u0010\b\u0002\u0010\t\u001a\n\u0012\u0004\u0012\u00020\u000b\u0018\u00010\n\u0012\b\b\u0002\u0010\f\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\rJ\u000b\u0010\u0018\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u000b\u0010\u0019\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J\t\u0010\u001a\u001a\u00020\u0007H\u00c6\u0003J\t\u0010\u001b\u001a\u00020\u0007H\u00c6\u0003J\u0011\u0010\u001c\u001a\n\u0012\u0004\u0012\u00020\u000b\u0018\u00010\nH\u00c6\u0003J\t\u0010\u001d\u001a\u00020\u0007H\u00c6\u0003JQ\u0010\u001e\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\u00072\u0010\b\u0002\u0010\t\u001a\n\u0012\u0004\u0012\u00020\u000b\u0018\u00010\n2\b\b\u0002\u0010\f\u001a\u00020\u0007H\u00c6\u0001J\u0013\u0010\u001f\u001a\u00020 2\b\u0010!\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\"\u001a\u00020\u0007H\u00d6\u0001J\t\u0010#\u001a\u00020$H\u00d6\u0001R\u001e\u0010\t\u001a\n\u0012\u0004\u0012\u00020\u000b\u0018\u00010\n8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0018\u0010\u0004\u001a\u0004\u0018\u00010\u00058\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0016\u0010\u0006\u001a\u00020\u00078\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0016\u0010\b\u001a\u00020\u00078\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0013R\u0018\u0010\u0002\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0016\u0010\f\u001a\u00020\u00078\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0013\u00a8\u0006%"}, d2 = {"Lcom/riskcare/app/data/models/DashboardData;", "", "todayAttendance", "Lcom/riskcare/app/data/models/AttendanceRecord;", "monthlySummary", "Lcom/riskcare/app/data/models/MonthlySummary;", "pendingLeaveApprovals", "", "pendingRegularizations", "leaveBalance", "", "Lcom/riskcare/app/data/models/LeaveBalance;", "unreadNotifications", "(Lcom/riskcare/app/data/models/AttendanceRecord;Lcom/riskcare/app/data/models/MonthlySummary;IILjava/util/List;I)V", "getLeaveBalance", "()Ljava/util/List;", "getMonthlySummary", "()Lcom/riskcare/app/data/models/MonthlySummary;", "getPendingLeaveApprovals", "()I", "getPendingRegularizations", "getTodayAttendance", "()Lcom/riskcare/app/data/models/AttendanceRecord;", "getUnreadNotifications", "component1", "component2", "component3", "component4", "component5", "component6", "copy", "equals", "", "other", "hashCode", "toString", "", "app_debug"})
public final class DashboardData {
    @com.google.gson.annotations.SerializedName(value = "today_attendance")
    @org.jetbrains.annotations.Nullable()
    private final com.riskcare.app.data.models.AttendanceRecord todayAttendance = null;
    @com.google.gson.annotations.SerializedName(value = "monthly_summary")
    @org.jetbrains.annotations.Nullable()
    private final com.riskcare.app.data.models.MonthlySummary monthlySummary = null;
    @com.google.gson.annotations.SerializedName(value = "pending_leave_approvals")
    private final int pendingLeaveApprovals = 0;
    @com.google.gson.annotations.SerializedName(value = "pending_regularizations")
    private final int pendingRegularizations = 0;
    @com.google.gson.annotations.SerializedName(value = "leave_balance")
    @org.jetbrains.annotations.Nullable()
    private final java.util.List<com.riskcare.app.data.models.LeaveBalance> leaveBalance = null;
    @com.google.gson.annotations.SerializedName(value = "unread_notifications")
    private final int unreadNotifications = 0;
    
    public DashboardData(@org.jetbrains.annotations.Nullable()
    com.riskcare.app.data.models.AttendanceRecord todayAttendance, @org.jetbrains.annotations.Nullable()
    com.riskcare.app.data.models.MonthlySummary monthlySummary, int pendingLeaveApprovals, int pendingRegularizations, @org.jetbrains.annotations.Nullable()
    java.util.List<com.riskcare.app.data.models.LeaveBalance> leaveBalance, int unreadNotifications) {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.riskcare.app.data.models.AttendanceRecord getTodayAttendance() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.riskcare.app.data.models.MonthlySummary getMonthlySummary() {
        return null;
    }
    
    public final int getPendingLeaveApprovals() {
        return 0;
    }
    
    public final int getPendingRegularizations() {
        return 0;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.util.List<com.riskcare.app.data.models.LeaveBalance> getLeaveBalance() {
        return null;
    }
    
    public final int getUnreadNotifications() {
        return 0;
    }
    
    public DashboardData() {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.riskcare.app.data.models.AttendanceRecord component1() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.riskcare.app.data.models.MonthlySummary component2() {
        return null;
    }
    
    public final int component3() {
        return 0;
    }
    
    public final int component4() {
        return 0;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.util.List<com.riskcare.app.data.models.LeaveBalance> component5() {
        return null;
    }
    
    public final int component6() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.riskcare.app.data.models.DashboardData copy(@org.jetbrains.annotations.Nullable()
    com.riskcare.app.data.models.AttendanceRecord todayAttendance, @org.jetbrains.annotations.Nullable()
    com.riskcare.app.data.models.MonthlySummary monthlySummary, int pendingLeaveApprovals, int pendingRegularizations, @org.jetbrains.annotations.Nullable()
    java.util.List<com.riskcare.app.data.models.LeaveBalance> leaveBalance, int unreadNotifications) {
        return null;
    }
    
    @java.lang.Override()
    public boolean equals(@org.jetbrains.annotations.Nullable()
    java.lang.Object other) {
        return false;
    }
    
    @java.lang.Override()
    public int hashCode() {
        return 0;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public java.lang.String toString() {
        return null;
    }
}