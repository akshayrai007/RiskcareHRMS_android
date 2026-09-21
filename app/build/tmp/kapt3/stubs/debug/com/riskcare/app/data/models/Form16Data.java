package com.riskcare.app.data.models;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0014\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B;\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\n\u0012\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\f\u00a2\u0006\u0002\u0010\u000eJ\t\u0010\u001a\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u001b\u001a\u00020\u0005H\u00c6\u0003J\t\u0010\u001c\u001a\u00020\u0005H\u00c6\u0003J\t\u0010\u001d\u001a\u00020\bH\u00c6\u0003J\t\u0010\u001e\u001a\u00020\nH\u00c6\u0003J\u000f\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\r0\fH\u00c6\u0003JK\u0010 \u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00052\b\b\u0002\u0010\u0007\u001a\u00020\b2\b\b\u0002\u0010\t\u001a\u00020\n2\u000e\b\u0002\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\fH\u00c6\u0001J\u0013\u0010!\u001a\u00020\"2\b\u0010#\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010$\u001a\u00020%H\u00d6\u0001J\t\u0010&\u001a\u00020\u0005H\u00d6\u0001R\u0016\u0010\u0006\u001a\u00020\u00058\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0016\u0010\u0004\u001a\u00020\u00058\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0010R\u001c\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\f8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0016\u0010\u0007\u001a\u00020\b8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u0016\u0010\t\u001a\u00020\n8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019\u00a8\u0006\'"}, d2 = {"Lcom/riskcare/app/data/models/Form16Data;", "", "employee", "Lcom/riskcare/app/data/models/Form16Employee;", "financialYear", "", "assessmentYear", "partA", "Lcom/riskcare/app/data/models/Form16PartA;", "partB", "Lcom/riskcare/app/data/models/Form16PartB;", "monthlyBreakdown", "", "Lcom/riskcare/app/data/models/Form16Month;", "(Lcom/riskcare/app/data/models/Form16Employee;Ljava/lang/String;Ljava/lang/String;Lcom/riskcare/app/data/models/Form16PartA;Lcom/riskcare/app/data/models/Form16PartB;Ljava/util/List;)V", "getAssessmentYear", "()Ljava/lang/String;", "getEmployee", "()Lcom/riskcare/app/data/models/Form16Employee;", "getFinancialYear", "getMonthlyBreakdown", "()Ljava/util/List;", "getPartA", "()Lcom/riskcare/app/data/models/Form16PartA;", "getPartB", "()Lcom/riskcare/app/data/models/Form16PartB;", "component1", "component2", "component3", "component4", "component5", "component6", "copy", "equals", "", "other", "hashCode", "", "toString", "app_debug"})
public final class Form16Data {
    @org.jetbrains.annotations.NotNull()
    private final com.riskcare.app.data.models.Form16Employee employee = null;
    @com.google.gson.annotations.SerializedName(value = "financial_year")
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String financialYear = null;
    @com.google.gson.annotations.SerializedName(value = "assessment_year")
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String assessmentYear = null;
    @com.google.gson.annotations.SerializedName(value = "part_a")
    @org.jetbrains.annotations.NotNull()
    private final com.riskcare.app.data.models.Form16PartA partA = null;
    @com.google.gson.annotations.SerializedName(value = "part_b")
    @org.jetbrains.annotations.NotNull()
    private final com.riskcare.app.data.models.Form16PartB partB = null;
    @com.google.gson.annotations.SerializedName(value = "monthly_breakdown")
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.riskcare.app.data.models.Form16Month> monthlyBreakdown = null;
    
    public Form16Data(@org.jetbrains.annotations.NotNull()
    com.riskcare.app.data.models.Form16Employee employee, @org.jetbrains.annotations.NotNull()
    java.lang.String financialYear, @org.jetbrains.annotations.NotNull()
    java.lang.String assessmentYear, @org.jetbrains.annotations.NotNull()
    com.riskcare.app.data.models.Form16PartA partA, @org.jetbrains.annotations.NotNull()
    com.riskcare.app.data.models.Form16PartB partB, @org.jetbrains.annotations.NotNull()
    java.util.List<com.riskcare.app.data.models.Form16Month> monthlyBreakdown) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.riskcare.app.data.models.Form16Employee getEmployee() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getFinancialYear() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getAssessmentYear() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.riskcare.app.data.models.Form16PartA getPartA() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.riskcare.app.data.models.Form16PartB getPartB() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.riskcare.app.data.models.Form16Month> getMonthlyBreakdown() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.riskcare.app.data.models.Form16Employee component1() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component2() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component3() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.riskcare.app.data.models.Form16PartA component4() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.riskcare.app.data.models.Form16PartB component5() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.riskcare.app.data.models.Form16Month> component6() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.riskcare.app.data.models.Form16Data copy(@org.jetbrains.annotations.NotNull()
    com.riskcare.app.data.models.Form16Employee employee, @org.jetbrains.annotations.NotNull()
    java.lang.String financialYear, @org.jetbrains.annotations.NotNull()
    java.lang.String assessmentYear, @org.jetbrains.annotations.NotNull()
    com.riskcare.app.data.models.Form16PartA partA, @org.jetbrains.annotations.NotNull()
    com.riskcare.app.data.models.Form16PartB partB, @org.jetbrains.annotations.NotNull()
    java.util.List<com.riskcare.app.data.models.Form16Month> monthlyBreakdown) {
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