package com.riskcare.app.data.models;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0006\n\u0002\bB\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u00d7\u0001\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0007\u001a\u00020\u0003\u0012\b\b\u0002\u0010\b\u001a\u00020\u0003\u0012\b\b\u0002\u0010\t\u001a\u00020\u0003\u0012\b\b\u0002\u0010\n\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u000b\u001a\u00020\u0003\u0012\b\b\u0002\u0010\f\u001a\u00020\u0003\u0012\b\b\u0002\u0010\r\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u000e\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u000f\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0010\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0011\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0012\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0013\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0014\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0015\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0016\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0017\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0018J\t\u0010/\u001a\u00020\u0003H\u00c6\u0003J\t\u00100\u001a\u00020\u0003H\u00c6\u0003J\t\u00101\u001a\u00020\u0003H\u00c6\u0003J\t\u00102\u001a\u00020\u0003H\u00c6\u0003J\t\u00103\u001a\u00020\u0003H\u00c6\u0003J\t\u00104\u001a\u00020\u0003H\u00c6\u0003J\t\u00105\u001a\u00020\u0003H\u00c6\u0003J\t\u00106\u001a\u00020\u0003H\u00c6\u0003J\t\u00107\u001a\u00020\u0003H\u00c6\u0003J\t\u00108\u001a\u00020\u0003H\u00c6\u0003J\t\u00109\u001a\u00020\u0003H\u00c6\u0003J\t\u0010:\u001a\u00020\u0003H\u00c6\u0003J\t\u0010;\u001a\u00020\u0003H\u00c6\u0003J\t\u0010<\u001a\u00020\u0003H\u00c6\u0003J\t\u0010=\u001a\u00020\u0003H\u00c6\u0003J\t\u0010>\u001a\u00020\u0003H\u00c6\u0003J\t\u0010?\u001a\u00020\u0003H\u00c6\u0003J\t\u0010@\u001a\u00020\u0003H\u00c6\u0003J\t\u0010A\u001a\u00020\u0003H\u00c6\u0003J\t\u0010B\u001a\u00020\u0003H\u00c6\u0003J\t\u0010C\u001a\u00020\u0003H\u00c6\u0003J\u00db\u0001\u0010D\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u00032\b\b\u0002\u0010\u0007\u001a\u00020\u00032\b\b\u0002\u0010\b\u001a\u00020\u00032\b\b\u0002\u0010\t\u001a\u00020\u00032\b\b\u0002\u0010\n\u001a\u00020\u00032\b\b\u0002\u0010\u000b\u001a\u00020\u00032\b\b\u0002\u0010\f\u001a\u00020\u00032\b\b\u0002\u0010\r\u001a\u00020\u00032\b\b\u0002\u0010\u000e\u001a\u00020\u00032\b\b\u0002\u0010\u000f\u001a\u00020\u00032\b\b\u0002\u0010\u0010\u001a\u00020\u00032\b\b\u0002\u0010\u0011\u001a\u00020\u00032\b\b\u0002\u0010\u0012\u001a\u00020\u00032\b\b\u0002\u0010\u0013\u001a\u00020\u00032\b\b\u0002\u0010\u0014\u001a\u00020\u00032\b\b\u0002\u0010\u0015\u001a\u00020\u00032\b\b\u0002\u0010\u0016\u001a\u00020\u00032\b\b\u0002\u0010\u0017\u001a\u00020\u0003H\u00c6\u0001J\u0013\u0010E\u001a\u00020F2\b\u0010G\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010H\u001a\u00020IH\u00d6\u0001J\t\u0010J\u001a\u00020KH\u00d6\u0001R\u0011\u0010\u0013\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001aR\u0016\u0010\u0005\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u001aR\u0016\u0010\u0007\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u001aR\u0016\u0010\u000b\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001aR\u0016\u0010\r\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u001aR\u0016\u0010\t\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010\u001aR\u0016\u0010\n\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b \u0010\u001aR\u0016\u0010\f\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b!\u0010\u001aR\u0016\u0010\b\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\"\u0010\u001aR\u0016\u0010\u000e\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b#\u0010\u001aR\u0016\u0010\u0006\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b$\u0010\u001aR\u0016\u0010\u000f\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b%\u0010\u001aR\u0016\u0010\u0004\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b&\u0010\u001aR\u0016\u0010\u0017\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\'\u0010\u001aR\u0016\u0010\u0016\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b(\u0010\u001aR\u0016\u0010\u0015\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b)\u0010\u001aR\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b*\u0010\u001aR\u0011\u0010\u0014\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b+\u0010\u001aR\u0016\u0010\u0012\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b,\u0010\u001aR\u0016\u0010\u0011\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b-\u0010\u001aR\u0016\u0010\u0010\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b.\u0010\u001a\u00a8\u0006L"}, d2 = {"Lcom/riskcare/app/data/models/TaxRegimeOld;", "", "stdDeduction", "", "hraExemption", "deduction80c", "deductionNps", "deduction80d", "deductionHomeloan", "deduction80e", "deduction80g", "deduction80dd", "deduction80u", "deduction80ddb", "deductionLta", "houseProperty", "totalDeductions", "taxableIncome", "taxBeforeCess", "cess", "tax", "prevTds", "netTax", "monthlyTds", "(DDDDDDDDDDDDDDDDDDDDD)V", "getCess", "()D", "getDeduction80c", "getDeduction80d", "getDeduction80dd", "getDeduction80ddb", "getDeduction80e", "getDeduction80g", "getDeduction80u", "getDeductionHomeloan", "getDeductionLta", "getDeductionNps", "getHouseProperty", "getHraExemption", "getMonthlyTds", "getNetTax", "getPrevTds", "getStdDeduction", "getTax", "getTaxBeforeCess", "getTaxableIncome", "getTotalDeductions", "component1", "component10", "component11", "component12", "component13", "component14", "component15", "component16", "component17", "component18", "component19", "component2", "component20", "component21", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "", "other", "hashCode", "", "toString", "", "app_debug"})
public final class TaxRegimeOld {
    @com.google.gson.annotations.SerializedName(value = "std_deduction")
    private final double stdDeduction = 0.0;
    @com.google.gson.annotations.SerializedName(value = "hra_exemption")
    private final double hraExemption = 0.0;
    @com.google.gson.annotations.SerializedName(value = "deduction_80c")
    private final double deduction80c = 0.0;
    @com.google.gson.annotations.SerializedName(value = "deduction_nps")
    private final double deductionNps = 0.0;
    @com.google.gson.annotations.SerializedName(value = "deduction_80d")
    private final double deduction80d = 0.0;
    @com.google.gson.annotations.SerializedName(value = "deduction_homeloan")
    private final double deductionHomeloan = 0.0;
    @com.google.gson.annotations.SerializedName(value = "deduction_80e")
    private final double deduction80e = 0.0;
    @com.google.gson.annotations.SerializedName(value = "deduction_80g")
    private final double deduction80g = 0.0;
    @com.google.gson.annotations.SerializedName(value = "deduction_80dd")
    private final double deduction80dd = 0.0;
    @com.google.gson.annotations.SerializedName(value = "deduction_80u")
    private final double deduction80u = 0.0;
    @com.google.gson.annotations.SerializedName(value = "deduction_80ddb")
    private final double deduction80ddb = 0.0;
    @com.google.gson.annotations.SerializedName(value = "deduction_lta")
    private final double deductionLta = 0.0;
    @com.google.gson.annotations.SerializedName(value = "house_property")
    private final double houseProperty = 0.0;
    @com.google.gson.annotations.SerializedName(value = "total_deductions")
    private final double totalDeductions = 0.0;
    @com.google.gson.annotations.SerializedName(value = "taxable_income")
    private final double taxableIncome = 0.0;
    @com.google.gson.annotations.SerializedName(value = "tax_before_cess")
    private final double taxBeforeCess = 0.0;
    private final double cess = 0.0;
    private final double tax = 0.0;
    @com.google.gson.annotations.SerializedName(value = "prev_tds")
    private final double prevTds = 0.0;
    @com.google.gson.annotations.SerializedName(value = "net_tax")
    private final double netTax = 0.0;
    @com.google.gson.annotations.SerializedName(value = "monthly_tds")
    private final double monthlyTds = 0.0;
    
