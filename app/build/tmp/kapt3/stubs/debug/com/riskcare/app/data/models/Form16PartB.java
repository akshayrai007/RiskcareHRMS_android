package com.riskcare.app.data.models;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0006\n\u0002\b6\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u00af\u0001\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0007\u001a\u00020\u0003\u0012\b\b\u0002\u0010\b\u001a\u00020\u0003\u0012\b\b\u0002\u0010\t\u001a\u00020\u0003\u0012\b\b\u0002\u0010\n\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u000b\u001a\u00020\u0003\u0012\b\b\u0002\u0010\f\u001a\u00020\u0003\u0012\b\b\u0002\u0010\r\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u000e\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u000f\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0010\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0011\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0012\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0013\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0014J\t\u0010\'\u001a\u00020\u0003H\u00c6\u0003J\t\u0010(\u001a\u00020\u0003H\u00c6\u0003J\t\u0010)\u001a\u00020\u0003H\u00c6\u0003J\t\u0010*\u001a\u00020\u0003H\u00c6\u0003J\t\u0010+\u001a\u00020\u0003H\u00c6\u0003J\t\u0010,\u001a\u00020\u0003H\u00c6\u0003J\t\u0010-\u001a\u00020\u0003H\u00c6\u0003J\t\u0010.\u001a\u00020\u0003H\u00c6\u0003J\t\u0010/\u001a\u00020\u0003H\u00c6\u0003J\t\u00100\u001a\u00020\u0003H\u00c6\u0003J\t\u00101\u001a\u00020\u0003H\u00c6\u0003J\t\u00102\u001a\u00020\u0003H\u00c6\u0003J\t\u00103\u001a\u00020\u0003H\u00c6\u0003J\t\u00104\u001a\u00020\u0003H\u00c6\u0003J\t\u00105\u001a\u00020\u0003H\u00c6\u0003J\t\u00106\u001a\u00020\u0003H\u00c6\u0003J\t\u00107\u001a\u00020\u0003H\u00c6\u0003J\u00b3\u0001\u00108\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u00032\b\b\u0002\u0010\u0007\u001a\u00020\u00032\b\b\u0002\u0010\b\u001a\u00020\u00032\b\b\u0002\u0010\t\u001a\u00020\u00032\b\b\u0002\u0010\n\u001a\u00020\u00032\b\b\u0002\u0010\u000b\u001a\u00020\u00032\b\b\u0002\u0010\f\u001a\u00020\u00032\b\b\u0002\u0010\r\u001a\u00020\u00032\b\b\u0002\u0010\u000e\u001a\u00020\u00032\b\b\u0002\u0010\u000f\u001a\u00020\u00032\b\b\u0002\u0010\u0010\u001a\u00020\u00032\b\b\u0002\u0010\u0011\u001a\u00020\u00032\b\b\u0002\u0010\u0012\u001a\u00020\u00032\b\b\u0002\u0010\u0013\u001a\u00020\u0003H\u00c6\u0001J\u0013\u00109\u001a\u00020:2\b\u0010;\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010<\u001a\u00020=H\u00d6\u0001J\t\u0010>\u001a\u00020?H\u00d6\u0001R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0011\u0010\u0005\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0016R\u0016\u0010\u0010\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0016R\u0016\u0010\u0007\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0016R\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0016R\u0016\u0010\t\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u0016R\u0016\u0010\u0012\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u0016R\u0016\u0010\u0013\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u0016R\u0016\u0010\f\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u0016R\u0016\u0010\u000e\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010\u0016R\u0016\u0010\u000f\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b \u0010\u0016R\u0016\u0010\u0011\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b!\u0010\u0016R\u0016\u0010\n\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\"\u0010\u0016R\u0016\u0010\u0006\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b#\u0010\u0016R\u0016\u0010\b\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b$\u0010\u0016R\u0016\u0010\u000b\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b%\u0010\u0016R\u0016\u0010\r\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b&\u0010\u0016\u00a8\u0006@"}, d2 = {"Lcom/riskcare/app/data/models/Form16PartB;", "", "basic", "", "hra", "conveyance", "specialAllowance", "grossSalary", "standardDeduction", "incomeChargeable", "sec80cPf", "totalDeductionsViA", "netTaxableIncome", "totalTds", "pfEmployeeTotal", "pfEmployerTotal", "esiEmployeeTotal", "professionalTaxTotal", "lwfTotal", "netSalaryTotal", "(DDDDDDDDDDDDDDDDD)V", "getBasic", "()D", "getConveyance", "getEsiEmployeeTotal", "getGrossSalary", "getHra", "getIncomeChargeable", "getLwfTotal", "getNetSalaryTotal", "getNetTaxableIncome", "getPfEmployeeTotal", "getPfEmployerTotal", "getProfessionalTaxTotal", "getSec80cPf", "getSpecialAllowance", "getStandardDeduction", "getTotalDeductionsViA", "getTotalTds", "component1", "component10", "component11", "component12", "component13", "component14", "component15", "component16", "component17", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "", "other", "hashCode", "", "toString", "", "app_debug"})
public final class Form16PartB {
    private final double basic = 0.0;
    private final double hra = 0.0;
    private final double conveyance = 0.0;
    @com.google.gson.annotations.SerializedName(value = "special_allowance")
    private final double specialAllowance = 0.0;
    @com.google.gson.annotations.SerializedName(value = "gross_salary")
    private final double grossSalary = 0.0;
    @com.google.gson.annotations.SerializedName(value = "standard_deduction")
    private final double standardDeduction = 0.0;
    @com.google.gson.annotations.SerializedName(value = "income_chargeable")
    private final double incomeChargeable = 0.0;
    @com.google.gson.annotations.SerializedName(value = "sec_80c_pf")
    private final double sec80cPf = 0.0;
    @com.google.gson.annotations.SerializedName(value = "total_deductions_vi_a")
    private final double totalDeductionsViA = 0.0;
    @com.google.gson.annotations.SerializedName(value = "net_taxable_income")
    private final double netTaxableIncome = 0.0;
    @com.google.gson.annotations.SerializedName(value = "total_tds")
    private final double totalTds = 0.0;
    @com.google.gson.annotations.SerializedName(value = "pf_employee_total")
    private final double pfEmployeeTotal = 0.0;
    @com.google.gson.annotations.SerializedName(value = "pf_employer_total")
    private final double pfEmployerTotal = 0.0;
    @com.google.gson.annotations.SerializedName(value = "esi_employee_total")
    private final double esiEmployeeTotal = 0.0;
    @com.google.gson.annotations.SerializedName(value = "professional_tax_total")
    private final double professionalTaxTotal = 0.0;
    @com.google.gson.annotations.SerializedName(value = "lwf_total")
    private final double lwfTotal = 0.0;
    @com.google.gson.annotations.SerializedName(value = "net_salary_total")
    private final double netSalaryTotal = 0.0;
    
