package com.riskcare.app.data.models;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0018\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001BY\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\b\b\u0002\u0010\n\u001a\u00020\u000b\u0012\b\b\u0002\u0010\f\u001a\u00020\u0003\u0012\u0010\b\u0002\u0010\r\u001a\n\u0012\u0004\u0012\u00020\u000b\u0018\u00010\u000e\u00a2\u0006\u0002\u0010\u000fJ\t\u0010\u001d\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u001e\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u001f\u001a\u00020\u0003H\u00c6\u0003J\t\u0010 \u001a\u00020\u0007H\u00c6\u0003J\t\u0010!\u001a\u00020\tH\u00c6\u0003J\t\u0010\"\u001a\u00020\u000bH\u00c6\u0003J\t\u0010#\u001a\u00020\u0003H\u00c6\u0003J\u0011\u0010$\u001a\n\u0012\u0004\u0012\u00020\u000b\u0018\u00010\u000eH\u00c6\u0003Ja\u0010%\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\t2\b\b\u0002\u0010\n\u001a\u00020\u000b2\b\b\u0002\u0010\f\u001a\u00020\u00032\u0010\b\u0002\u0010\r\u001a\n\u0012\u0004\u0012\u00020\u000b\u0018\u00010\u000eH\u00c6\u0001J\u0013\u0010&\u001a\u00020\'2\b\u0010(\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010)\u001a\u00020*H\u00d6\u0001J\t\u0010+\u001a\u00020\u000bH\u00d6\u0001R\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0016\u0010\b\u001a\u00020\t8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0016\u0010\u0006\u001a\u00020\u00078\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0016\u0010\u0005\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0011R\u0016\u0010\u0004\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0011R\u001e\u0010\r\u001a\n\u0012\u0004\u0012\u00020\u000b\u0018\u00010\u000e8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019R\u0011\u0010\n\u001a\u00020\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u001bR\u0011\u0010\f\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u0011\u00a8\u0006,"}, d2 = {"Lcom/riskcare/app/data/models/TaxPreviewData;", "", "annualGross", "", "prevSalary", "otherIncome", "oldRegime", "Lcom/riskcare/app/data/models/TaxRegimeOld;", "newRegime", "Lcom/riskcare/app/data/models/TaxRegimeNew;", "recommended", "", "savings", "reasons", "", "(DDDLcom/riskcare/app/data/models/TaxRegimeOld;Lcom/riskcare/app/data/models/TaxRegimeNew;Ljava/lang/String;DLjava/util/List;)V", "getAnnualGross", "()D", "getNewRegime", "()Lcom/riskcare/app/data/models/TaxRegimeNew;", "getOldRegime", "()Lcom/riskcare/app/data/models/TaxRegimeOld;", "getOtherIncome", "getPrevSalary", "getReasons", "()Ljava/util/List;", "getRecommended", "()Ljava/lang/String;", "getSavings", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "copy", "equals", "", "other", "hashCode", "", "toString", "app_debug"})
public final class TaxPreviewData {
    @com.google.gson.annotations.SerializedName(value = "annual_gross")
    private final double annualGross = 0.0;
    @com.google.gson.annotations.SerializedName(value = "prev_salary")
    private final double prevSalary = 0.0;
    @com.google.gson.annotations.SerializedName(value = "other_income")
    private final double otherIncome = 0.0;
    @com.google.gson.annotations.SerializedName(value = "old_regime")
    @org.jetbrains.annotations.NotNull()
    private final com.riskcare.app.data.models.TaxRegimeOld oldRegime = null;
    @com.google.gson.annotations.SerializedName(value = "new_regime")
    @org.jetbrains.annotations.NotNull()
    private final com.riskcare.app.data.models.TaxRegimeNew newRegime = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String recommended = null;
    private final double savings = 0.0;
    @com.google.gson.annotations.SerializedName(value = "recommendation_reasons")
    @org.jetbrains.annotations.Nullable()
    private final java.util.List<java.lang.String> reasons = null;
    
    public TaxPreviewData(double annualGross, double prevSalary, double otherIncome, @org.jetbrains.annotations.NotNull()
    com.riskcare.app.data.models.TaxRegimeOld oldRegime, @org.jetbrains.annotations.NotNull()
    com.riskcare.app.data.models.TaxRegimeNew newRegime, @org.jetbrains.annotations.NotNull()
    java.lang.String recommended, double savings, @org.jetbrains.annotations.Nullable()
    java.util.List<java.lang.String> reasons) {
        super();
    }
    
    public final double getAnnualGross() {
        return 0.0;
    }
    
    public final double getPrevSalary() {
        return 0.0;
    }
    
    public final double getOtherIncome() {
        return 0.0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.riskcare.app.data.models.TaxRegimeOld getOldRegime() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.riskcare.app.data.models.TaxRegimeNew getNewRegime() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getRecommended() {
        return null;
    }
    
    public final double getSavings() {
        return 0.0;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.util.List<java.lang.String> getReasons() {
        return null;
    }
    
    public final double component1() {
        return 0.0;
    }
    
    public final double component2() {
        return 0.0;
    }
    
    public final double component3() {
        return 0.0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.riskcare.app.data.models.TaxRegimeOld component4() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.riskcare.app.data.models.TaxRegimeNew component5() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component6() {
        return null;
    }
    
    public final double component7() {
        return 0.0;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.util.List<java.lang.String> component8() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.riskcare.app.data.models.TaxPreviewData copy(double annualGross, double prevSalary, double otherIncome, @org.jetbrains.annotations.NotNull()
    com.riskcare.app.data.models.TaxRegimeOld oldRegime, @org.jetbrains.annotations.NotNull()
    com.riskcare.app.data.models.TaxRegimeNew newRegime, @org.jetbrains.annotations.NotNull()
    java.lang.String recommended, double savings, @org.jetbrains.annotations.Nullable()
    java.util.List<java.lang.String> reasons) {
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