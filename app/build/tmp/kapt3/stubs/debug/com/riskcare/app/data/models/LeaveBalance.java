package com.riskcare.app.data.models;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\u0006\n\u0002\b4\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0086\b\u0018\u00002\u00020\u0001B\u009f\u0001\u0012\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u0005\u0012\b\b\u0002\u0010\t\u001a\u00020\n\u0012\b\b\u0002\u0010\u000b\u001a\u00020\n\u0012\b\b\u0002\u0010\f\u001a\u00020\n\u0012\b\b\u0002\u0010\r\u001a\u00020\n\u0012\b\b\u0002\u0010\u000e\u001a\u00020\n\u0012\b\b\u0002\u0010\u000f\u001a\u00020\n\u0012\b\b\u0002\u0010\u0010\u001a\u00020\n\u0012\n\b\u0002\u0010\u0011\u001a\u0004\u0018\u00010\n\u0012\n\b\u0002\u0010\u0012\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\u0002\u0010\u0013J\u0010\u0010.\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003\u00a2\u0006\u0002\u0010\u001cJ\t\u0010/\u001a\u00020\nH\u00c6\u0003J\t\u00100\u001a\u00020\nH\u00c6\u0003J\t\u00101\u001a\u00020\nH\u00c6\u0003J\u0010\u00102\u001a\u0004\u0018\u00010\nH\u00c6\u0003\u00a2\u0006\u0002\u0010%J\u0010\u00103\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003\u00a2\u0006\u0002\u0010\u001cJ\u000b\u00104\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J\u000b\u00105\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J\u000b\u00106\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J\u000b\u00107\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J\t\u00108\u001a\u00020\nH\u00c6\u0003J\t\u00109\u001a\u00020\nH\u00c6\u0003J\t\u0010:\u001a\u00020\nH\u00c6\u0003J\t\u0010;\u001a\u00020\nH\u00c6\u0003J\u00a8\u0001\u0010<\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u00052\b\b\u0002\u0010\t\u001a\u00020\n2\b\b\u0002\u0010\u000b\u001a\u00020\n2\b\b\u0002\u0010\f\u001a\u00020\n2\b\b\u0002\u0010\r\u001a\u00020\n2\b\b\u0002\u0010\u000e\u001a\u00020\n2\b\b\u0002\u0010\u000f\u001a\u00020\n2\b\b\u0002\u0010\u0010\u001a\u00020\n2\n\b\u0002\u0010\u0011\u001a\u0004\u0018\u00010\n2\n\b\u0002\u0010\u0012\u001a\u0004\u0018\u00010\u0003H\u00c6\u0001\u00a2\u0006\u0002\u0010=J\u0013\u0010>\u001a\u00020?2\b\u0010@\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010A\u001a\u00020\u0003H\u00d6\u0001J\t\u0010B\u001a\u00020\u0005H\u00d6\u0001R\u0011\u0010\u000b\u001a\u00020\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0011\u0010\t\u001a\u00020\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0015R\u0011\u0010\u000f\u001a\u00020\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0015R\u0016\u0010\u000e\u001a\u00020\n8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0015R\u0013\u0010\u0007\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001aR\u001a\u0010\u0012\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004\u00a2\u0006\n\n\u0002\u0010\u001d\u001a\u0004\b\u001b\u0010\u001cR\u0011\u0010\u001e\u001a\u00020\n8F\u00a2\u0006\u0006\u001a\u0004\b\u001f\u0010\u0015R\u0013\u0010 \u001a\u0004\u0018\u00010\u00058F\u00a2\u0006\u0006\u001a\u0004\b!\u0010\u001aR\u0011\u0010\"\u001a\u00020\u00058F\u00a2\u0006\u0006\u001a\u0004\b#\u0010\u001aR\u0015\u0010\u0011\u001a\u0004\u0018\u00010\n\u00a2\u0006\n\n\u0002\u0010&\u001a\u0004\b$\u0010%R\u0018\u0010\b\u001a\u0004\u0018\u00010\u00058\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\'\u0010\u001aR\u001a\u0010\u0002\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004\u00a2\u0006\n\n\u0002\u0010\u001d\u001a\u0004\b(\u0010\u001cR\u0018\u0010\u0004\u001a\u0004\u0018\u00010\u00058\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b)\u0010\u001aR\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b*\u0010\u001aR\u0011\u0010\r\u001a\u00020\n\u00a2\u0006\b\n\u0000\u001a\u0004\b+\u0010\u0015R\u0011\u0010\u0010\u001a\u00020\n\u00a2\u0006\b\n\u0000\u001a\u0004\b,\u0010\u0015R\u0011\u0010\f\u001a\u00020\n\u00a2\u0006\b\n\u0000\u001a\u0004\b-\u0010\u0015\u00a8\u0006C"}, d2 = {"Lcom/riskcare/app/data/models/LeaveBalance;", "", "leaveTypeId", "", "leaveTypeName", "", "name", "code", "leaveTypeCode", "available", "", "allocated", "used", "pending", "carryForward", "balance", "total", "entitled", "daysAllowed", "(Ljava/lang/Integer;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;DDDDDDDLjava/lang/Double;Ljava/lang/Integer;)V", "getAllocated", "()D", "getAvailable", "getBalance", "getCarryForward", "getCode", "()Ljava/lang/String;", "getDaysAllowed", "()Ljava/lang/Integer;", "Ljava/lang/Integer;", "effectiveBalance", "getEffectiveBalance", "effectiveCode", "getEffectiveCode", "effectiveName", "getEffectiveName", "getEntitled", "()Ljava/lang/Double;", "Ljava/lang/Double;", "getLeaveTypeCode", "getLeaveTypeId", "getLeaveTypeName", "getName", "getPending", "getTotal", "getUsed", "component1", "component10", "component11", "component12", "component13", "component14", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "(Ljava/lang/Integer;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;DDDDDDDLjava/lang/Double;Ljava/lang/Integer;)Lcom/riskcare/app/data/models/LeaveBalance;", "equals", "", "other", "hashCode", "toString", "app_debug"})
public final class LeaveBalance {
    @com.google.gson.annotations.SerializedName(value = "leave_type_id")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Integer leaveTypeId = null;
    @com.google.gson.annotations.SerializedName(value = "leave_type_name")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String leaveTypeName = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String name = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String code = null;
    @com.google.gson.annotations.SerializedName(value = "leave_type_code")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String leaveTypeCode = null;
    private final double available = 0.0;
    private final double allocated = 0.0;
    private final double used = 0.0;
    private final double pending = 0.0;
    @com.google.gson.annotations.SerializedName(value = "carry_forward")
    private final double carryForward = 0.0;
    private final double balance = 0.0;
    private final double total = 0.0;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double entitled = null;
    @com.google.gson.annotations.SerializedName(value = "days_allowed")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Integer daysAllowed = null;
    
