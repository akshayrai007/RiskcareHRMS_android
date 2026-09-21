package com.riskcare.app.data.models;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u001b\n\u0002\u0010\u0006\n\u0002\b!\b\u0086\b\u0018\u00002\u00020\u0001B\u00a7\u0001\u0012\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u0001\u0012\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u0001\u0012\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u0001\u0012\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u0001\u0012\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u0001\u0012\b\b\u0002\u0010\u000e\u001a\u00020\u000f\u0012\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u0005\u0012\b\b\u0002\u0010\u0011\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0012\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0013J\u0010\u00107\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003\u00a2\u0006\u0002\u0010#J\u000b\u00108\u001a\u0004\u0018\u00010\u0001H\u00c6\u0003J\t\u00109\u001a\u00020\u000fH\u00c6\u0003J\u000b\u0010:\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J\t\u0010;\u001a\u00020\u0003H\u00c6\u0003J\t\u0010<\u001a\u00020\u0003H\u00c6\u0003J\u000b\u0010=\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J\u000b\u0010>\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J\u000b\u0010?\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J\u000b\u0010@\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J\u000b\u0010A\u001a\u0004\u0018\u00010\u0001H\u00c6\u0003J\u000b\u0010B\u001a\u0004\u0018\u00010\u0001H\u00c6\u0003J\u000b\u0010C\u001a\u0004\u0018\u00010\u0001H\u00c6\u0003J\u000b\u0010D\u001a\u0004\u0018\u00010\u0001H\u00c6\u0003J\u00b0\u0001\u0010E\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u00012\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u00012\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u00012\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u00012\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u00012\b\b\u0002\u0010\u000e\u001a\u00020\u000f2\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u00052\b\b\u0002\u0010\u0011\u001a\u00020\u00032\b\b\u0002\u0010\u0012\u001a\u00020\u0003H\u00c6\u0001\u00a2\u0006\u0002\u0010FJ\u0013\u0010G\u001a\u00020\u000f2\b\u0010H\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010I\u001a\u00020\u0003H\u00d6\u0001J\t\u0010J\u001a\u00020\u0005H\u00d6\u0001J\u000e\u0010K\u001a\u00020+*\u0004\u0018\u00010\u0001H\u0002R\u0011\u0010\u0014\u001a\u00020\u00038F\u00a2\u0006\u0006\u001a\u0004\b\u0015\u0010\u0016R\u0018\u0010\r\u001a\u0004\u0018\u00010\u00018\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u0011\u0010\u0019\u001a\u00020\u00038F\u00a2\u0006\u0006\u001a\u0004\b\u001a\u0010\u0016R\u0018\u0010\n\u001a\u0004\u0018\u00010\u00018\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u0018R\u0018\u0010\b\u001a\u0004\u0018\u00010\u00058\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u001dR\u0011\u0010\u001e\u001a\u00020\u00058F\u00a2\u0006\u0006\u001a\u0004\b\u001f\u0010\u001dR\u0018\u0010\u0007\u001a\u0004\u0018\u00010\u00058\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b \u0010\u001dR\u0018\u0010\u0004\u001a\u0004\u0018\u00010\u00058\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b!\u0010\u001dR\u0015\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\n\n\u0002\u0010$\u001a\u0004\b\"\u0010#R\u0016\u0010\u000e\u001a\u00020\u000f8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010%R\u0018\u0010\u0006\u001a\u0004\u0018\u00010\u00058\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b&\u0010\u001dR\u0013\u0010\u0010\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\'\u0010\u001dR\u0011\u0010\u0011\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b(\u0010\u0016R\u0011\u0010\u0012\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b)\u0010\u0016R\u0011\u0010*\u001a\u00020+8F\u00a2\u0006\u0006\u001a\u0004\b,\u0010-R\u0011\u0010.\u001a\u00020\u00058F\u00a2\u0006\u0006\u001a\u0004\b/\u0010\u001dR\u0018\u0010\f\u001a\u0004\u0018\u00010\u00018\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b0\u0010\u0018R\u0011\u00101\u001a\u00020+8F\u00a2\u0006\u0006\u001a\u0004\b2\u0010-R\u0018\u0010\t\u001a\u0004\u0018\u00010\u00018\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b3\u0010\u0018R\u0011\u00104\u001a\u00020\u00038F\u00a2\u0006\u0006\u001a\u0004\b5\u0010\u0016R\u0018\u0010\u000b\u001a\u0004\u0018\u00010\u00018\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b6\u0010\u0018\u00a8\u0006L"}, d2 = {"Lcom/riskcare/app/data/models/LeaderboardEntry;", "", "id", "", "firstName", "", "lastName", "employeeCode", "departmentName", "totalScoreRaw", "correctRaw", "wrongRaw", "skippedRaw", "attemptedRaw", "isMe", "", "name", "points", "rank", "(Ljava/lang/Integer;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;ZLjava/lang/String;II)V", "attempted", "getAttempted", "()I", "getAttemptedRaw", "()Ljava/lang/Object;", "correct", "getCorrect", "getCorrectRaw", "getDepartmentName", "()Ljava/lang/String;", "displayName", "getDisplayName", "getEmployeeCode", "getFirstName", "getId", "()Ljava/lang/Integer;", "Ljava/lang/Integer;", "()Z", "getLastName", "getName", "getPoints", "getRank", "score", "", "getScore", "()D", "scoreDisplay", "getScoreDisplay", "getSkippedRaw", "totalScore", "getTotalScore", "getTotalScoreRaw", "wrong", "getWrong", "getWrongRaw", "component1", "component10", "component11", "component12", "component13", "component14", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "(Ljava/lang/Integer;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;ZLjava/lang/String;II)Lcom/riskcare/app/data/models/LeaderboardEntry;", "equals", "other", "hashCode", "toString", "toSafeDouble", "app_debug"})
public final class LeaderboardEntry {
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Integer id = null;
    @com.google.gson.annotations.SerializedName(value = "first_name")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String firstName = null;
    @com.google.gson.annotations.SerializedName(value = "last_name")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String lastName = null;
    @com.google.gson.annotations.SerializedName(value = "employee_code")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String employeeCode = null;
    @com.google.gson.annotations.SerializedName(value = "department_name")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String departmentName = null;
    @com.google.gson.annotations.SerializedName(value = "total_score")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Object totalScoreRaw = null;
    @com.google.gson.annotations.SerializedName(value = "correct")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Object correctRaw = null;
    @com.google.gson.annotations.SerializedName(value = "wrong")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Object wrongRaw = null;
    @com.google.gson.annotations.SerializedName(value = "skipped")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Object skippedRaw = null;
    @com.google.gson.annotations.SerializedName(value = "attempted")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Object attemptedRaw = null;
    @com.google.gson.annotations.SerializedName(value = "is_me")
    private final boolean isMe = false;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String name = null;
    private final int points = 0;
    private final int rank = 0;
    