    public TaxRegimeOld(double stdDeduction, double hraExemption, double deduction80c, double deductionNps, double deduction80d, double deductionHomeloan, double deduction80e, double deduction80g, double deduction80dd, double deduction80u, double deduction80ddb, double deductionLta, double houseProperty, double totalDeductions, double taxableIncome, double taxBeforeCess, double cess, double tax, double prevTds, double netTax, double monthlyTds) {
        super();
    }
    
    public final double getStdDeduction() {
        return 0.0;
    }
    
    public final double getHraExemption() {
        return 0.0;
    }
    
    public final double getDeduction80c() {
        return 0.0;
    }
    
    public final double getDeductionNps() {
        return 0.0;
    }
    
    public final double getDeduction80d() {
        return 0.0;
    }
    
    public final double getDeductionHomeloan() {
        return 0.0;
    }
    
    public final double getDeduction80e() {
        return 0.0;
    }
    
    public final double getDeduction80g() {
        return 0.0;
    }
    
    public final double getDeduction80dd() {
        return 0.0;
    }
    
    public final double getDeduction80u() {
        return 0.0;
    }
    
    public final double getDeduction80ddb() {
        return 0.0;
    }
    
    public final double getDeductionLta() {
        return 0.0;
    }
    
    public final double getHouseProperty() {
        return 0.0;
    }
    
    public final double getTotalDeductions() {
        return 0.0;
    }
    
    public final double getTaxableIncome() {
        return 0.0;
    }
    
    public final double getTaxBeforeCess() {
        return 0.0;
    }
    
    public final double getCess() {
        return 0.0;
    }
    
    public final double getTax() {
        return 0.0;
    }
    
    public final double getPrevTds() {
        return 0.0;
    }
    
    public final double getNetTax() {
        return 0.0;
    }
    
    public final double getMonthlyTds() {
        return 0.0;
    }
    
    public TaxRegimeOld() {
        super();
    }
    
    public final double component1() {
        return 0.0;
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
    
    public final double component13() {
        return 0.0;
    }
    
    public final double component14() {
        return 0.0;
    }
    
    public final double component15() {
        return 0.0;
    }
    
    public final double component16() {
        return 0.0;
    }
    
    public final double component17() {
        return 0.0;
    }
    
    public final double component18() {
        return 0.0;
    }
    
    public final double component19() {
        return 0.0;
    }
    
    public final double component2() {
        return 0.0;
    }
    
    public final double component20() {
        return 0.0;
    }
    
    public final double component21() {
        return 0.0;
    }
    
    public final double component3() {
        return 0.0;
    }
    
    public final double component4() {
        return 0.0;
    }
    
    public final double component5() {
        return 0.0;
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
    public final com.riskcare.app.data.models.TaxRegimeOld copy(double stdDeduction, double hraExemption, double deduction80c, double deductionNps, double deduction80d, double deductionHomeloan, double deduction80e, double deduction80g, double deduction80dd, double deduction80u, double deduction80ddb, double deductionLta, double houseProperty, double totalDeductions, double taxableIncome, double taxBeforeCess, double cess, double tax, double prevTds, double netTax, double monthlyTds) {
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