    public LeaveBalance(@org.jetbrains.annotations.Nullable()
    java.lang.Integer leaveTypeId, @org.jetbrains.annotations.Nullable()
    java.lang.String leaveTypeName, @org.jetbrains.annotations.Nullable()
    java.lang.String name, @org.jetbrains.annotations.Nullable()
    java.lang.String code, @org.jetbrains.annotations.Nullable()
    java.lang.String leaveTypeCode, double available, double allocated, double used, double pending, double carryForward, double balance, double total, @org.jetbrains.annotations.Nullable()
    java.lang.Double entitled, @org.jetbrains.annotations.Nullable()
    java.lang.Integer daysAllowed) {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer getLeaveTypeId() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getLeaveTypeName() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getName() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getCode() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getLeaveTypeCode() {
        return null;
    }
    
    public final double getAvailable() {
        return 0.0;
    }
    
    public final double getAllocated() {
        return 0.0;
    }
    
    public final double getUsed() {
        return 0.0;
    }
    
    public final double getPending() {
        return 0.0;
    }
    
    public final double getCarryForward() {
        return 0.0;
    }
    
    public final double getBalance() {
        return 0.0;
    }
    
    public final double getTotal() {
        return 0.0;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getEntitled() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer getDaysAllowed() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getEffectiveCode() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getEffectiveName() {
        return null;
    }
    
    public final double getEffectiveBalance() {
        return 0.0;
    }
    
    public LeaveBalance() {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer component1() {
        return null;
    }
    
    public final double component10() {
        return 0.0;
    }
    
    public final double component11() {
        return 0.0;
    }
    
    public final double component12() {
        return 0.0;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double component13() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer component14() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component2() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component3() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component4() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component5() {
        return null;
    }
    
    public final double component6() {
        return 0.0;
    }
    
    public final double component7() {
        return 0.0;
    }
    
    public final double component8() {
        return 0.0;
    }
    
    public final double component9() {
        return 0.0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.riskcare.app.data.models.LeaveBalance copy(@org.jetbrains.annotations.Nullable()
    java.lang.Integer leaveTypeId, @org.jetbrains.annotations.Nullable()
    java.lang.String leaveTypeName, @org.jetbrains.annotations.Nullable()
    java.lang.String name, @org.jetbrains.annotations.Nullable()
    java.lang.String code, @org.jetbrains.annotations.Nullable()
    java.lang.String leaveTypeCode, double available, double allocated, double used, double pending, double carryForward, double balance, double total, @org.jetbrains.annotations.Nullable()
    java.lang.Double entitled, @org.jetbrains.annotations.Nullable()
    java.lang.Integer daysAllowed) {
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