    public LeaderboardEntry(@org.jetbrains.annotations.Nullable()
    java.lang.Integer id, @org.jetbrains.annotations.Nullable()
    java.lang.String firstName, @org.jetbrains.annotations.Nullable()
    java.lang.String lastName, @org.jetbrains.annotations.Nullable()
    java.lang.String employeeCode, @org.jetbrains.annotations.Nullable()
    java.lang.String departmentName, @org.jetbrains.annotations.Nullable()
    java.lang.Object totalScoreRaw, @org.jetbrains.annotations.Nullable()
    java.lang.Object correctRaw, @org.jetbrains.annotations.Nullable()
    java.lang.Object wrongRaw, @org.jetbrains.annotations.Nullable()
    java.lang.Object skippedRaw, @org.jetbrains.annotations.Nullable()
    java.lang.Object attemptedRaw, boolean isMe, @org.jetbrains.annotations.Nullable()
    java.lang.String name, int points, int rank) {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer getId() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getFirstName() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getLastName() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getEmployeeCode() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getDepartmentName() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getTotalScoreRaw() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getCorrectRaw() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getWrongRaw() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getSkippedRaw() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getAttemptedRaw() {
        return null;
    }
    
    public final boolean isMe() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getName() {
        return null;
    }
    
    public final int getPoints() {
        return 0;
    }
    
    public final int getRank() {
        return 0;
    }
    
    private final double toSafeDouble(java.lang.Object $this$toSafeDouble) {
        return 0.0;
    }
    
    public final double getTotalScore() {
        return 0.0;
    }
    
    public final int getCorrect() {
        return 0;
    }
    
    public final int getWrong() {
        return 0;
    }
    
    public final int getAttempted() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getDisplayName() {
        return null;
    }
    
    public final double getScore() {
        return 0.0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getScoreDisplay() {
        return null;
    }
    
    public LeaderboardEntry() {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer component1() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object component10() {
        return null;
    }
    
    public final boolean component11() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component12() {
        return null;
    }
    
    public final int component13() {
        return 0;
    }
    
    public final int component14() {
        return 0;
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
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object component6() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object component7() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object component8() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object component9() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.riskcare.app.data.models.LeaderboardEntry copy(@org.jetbrains.annotations.Nullable()
    java.lang.Integer id, @org.jetbrains.annotations.Nullable()
    java.lang.String firstName, @org.jetbrains.annotations.Nullable()
    java.lang.String lastName, @org.jetbrains.annotations.Nullable()
    java.lang.String employeeCode, @org.jetbrains.annotations.Nullable()
    java.lang.String departmentName, @org.jetbrains.annotations.Nullable()
    java.lang.Object totalScoreRaw, @org.jetbrains.annotations.Nullable()
    java.lang.Object correctRaw, @org.jetbrains.annotations.Nullable()
    java.lang.Object wrongRaw, @org.jetbrains.annotations.Nullable()
    java.lang.Object skippedRaw, @org.jetbrains.annotations.Nullable()
    java.lang.Object attemptedRaw, boolean isMe, @org.jetbrains.annotations.Nullable()
    java.lang.String name, int points, int rank) {
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