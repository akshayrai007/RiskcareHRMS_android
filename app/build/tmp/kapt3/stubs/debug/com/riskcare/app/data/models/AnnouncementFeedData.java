package com.riskcare.app.data.models;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001BG\u0012\u0010\b\u0002\u0010\u0002\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u0003\u0012\u0010\b\u0002\u0010\u0005\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0003\u0012\u0010\b\u0002\u0010\u0007\u001a\n\u0012\u0004\u0012\u00020\b\u0018\u00010\u0003\u0012\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\n\u00a2\u0006\u0002\u0010\u000bJ\u0011\u0010\u0012\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u0003H\u00c6\u0003J\u0011\u0010\u0013\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0003H\u00c6\u0003J\u0011\u0010\u0014\u001a\n\u0012\u0004\u0012\u00020\b\u0018\u00010\u0003H\u00c6\u0003J\u000b\u0010\u0015\u001a\u0004\u0018\u00010\nH\u00c6\u0003JK\u0010\u0016\u001a\u00020\u00002\u0010\b\u0002\u0010\u0002\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u00032\u0010\b\u0002\u0010\u0005\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u00032\u0010\b\u0002\u0010\u0007\u001a\n\u0012\u0004\u0012\u00020\b\u0018\u00010\u00032\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\nH\u00c6\u0001J\u0013\u0010\u0017\u001a\u00020\u00182\b\u0010\u0019\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u001a\u001a\u00020\u001bH\u00d6\u0001J\t\u0010\u001c\u001a\u00020\u001dH\u00d6\u0001R\u0019\u0010\u0002\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0019\u0010\u0005\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\rR\u0018\u0010\t\u001a\u0004\u0018\u00010\n8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u001e\u0010\u0007\u001a\n\u0012\u0004\u0012\u00020\b\u0018\u00010\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\r\u00a8\u0006\u001e"}, d2 = {"Lcom/riskcare/app/data/models/AnnouncementFeedData;", "", "announcements", "", "Lcom/riskcare/app/data/models/Announcement;", "birthdays", "Lcom/riskcare/app/data/models/BirthdayRecord;", "upcomingHolidays", "Lcom/riskcare/app/data/models/Holiday;", "thoughtOfDay", "Lcom/riskcare/app/data/models/ThoughtData;", "(Ljava/util/List;Ljava/util/List;Ljava/util/List;Lcom/riskcare/app/data/models/ThoughtData;)V", "getAnnouncements", "()Ljava/util/List;", "getBirthdays", "getThoughtOfDay", "()Lcom/riskcare/app/data/models/ThoughtData;", "getUpcomingHolidays", "component1", "component2", "component3", "component4", "copy", "equals", "", "other", "hashCode", "", "toString", "", "app_debug"})
public final class AnnouncementFeedData {
    @org.jetbrains.annotations.Nullable()
    private final java.util.List<com.riskcare.app.data.models.Announcement> announcements = null;
    @org.jetbrains.annotations.Nullable()
    private final java.util.List<com.riskcare.app.data.models.BirthdayRecord> birthdays = null;
    @com.google.gson.annotations.SerializedName(value = "upcoming_holidays")
    @org.jetbrains.annotations.Nullable()
    private final java.util.List<com.riskcare.app.data.models.Holiday> upcomingHolidays = null;
    @com.google.gson.annotations.SerializedName(value = "thought_of_day")
    @org.jetbrains.annotations.Nullable()
    private final com.riskcare.app.data.models.ThoughtData thoughtOfDay = null;
    
    public AnnouncementFeedData(@org.jetbrains.annotations.Nullable()
    java.util.List<com.riskcare.app.data.models.Announcement> announcements, @org.jetbrains.annotations.Nullable()
    java.util.List<com.riskcare.app.data.models.BirthdayRecord> birthdays, @org.jetbrains.annotations.Nullable()
    java.util.List<com.riskcare.app.data.models.Holiday> upcomingHolidays, @org.jetbrains.annotations.Nullable()
    com.riskcare.app.data.models.ThoughtData thoughtOfDay) {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.util.List<com.riskcare.app.data.models.Announcement> getAnnouncements() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.util.List<com.riskcare.app.data.models.BirthdayRecord> getBirthdays() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.util.List<com.riskcare.app.data.models.Holiday> getUpcomingHolidays() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.riskcare.app.data.models.ThoughtData getThoughtOfDay() {
        return null;
    }
    
    public AnnouncementFeedData() {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.util.List<com.riskcare.app.data.models.Announcement> component1() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.util.List<com.riskcare.app.data.models.BirthdayRecord> component2() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.util.List<com.riskcare.app.data.models.Holiday> component3() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.riskcare.app.data.models.ThoughtData component4() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.riskcare.app.data.models.AnnouncementFeedData copy(@org.jetbrains.annotations.Nullable()
    java.util.List<com.riskcare.app.data.models.Announcement> announcements, @org.jetbrains.annotations.Nullable()
    java.util.List<com.riskcare.app.data.models.BirthdayRecord> birthdays, @org.jetbrains.annotations.Nullable()
    java.util.List<com.riskcare.app.data.models.Holiday> upcomingHolidays, @org.jetbrains.annotations.Nullable()
    com.riskcare.app.data.models.ThoughtData thoughtOfDay) {
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