package com.riskcare.app.data.models;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\u0006\n\u0002\b2\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\u00c5\u0001\u0012\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\b\u0012\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\b\u0012\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\b\u0012\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\b\u0012\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\b\u0012\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\b\u0012\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\b\u0012\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\b\u0012\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\b\u0012\n\b\u0002\u0010\u0011\u001a\u0004\u0018\u00010\b\u0012\n\b\u0002\u0010\u0012\u001a\u0004\u0018\u00010\b\u0012\n\b\u0002\u0010\u0013\u001a\u0004\u0018\u00010\b\u00a2\u0006\u0002\u0010\u0014J\u000b\u0010(\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u0010\u0010)\u001a\u0004\u0018\u00010\bH\u00c6\u0003\u00a2\u0006\u0002\u0010\u0016J\u0010\u0010*\u001a\u0004\u0018\u00010\bH\u00c6\u0003\u00a2\u0006\u0002\u0010\u0016J\u0010\u0010+\u001a\u0004\u0018\u00010\bH\u00c6\u0003\u00a2\u0006\u0002\u0010\u0016J\u0010\u0010,\u001a\u0004\u0018\u00010\bH\u00c6\u0003\u00a2\u0006\u0002\u0010\u0016J\u0010\u0010-\u001a\u0004\u0018\u00010\bH\u00c6\u0003\u00a2\u0006\u0002\u0010\u0016J\u0010\u0010.\u001a\u0004\u0018\u00010\bH\u00c6\u0003\u00a2\u0006\u0002\u0010\u0016J\u0010\u0010/\u001a\u0004\u0018\u00010\bH\u00c6\u0003\u00a2\u0006\u0002\u0010\u0016J\u000b\u00100\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u000b\u00101\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u000b\u00102\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u0010\u00103\u001a\u0004\u0018\u00010\bH\u00c6\u0003\u00a2\u0006\u0002\u0010\u0016J\u0010\u00104\u001a\u0004\u0018\u00010\bH\u00c6\u0003\u00a2\u0006\u0002\u0010\u0016J\u0010\u00105\u001a\u0004\u0018\u00010\bH\u00c6\u0003\u00a2\u0006\u0002\u0010\u0016J\u0010\u00106\u001a\u0004\u0018\u00010\bH\u00c6\u0003\u00a2\u0006\u0002\u0010\u0016J\u0010\u00107\u001a\u0004\u0018\u00010\bH\u00c6\u0003\u00a2\u0006\u0002\u0010\u0016J\u00ce\u0001\u00108\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\b2\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\b2\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\b2\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\b2\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\b2\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\b2\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\b2\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\b2\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\b2\n\b\u0002\u0010\u0011\u001a\u0004\u0018\u00010\b2\n\b\u0002\u0010\u0012\u001a\u0004\u0018\u00010\b2\n\b\u0002\u0010\u0013\u001a\u0004\u0018\u00010\bH\u00c6\u0001\u00a2\u0006\u0002\u00109J\u0013\u0010:\u001a\u00020;2\b\u0010<\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010=\u001a\u00020>H\u00d6\u0001J\t\u0010?\u001a\u00020\u0003H\u00d6\u0001R\u001a\u0010\u000e\u001a\u0004\u0018\u00010\b8\u0006X\u0087\u0004\u00a2\u0006\n\n\u0002\u0010\u0017\u001a\u0004\b\u0015\u0010\u0016R\u001a\u0010\u0010\u001a\u0004\u0018\u00010\b8\u0006X\u0087\u0004\u00a2\u0006\n\n\u0002\u0010\u0017\u001a\u0004\b\u0018\u0010\u0016R\u001a\u0010\u000f\u001a\u0004\u0018\u00010\b8\u0006X\u0087\u0004\u00a2\u0006\n\n\u0002\u0010\u0017\u001a\u0004\b\u0019\u0010\u0016R\u0013\u0010\u0005\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u001bR\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u001bR\u001a\u0010\u0007\u001a\u0004\u0018\u00010\b8\u0006X\u0087\u0004\u00a2\u0006\n\n\u0002\u0010\u0017\u001a\u0004\b\u001d\u0010\u0016R\u001a\u0010\n\u001a\u0004\u0018\u00010\b8\u0006X\u0087\u0004\u00a2\u0006\n\n\u0002\u0010\u0017\u001a\u0004\b\u001e\u0010\u0016R\u001a\u0010\t\u001a\u0004\u0018\u00010\b8\u0006X\u0087\u0004\u00a2\u0006\n\n\u0002\u0010\u0017\u001a\u0004\b\u001f\u0010\u0016R\u0018\u0010\u0002\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b \u0010\u001bR\u0018\u0010\u0004\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b!\u0010\u001bR\u001a\u0010\u0011\u001a\u0004\u0018\u00010\b8\u0006X\u0087\u0004\u00a2\u0006\n\n\u0002\u0010\u0017\u001a\u0004\b\"\u0010\u0016R\u001a\u0010\u0013\u001a\u0004\u0018\u00010\b8\u0006X\u0087\u0004\u00a2\u0006\n\n\u0002\u0010\u0017\u001a\u0004\b#\u0010\u0016R\u001a\u0010\u0012\u001a\u0004\u0018\u00010\b8\u0006X\u0087\u0004\u00a2\u0006\n\n\u0002\u0010\u0017\u001a\u0004\b$\u0010\u0016R\u001a\u0010\u000b\u001a\u0004\u0018\u00010\b8\u0006X\u0087\u0004\u00a2\u0006\n\n\u0002\u0010\u0017\u001a\u0004\b%\u0010\u0016R\u001a\u0010\r\u001a\u0004\u0018\u00010\b8\u0006X\u0087\u0004\u00a2\u0006\n\n\u0002\u0010\u0017\u001a\u0004\b&\u0010\u0016R\u001a\u0010\f\u001a\u0004\u0018\u00010\b8\u0006X\u0087\u0004\u00a2\u0006\n\n\u0002\u0010\u0017\u001a\u0004\b\'\u0010\u0016\u00a8\u0006@"}, d2 = {"Lcom/riskcare/app/data/models/LeaveSummaryRow;", "", "employeeCode", "", "employeeName", "department", "designation", "elAllocated", "", "elUsed", "elAvailable", "slAllocated", "slUsed", "slAvailable", "clAllocated", "clUsed", "clAvailable", "mlAllocated", "mlUsed", "mlAvailable", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;)V", "getClAllocated", "()Ljava/lang/Double;", "Ljava/lang/Double;", "getClAvailable", "getClUsed", "getDepartment", "()Ljava/lang/String;", "getDesignation", "getElAllocated", "getElAvailable", "getElUsed", "getEmployeeCode", "getEmployeeName", "getMlAllocated", "getMlAvailable", "getMlUsed", "getSlAllocated", "getSlAvailable", "getSlUsed", "component1", "component10", "component11", "component12", "component13", "component14", "component15", "component16", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;)Lcom/riskcare/app/data/models/LeaveSummaryRow;", "equals", "", "other", "hashCode", "", "toString", "app_debug"})
public final class LeaveSummaryRow {
    @com.google.gson.annotations.SerializedName(value = "employee_code")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String employeeCode = null;
    @com.google.gson.annotations.SerializedName(value = "employee_name")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String employeeName = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String department = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String designation = null;
    @com.google.gson.annotations.SerializedName(value = "el_allocated")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double elAllocated = null;
    @com.google.gson.annotations.SerializedName(value = "el_used")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double elUsed = null;
    @com.google.gson.annotations.SerializedName(value = "el_available")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double elAvailable = null;
    @com.google.gson.annotations.SerializedName(value = "sl_allocated")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double slAllocated = null;
    @com.google.gson.annotations.SerializedName(value = "sl_used")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double slUsed = null;
    @com.google.gson.annotations.SerializedName(value = "sl_available")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double slAvailable = null;
    @com.google.gson.annotations.SerializedName(value = "cl_allocated")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double clAllocated = null;
    @com.google.gson.annotations.SerializedName(value = "cl_used")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double clUsed = null;
    @com.google.gson.annotations.SerializedName(value = "cl_available")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double clAvailable = null;
    @com.google.gson.annotations.SerializedName(value = "ml_allocated")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double mlAllocated = null;
    @com.google.gson.annotations.SerializedName(value = "ml_used")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double mlUsed = null;
    @com.google.gson.annotations.SerializedName(value = "ml_available")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double mlAvailable = null;
    