    public Form16PartB(double basic, double hra, double conveyance, double specialAllowance, double grossSalary, double standardDeduction, double incomeChargeable, double sec80cPf, double totalDeductionsViA, double netTaxableIncome, double totalTds, double pfEmployeeTotal, double pfEmployerTotal, double esiEmployeeTotal, double professionalTaxTotal, double lwfTotal, double netSalaryTotal) {
        super();
    }
    
    public final double getBasic() {
        return 0.0;
    }
    
    public final double getHra() {
        return 0.0;
    }
    
    public final double getConveyance() {
        return 0.0;
    }
    
    public final double getSpecialAllowance() {
        return 0.0;
    }
    
    public final double getGrossSalary() {
        return 0.0;
    }
    
    public final double getStandardDeduction() {
        return 0.0;
    }
    
    public final double getIncomeChargeable() {
        return 0.0;
    }
    
    public final double getSec80cPf() {
        return 0.0;
    }
    
    public final double getTotalDeductionsViA() {
        return 0.0;
    }
    
    public final double getNetTaxableIncome() {
        return 0.0;
    }
    
    public final double getTotalTds() {
        return 0.0;
    }
    
    public final double getPfEmployeeTotal() {
        return 0.0;
    }
    
    public final double getPfEmployerTotal() {
        return 0.0;
    }
    
    public final double getEsiEmployeeTotal() {
        return 0.0;
    }
    
    public final double getProfessionalTaxTotal() {
        return 0.0;
    }
    
    public final double getLwfTotal() {
        return 0.0;
    }
    
    public final double getNetSalaryTotal() {
        return 0.0;
    }
    
    public Form16PartB() {
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
    
    public final double component2() {
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
    public final com.riskcare.app.data.models.Form16PartB copy(double basic, double hra, double conveyance, double specialAllowance, double grossSalary, double standardDeduction, double incomeChargeable, double sec80cPf, double totalDeductionsViA, double netTaxableIncome, double totalTds, double pfEmployeeTotal, double pfEmployerTotal, double esiEmployeeTotal, double professionalTaxTotal, double lwfTotal, double netSalaryTotal) {
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