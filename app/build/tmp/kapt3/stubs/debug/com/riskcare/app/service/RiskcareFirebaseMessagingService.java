package com.riskcare.app.service;

/**
 * RiskcareFirebaseMessagingService
 *
 * Handles two FCM events:
 * 1. onNewToken        — device gets a new FCM token -> save to backend
 * 2. onMessageReceived — push arrives -> show a system notification
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0007\u0018\u0000 \u00102\u00020\u0001:\u0001\u0010B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H\u0002J\u0010\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u0007H\u0016J\u0010\u0010\b\u001a\u00020\u00042\u0006\u0010\t\u001a\u00020\nH\u0016J*\u0010\u000b\u001a\u00020\u00042\u0006\u0010\f\u001a\u00020\n2\u0006\u0010\r\u001a\u00020\n2\u0006\u0010\u000e\u001a\u00020\n2\b\u0010\u000f\u001a\u0004\u0018\u00010\nH\u0002\u00a8\u0006\u0011"}, d2 = {"Lcom/riskcare/app/service/RiskcareFirebaseMessagingService;", "Lcom/google/firebase/messaging/FirebaseMessagingService;", "()V", "ensureChannelsExist", "", "onMessageReceived", "message", "Lcom/google/firebase/messaging/RemoteMessage;", "onNewToken", "token", "", "showNotification", "title", "body", "channelId", "screen", "Companion", "app_debug"})
public final class RiskcareFirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String TAG = "RiskcareFCM";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String CHANNEL_GENERAL = "riskcare_general";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String CHANNEL_ALERTS = "riskcare_alerts";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String CHANNEL_CHAT = "riskcare_chat";
    @org.jetbrains.annotations.NotNull()
    public static final com.riskcare.app.service.RiskcareFirebaseMessagingService.Companion Companion = null;
    
    public RiskcareFirebaseMessagingService() {
        super();
    }
    
    @java.lang.Override()
    public void onNewToken(@org.jetbrains.annotations.NotNull()
    java.lang.String token) {
    }
    
    @java.lang.Override()
    public void onMessageReceived(@org.jetbrains.annotations.NotNull()
    com.google.firebase.messaging.RemoteMessage message) {
    }
    
    private final void showNotification(java.lang.String title, java.lang.String body, java.lang.String channelId, java.lang.String screen) {
    }
    
    private final void ensureChannelsExist() {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\b"}, d2 = {"Lcom/riskcare/app/service/RiskcareFirebaseMessagingService$Companion;", "", "()V", "CHANNEL_ALERTS", "", "CHANNEL_CHAT", "CHANNEL_GENERAL", "TAG", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}