    public LeaveSummaryRow(@org.jetbrains.annotations.Nullable()
    java.lang.String employeeCode, @org.jetbrains.annotations.Nullable()
    java.lang.String employeeName, @org.jetbrains.annotations.Nullable()
    java.lang.String department, @org.jetbrains.annotations.Nullable()
    java.lang.String designation, @org.jetbrains.annotations.Nullable()
    java.lang.Double elAllocated, @org.jetbrains.annotations.Nullable()
    java.lang.Double elUsed, @org.jetbrains.annotations.Nullable()
    java.lang.Double elAvailable, @org.jetbrains.annotations.Nullable()
    java.lang.Double slAllocated, @org.jetbrains.annotations.Nullable()
    java.lang.Double slUsed, @org.jetbrains.annotations.Nullable()
    java.lang.Double slAvailable, @org.jetbrains.annotations.Nullable()
    java.lang.Double clAllocated, @org.jetbrains.annotations.Nullable()
    java.lang.Double clUsed, @org.jetbrains.annotations.Nullable()
    java.lang.Double clAvailable, @org.jetbrains.annotations.Nullable()
    java.lang.Double mlAllocated, @org.jetbrains.annotations.Nullable()
    java.lang.Double mlUsed, @org.jetbrains.annotations.Nullable()
    java.lang.Double mlAvailable) {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getEmployeeCode() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getEmployeeName() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getDepartment() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getDesignation() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getElAllocated() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getElUsed() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getElAvailable() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getSlAllocated() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getSlUsed() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getSlAvailable() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getClAllocated() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getClUsed() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getClAvailable() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getMlAllocated() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getMlUsed() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getMlAvailable() {
        return null;
    }
    
    public LeaveSummaryRow() {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component1() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double component10() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double component11() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double component12() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double component13() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double component14() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double component15() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double component16() {
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
    public final java.lang.Double component5() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double component6() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double component7() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double component8() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double component9() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.riskcare.app.data.models.LeaveSummaryRow copy(@org.jetbrains.annotations.Nullable()
    java.lang.String employeeCode, @org.jetbrains.annotations.Nullable()
    java.lang.String employeeName, @org.jetbrains.annotations.Nullable()
    java.lang.String department, @org.jetbrains.annotations.Nullable()
    java.lang.String designation, @org.jetbrains.annotations.Nullable()
    java.lang.Double elAllocated, @org.jetbrains.annotations.Nullable()
    java.lang.Double elUsed, @org.jetbrains.annotations.Nullable()
    java.lang.Double elAvailable, @org.jetbrains.annotations.Nullable()
    java.lang.Double slAllocated, @org.jetbrains.annotations.Nullable()
    java.lang.Double slUsed, @org.jetbrains.annotations.Nullable()
    java.lang.Double slAvailable, @org.jetbrains.annotations.Nullable()
    java.lang.Double clAllocated, @org.jetbrains.annotations.Nullable()
    java.lang.Double clUsed, @org.jetbrains.annotations.Nullable()
    java.lang.Double clAvailable, @org.jetbrains.annotations.Nullable()
    java.lang.Double mlAllocated, @org.jetbrains.annotations.Nullable()
    java.lang.Double mlUsed, @org.jetbrains.annotations.Nullable()
    java.lang.Double mlAvailable) {
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