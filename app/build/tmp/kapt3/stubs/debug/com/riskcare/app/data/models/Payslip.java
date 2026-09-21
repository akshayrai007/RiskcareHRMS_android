package com.riskcare.app.data.models;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\u0006\n\u0002\b\u001c\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\bz\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0086\b\u0018\u00002\u00020\u0001B\u00ef\u0004\u0012\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u0012\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u0007\u0012\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u0007\u0012\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u0007\u0012\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u0007\u0012\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\r\u0012\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\r\u0012\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\r\u0012\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\r\u0012\n\b\u0002\u0010\u0011\u001a\u0004\u0018\u00010\r\u0012\n\b\u0002\u0010\u0012\u001a\u0004\u0018\u00010\r\u0012\b\b\u0002\u0010\u0013\u001a\u00020\r\u0012\b\b\u0002\u0010\u0014\u001a\u00020\r\u0012\b\b\u0002\u0010\u0015\u001a\u00020\r\u0012\n\b\u0002\u0010\u0016\u001a\u0004\u0018\u00010\r\u0012\n\b\u0002\u0010\u0017\u001a\u0004\u0018\u00010\r\u0012\n\b\u0002\u0010\u0018\u001a\u0004\u0018\u00010\r\u0012\n\b\u0002\u0010\u0019\u001a\u0004\u0018\u00010\r\u0012\n\b\u0002\u0010\u001a\u001a\u0004\u0018\u00010\r\u0012\n\b\u0002\u0010\u001b\u001a\u0004\u0018\u00010\r\u0012\n\b\u0002\u0010\u001c\u001a\u0004\u0018\u00010\r\u0012\n\b\u0002\u0010\u001d\u001a\u0004\u0018\u00010\r\u0012\n\b\u0002\u0010\u001e\u001a\u0004\u0018\u00010\r\u0012\n\b\u0002\u0010\u001f\u001a\u0004\u0018\u00010\r\u0012\n\b\u0002\u0010 \u001a\u0004\u0018\u00010\r\u0012\n\b\u0002\u0010!\u001a\u0004\u0018\u00010\r\u0012\n\b\u0002\u0010\"\u001a\u0004\u0018\u00010\r\u0012\n\b\u0002\u0010#\u001a\u0004\u0018\u00010\r\u0012\n\b\u0002\u0010$\u001a\u0004\u0018\u00010\r\u0012\n\b\u0002\u0010%\u001a\u0004\u0018\u00010\r\u0012\n\b\u0002\u0010&\u001a\u0004\u0018\u00010\r\u0012\n\b\u0002\u0010\'\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010(\u001a\u0004\u0018\u00010\u0003\u0012\u0010\b\u0002\u0010)\u001a\n\u0012\u0004\u0012\u00020+\u0018\u00010*\u0012\n\b\u0002\u0010,\u001a\u0004\u0018\u00010\u0007\u0012\n\b\u0002\u0010-\u001a\u0004\u0018\u00010\u0007\u0012\n\b\u0002\u0010.\u001a\u0004\u0018\u00010\u0007\u0012\n\b\u0002\u0010/\u001a\u0004\u0018\u00010\u0007\u0012\n\b\u0002\u00100\u001a\u0004\u0018\u00010\u0007\u0012\n\b\u0002\u00101\u001a\u0004\u0018\u00010\u0007\u0012\n\b\u0002\u00102\u001a\u0004\u0018\u00010\u0007\u0012\n\b\u0002\u00103\u001a\u0004\u0018\u00010\u0007\u0012\n\b\u0002\u00104\u001a\u0004\u0018\u00010\r\u0012\n\b\u0002\u00105\u001a\u0004\u0018\u00010\r\u0012\n\b\u0002\u00106\u001a\u0004\u0018\u00010\r\u0012\n\b\u0002\u00107\u001a\u0004\u0018\u00010\r\u0012\n\b\u0002\u00108\u001a\u0004\u0018\u00010\r\u0012\u0010\b\u0002\u00109\u001a\n\u0012\u0004\u0012\u00020:\u0018\u00010*\u00a2\u0006\u0002\u0010;J\f\u0010~\u001a\b\u0012\u0004\u0012\u00020+0*J\u0010\u0010\u007f\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003\u00a2\u0006\u0002\u0010JJ\u0011\u0010\u0080\u0001\u001a\u0004\u0018\u00010\rH\u00c6\u0003\u00a2\u0006\u0002\u0010=J\u0011\u0010\u0081\u0001\u001a\u0004\u0018\u00010\rH\u00c6\u0003\u00a2\u0006\u0002\u0010=J\u0011\u0010\u0082\u0001\u001a\u0004\u0018\u00010\rH\u00c6\u0003\u00a2\u0006\u0002\u0010=J\u0011\u0010\u0083\u0001\u001a\u0004\u0018\u00010\rH\u00c6\u0003\u00a2\u0006\u0002\u0010=J\u0011\u0010\u0084\u0001\u001a\u0004\u0018\u00010\rH\u00c6\u0003\u00a2\u0006\u0002\u0010=J\n\u0010\u0085\u0001\u001a\u00020\rH\u00c6\u0003J\n\u0010\u0086\u0001\u001a\u00020\rH\u00c6\u0003J\n\u0010\u0087\u0001\u001a\u00020\rH\u00c6\u0003J\u0011\u0010\u0088\u0001\u001a\u0004\u0018\u00010\rH\u00c6\u0003\u00a2\u0006\u0002\u0010=J\u0011\u0010\u0089\u0001\u001a\u0004\u0018\u00010\rH\u00c6\u0003\u00a2\u0006\u0002\u0010=J\u0011\u0010\u008a\u0001\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003\u00a2\u0006\u0002\u0010JJ\u0011\u0010\u008b\u0001\u001a\u0004\u0018\u00010\rH\u00c6\u0003\u00a2\u0006\u0002\u0010=J\u0011\u0010\u008c\u0001\u001a\u0004\u0018\u00010\rH\u00c6\u0003\u00a2\u0006\u0002\u0010=J\u0011\u0010\u008d\u0001\u001a\u0004\u0018\u00010\rH\u00c6\u0003\u00a2\u0006\u0002\u0010=J\u0011\u0010\u008e\u0001\u001a\u0004\u0018\u00010\rH\u00c6\u0003\u00a2\u0006\u0002\u0010=J\u0011\u0010\u008f\u0001\u001a\u0004\u0018\u00010\rH\u00c6\u0003\u00a2\u0006\u0002\u0010=J\u0011\u0010\u0090\u0001\u001a\u0004\u0018\u00010\rH\u00c6\u0003\u00a2\u0006\u0002\u0010=J\u0011\u0010\u0091\u0001\u001a\u0004\u0018\u00010\rH\u00c6\u0003\u00a2\u0006\u0002\u0010=J\u0011\u0010\u0092\u0001\u001a\u0004\u0018\u00010\rH\u00c6\u0003\u00a2\u0006\u0002\u0010=J\u0011\u0010\u0093\u0001\u001a\u0004\u0018\u00010\rH\u00c6\u0003\u00a2\u0006\u0002\u0010=J\u0011\u0010\u0094\u0001\u001a\u0004\u0018\u00010\rH\u00c6\u0003\u00a2\u0006\u0002\u0010=J\u0011\u0010\u0095\u0001\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003\u00a2\u0006\u0002\u0010JJ\u0011\u0010\u0096\u0001\u001a\u0004\u0018\u00010\rH\u00c6\u0003\u00a2\u0006\u0002\u0010=J\u0011\u0010\u0097\u0001\u001a\u0004\u0018\u00010\rH\u00c6\u0003\u00a2\u0006\u0002\u0010=J\u0011\u0010\u0098\u0001\u001a\u0004\u0018\u00010\rH\u00c6\u0003\u00a2\u0006\u0002\u0010=J\u0011\u0010\u0099\u0001\u001a\u0004\u0018\u00010\rH\u00c6\u0003\u00a2\u0006\u0002\u0010=J\u0011\u0010\u009a\u0001\u001a\u0004\u0018\u00010\rH\u00c6\u0003\u00a2\u0006\u0002\u0010=J\u0011\u0010\u009b\u0001\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003\u00a2\u0006\u0002\u0010JJ\u0011\u0010\u009c\u0001\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003\u00a2\u0006\u0002\u0010JJ\u0012\u0010\u009d\u0001\u001a\n\u0012\u0004\u0012\u00020+\u0018\u00010*H\u00c6\u0003J\f\u0010\u009e\u0001\u001a\u0004\u0018\u00010\u0007H\u00c6\u0003J\f\u0010\u009f\u0001\u001a\u0004\u0018\u00010\u0007H\u00c6\u0003J\f\u0010\u00a0\u0001\u001a\u0004\u0018\u00010\u0007H\u00c6\u0003J\f\u0010\u00a1\u0001\u001a\u0004\u0018\u00010\u0007H\u00c6\u0003J\f\u0010\u00a2\u0001\u001a\u0004\u0018\u00010\u0007H\u00c6\u0003J\f\u0010\u00a3\u0001\u001a\u0004\u0018\u00010\u0007H\u00c6\u0003J\f\u0010\u00a4\u0001\u001a\u0004\u0018\u00010\u0007H\u00c6\u0003J\f\u0010\u00a5\u0001\u001a\u0004\u0018\u00010\u0007H\u00c6\u0003J\f\u0010\u00a6\u0001\u001a\u0004\u0018\u00010\u0007H\u00c6\u0003J\u0011\u0010\u00a7\u0001\u001a\u0004\u0018\u00010\rH\u00c6\u0003\u00a2\u0006\u0002\u0010=J\u0011\u0010\u00a8\u0001\u001a\u0004\u0018\u00010\rH\u00c6\u0003\u00a2\u0006\u0002\u0010=J\u0011\u0010\u00a9\u0001\u001a\u0004\u0018\u00010\rH\u00c6\u0003\u00a2\u0006\u0002\u0010=J\u0011\u0010\u00aa\u0001\u001a\u0004\u0018\u00010\rH\u00c6\u0003\u00a2\u0006\u0002\u0010=J\f\u0010\u00ab\u0001\u001a\u0004\u0018\u00010\u0007H\u00c6\u0003J\u0011\u0010\u00ac\u0001\u001a\u0004\u0018\u00010\rH\u00c6\u0003\u00a2\u0006\u0002\u0010=J\u0012\u0010\u00ad\u0001\u001a\n\u0012\u0004\u0012\u00020:\u0018\u00010*H\u00c6\u0003J\f\u0010\u00ae\u0001\u001a\u0004\u0018\u00010\u0007H\u00c6\u0003J\f\u0010\u00af\u0001\u001a\u0004\u0018\u00010\u0007H\u00c6\u0003J\f\u0010\u00b0\u0001\u001a\u0004\u0018\u00010\u0007H\u00c6\u0003J\u0011\u0010\u00b1\u0001\u001a\u0004\u0018\u00010\rH\u00c6\u0003\u00a2\u0006\u0002\u0010=J\u00fa\u0004\u0010\u00b2\u0001\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00072\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u00072\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u00072\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u00072\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u00072\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\r2\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\r2\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\r2\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\r2\n\b\u0002\u0010\u0011\u001a\u0004\u0018\u00010\r2\n\b\u0002\u0010\u0012\u001a\u0004\u0018\u00010\r2\b\b\u0002\u0010\u0013\u001a\u00020\r2\b\b\u0002\u0010\u0014\u001a\u00020\r2\b\b\u0002\u0010\u0015\u001a\u00020\r2\n\b\u0002\u0010\u0016\u001a\u0004\u0018\u00010\r2\n\b\u0002\u0010\u0017\u001a\u0004\u0018\u00010\r2\n\b\u0002\u0010\u0018\u001a\u0004\u0018\u00010\r2\n\b\u0002\u0010\u0019\u001a\u0004\u0018\u00010\r2\n\b\u0002\u0010\u001a\u001a\u0004\u0018\u00010\r2\n\b\u0002\u0010\u001b\u001a\u0004\u0018\u00010\r2\n\b\u0002\u0010\u001c\u001a\u0004\u0018\u00010\r2\n\b\u0002\u0010\u001d\u001a\u0004\u0018\u00010\r2\n\b\u0002\u0010\u001e\u001a\u0004\u0018\u00010\r2\n\b\u0002\u0010\u001f\u001a\u0004\u0018\u00010\r2\n\b\u0002\u0010 \u001a\u0004\u0018\u00010\r2\n\b\u0002\u0010!\u001a\u0004\u0018\u00010\r2\n\b\u0002\u0010\"\u001a\u0004\u0018\u00010\r2\n\b\u0002\u0010#\u001a\u0004\u0018\u00010\r2\n\b\u0002\u0010$\u001a\u0004\u0018\u00010\r2\n\b\u0002\u0010%\u001a\u0004\u0018\u00010\r2\n\b\u0002\u0010&\u001a\u0004\u0018\u00010\r2\n\b\u0002\u0010\'\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010(\u001a\u0004\u0018\u00010\u00032\u0010\b\u0002\u0010)\u001a\n\u0012\u0004\u0012\u00020+\u0018\u00010*2\n\b\u0002\u0010,\u001a\u0004\u0018\u00010\u00072\n\b\u0002\u0010-\u001a\u0004\u0018\u00010\u00072\n\b\u0002\u0010.\u001a\u0004\u0018\u00010\u00072\n\b\u0002\u0010/\u001a\u0004\u0018\u00010\u00072\n\b\u0002\u00100\u001a\u0004\u0018\u00010\u00072\n\b\u0002\u00101\u001a\u0004\u0018\u00010\u00072\n\b\u0002\u00102\u001a\u0004\u0018\u00010\u00072\n\b\u0002\u00103\u001a\u0004\u0018\u00010\u00072\n\b\u0002\u00104\u001a\u0004\u0018\u00010\r2\n\b\u0002\u00105\u001a\u0004\u0018\u00010\r2\n\b\u0002\u00106\u001a\u0004\u0018\u00010\r2\n\b\u0002\u00107\u001a\u0004\u0018\u00010\r2\n\b\u0002\u00108\u001a\u0004\u0018\u00010\r2\u0010\b\u0002\u00109\u001a\n\u0012\u0004\u0012\u00020:\u0018\u00010*H\u00c6\u0001\u00a2\u0006\u0003\u0010\u00b3\u0001J\u0016\u0010\u00b4\u0001\u001a\u00030\u00b5\u00012\t\u0010\u00b6\u0001\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\n\u0010\u00b7\u0001\u001a\u00020\u0003H\u00d6\u0001J\n\u0010\u00b8\u0001\u001a\u00020\u0007H\u00d6\u0001R\u001a\u0010%\u001a\u0004\u0018\u00010\r8\u0006X\u0087\u0004\u00a2\u0006\n\n\u0002\u0010>\u001a\u0004\b<\u0010=R\u001a\u0010\u001f\u001a\u0004\u0018\u00010\r8\u0006X\u0087\u0004\u00a2\u0006\n\n\u0002\u0010>\u001a\u0004\b?\u0010=R\u0018\u00100\u001a\u0004\u0018\u00010\u00078\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b@\u0010AR\u0018\u00101\u001a\u0004\u0018\u00010\u00078\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\bB\u0010AR\u0018\u0010/\u001a\u0004\u0018\u00010\u00078\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\bC\u0010AR\u0015\u0010\f\u001a\u0004\u0018\u00010\r\u00a2\u0006\n\n\u0002\u0010>\u001a\u0004\bD\u0010=R\u001a\u0010&\u001a\u0004\u0018\u00010\r8\u0006X\u0087\u0004\u00a2\u0006\n\n\u0002\u0010>\u001a\u0004\bE\u0010=R\u0019\u0010)\u001a\n\u0012\u0004\u0012\u00020+\u0018\u00010*\u00a2\u0006\b\n\u0000\u001a\u0004\bF\u0010GR\u0015\u0010\u000f\u001a\u0004\u0018\u00010\r\u00a2\u0006\n\n\u0002\u0010>\u001a\u0004\bH\u0010=R\u001a\u0010(\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004\u00a2\u0006\n\n\u0002\u0010K\u001a\u0004\bI\u0010JR\u001a\u0010\'\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004\u00a2\u0006\n\n\u0002\u0010K\u001a\u0004\bL\u0010JR\u0018\u0010\n\u001a\u0004\u0018\u00010\u00078\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\bM\u0010AR\u0018\u0010\u000b\u001a\u0004\u0018\u00010\u00078\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\bN\u0010AR\u0011\u0010O\u001a\u00020\r8F\u00a2\u0006\u0006\u001a\u0004\bP\u0010QR\u0011\u0010R\u001a\u00020\r8F\u00a2\u0006\u0006\u001a\u0004\bS\u0010QR\u0011\u0010T\u001a\u00020\r8F\u00a2\u0006\u0006\u001a\u0004\bU\u0010QR\u001a\u0010\u001e\u001a\u0004\u0018\u00010\r8\u0006X\u0087\u0004\u00a2\u0006\n\n\u0002\u0010>\u001a\u0004\bV\u0010=R\u0018\u0010\t\u001a\u0004\u0018\u00010\u00078\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\bW\u0010AR\u0018\u0010\b\u001a\u0004\u0018\u00010\u00078\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\bX\u0010AR\u001a\u0010\u001a\u001a\u0004\u0018\u00010\r8\u0006X\u0087\u0004\u00a2\u0006\n\n\u0002\u0010>\u001a\u0004\bY\u0010=R\u001a\u00104\u001a\u0004\u0018\u00010\r8\u0006X\u0087\u0004\u00a2\u0006\n\n\u0002\u0010>\u001a\u0004\bZ\u0010=R\u001a\u00106\u001a\u0004\u0018\u00010\r8\u0006X\u0087\u0004\u00a2\u0006\n\n\u0002\u0010>\u001a\u0004\b[\u0010=R\u001a\u00108\u001a\u0004\u0018\u00010\r8\u0006X\u0087\u0004\u00a2\u0006\n\n\u0002\u0010>\u001a\u0004\b\\\u0010=R\u001a\u00105\u001a\u0004\u0018\u00010\r8\u0006X\u0087\u0004\u00a2\u0006\n\n\u0002\u0010>\u001a\u0004\b]\u0010=R\u001a\u00107\u001a\u0004\u0018\u00010\r8\u0006X\u0087\u0004\u00a2\u0006\n\n\u0002\u0010>\u001a\u0004\b^\u0010=R\u001a\u0010\u0012\u001a\u0004\u0018\u00010\r8\u0006X\u0087\u0004\u00a2\u0006\n\n\u0002\u0010>\u001a\u0004\b_\u0010=R\u0015\u0010\u0011\u001a\u0004\u0018\u00010\r\u00a2\u0006\n\n\u0002\u0010>\u001a\u0004\b`\u0010=R\u0016\u0010\u0013\u001a\u00020\r8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\ba\u0010QR\u0015\u0010\u000e\u001a\u0004\u0018\u00010\r\u00a2\u0006\n\n\u0002\u0010>\u001a\u0004\bb\u0010=R\u0015\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\n\n\u0002\u0010K\u001a\u0004\bc\u0010JR\u001e\u00109\u001a\n\u0012\u0004\u0012\u00020:\u0018\u00010*8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\bd\u0010GR\u001a\u0010$\u001a\u0004\u0018\u00010\r8\u0006X\u0087\u0004\u00a2\u0006\n\n\u0002\u0010>\u001a\u0004\be\u0010=R\u0015\u0010\u001d\u001a\u0004\u0018\u00010\r\u00a2\u0006\n\n\u0002\u0010>\u001a\u0004\bf\u0010=R\u0015\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\n\n\u0002\u0010K\u001a\u0004\bg\u0010JR\u0016\u0010\u0014\u001a\u00020\r8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\bh\u0010QR\u001a\u0010\u0017\u001a\u0004\u0018\u00010\r8\u0006X\u0087\u0004\u00a2\u0006\n\n\u0002\u0010>\u001a\u0004\bi\u0010=R\u001a\u0010\"\u001a\u0004\u0018\u00010\r8\u0006X\u0087\u0004\u00a2\u0006\n\n\u0002\u0010>\u001a\u0004\bj\u0010=R\u0018\u0010.\u001a\u0004\u0018\u00010\u00078\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\bk\u0010AR\u001a\u0010 \u001a\u0004\u0018\u00010\r8\u0006X\u0087\u0004\u00a2\u0006\n\n\u0002\u0010>\u001a\u0004\bl\u0010=R\u001a\u0010\u0018\u001a\u0004\u0018\u00010\r8\u0006X\u0087\u0004\u00a2\u0006\n\n\u0002\u0010>\u001a\u0004\bm\u0010=R\u001a\u0010\u0019\u001a\u0004\u0018\u00010\r8\u0006X\u0087\u0004\u00a2\u0006\n\n\u0002\u0010>\u001a\u0004\bn\u0010=R\u0018\u0010-\u001a\u0004\u0018\u00010\u00078\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\bo\u0010AR\u001a\u0010!\u001a\u0004\u0018\u00010\r8\u0006X\u0087\u0004\u00a2\u0006\n\n\u0002\u0010>\u001a\u0004\bp\u0010=R\u001a\u0010\u001b\u001a\u0004\u0018\u00010\r8\u0006X\u0087\u0004\u00a2\u0006\n\n\u0002\u0010>\u001a\u0004\bq\u0010=R\u0018\u00102\u001a\u0004\u0018\u00010\u00078\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\br\u0010AR\u0018\u00103\u001a\u0004\u0018\u00010\u00078\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\bs\u0010AR\u001a\u0010\u0010\u001a\u0004\u0018\u00010\r8\u0006X\u0087\u0004\u00a2\u0006\n\n\u0002\u0010>\u001a\u0004\bt\u0010=R\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\bu\u0010AR\u0015\u0010\u001c\u001a\u0004\u0018\u00010\r\u00a2\u0006\n\n\u0002\u0010>\u001a\u0004\bv\u0010=R\u0016\u0010\u0015\u001a\u00020\r8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\bw\u0010QR\u001a\u0010\u0016\u001a\u0004\u0018\u00010\r8\u0006X\u0087\u0004\u00a2\u0006\n\n\u0002\u0010>\u001a\u0004\bx\u0010=R\u0011\u0010y\u001a\u00020\r8F\u00a2\u0006\u0006\u001a\u0004\bz\u0010QR\u0018\u0010,\u001a\u0004\u0018\u00010\u00078\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b{\u0010AR\u001a\u0010#\u001a\u0004\u0018\u00010\r8\u0006X\u0087\u0004\u00a2\u0006\n\n\u0002\u0010>\u001a\u0004\b|\u0010=R\u0015\u0010\u0005\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\n\n\u0002\u0010K\u001a\u0004\b}\u0010J\u00a8\u0006\u00b9\u0001"}, d2 = {"Lcom/riskcare/app/data/models/Payslip;", "", "id", "", "month", "year", "status", "", "employeeName", "employeeCode", "departmentName", "designationTitle", "basic", "", "hra", "conveyance", "specialAllowance", "gratuity", "foodCoupon", "grossSalary", "netSalary", "totalDeductions", "totalDeductionsDisplay", "netSalaryDisplay", "pfEmployee", "pfEmployer", "esiEmployee", "professionalTax", "tds", "lwf", "emiRecovery", "adminCharges", "pfAdmin", "presentDays", "paidDays", "workingDays", "lopDays", "absentDays", "basicSalary", "daysPresent", "daysAbsent", "components", "", "Lcom/riskcare/app/data/models/PayComponent;", "uanNumber", "pfNumber", "panNumber", "bankName", "bankAccount", "bankIfsc", "slipDob", "slipDoj", "fixedBasic", "fixedHra", "fixedConveyance", "fixedSpecialAllowance", "fixedGratuity", "leaveBalances", "Lcom/riskcare/app/data/models/PayslipLeaveBalance;", "(Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;DDDLjava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Integer;Ljava/lang/Integer;Ljava/util/List;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/util/List;)V", "getAbsentDays", "()Ljava/lang/Double;", "Ljava/lang/Double;", "getAdminCharges", "getBankAccount", "()Ljava/lang/String;", "getBankIfsc", "getBankName", "getBasic", "getBasicSalary", "getComponents", "()Ljava/util/List;", "getConveyance", "getDaysAbsent", "()Ljava/lang/Integer;", "Ljava/lang/Integer;", "getDaysPresent", "getDepartmentName", "getDesignationTitle", "effectiveDed", "getEffectiveDed", "()D", "effectiveGross", "getEffectiveGross", "effectiveNet", "getEffectiveNet", "getEmiRecovery", "getEmployeeCode", "getEmployeeName", "getEsiEmployee", "getFixedBasic", "getFixedConveyance", "getFixedGratuity", "getFixedHra", "getFixedSpecialAllowance", "getFoodCoupon", "getGratuity", "getGrossSalary", "getHra", "getId", "getLeaveBalances", "getLopDays", "getLwf", "getMonth", "getNetSalary", "getNetSalaryDisplay", "getPaidDays", "getPanNumber", "getPfAdmin", "getPfEmployee", "getPfEmployer", "getPfNumber", "getPresentDays", "getProfessionalTax", "getSlipDob", "getSlipDoj", "getSpecialAllowance", "getStatus", "getTds", "getTotalDeductions", "getTotalDeductionsDisplay", "totalEarning", "getTotalEarning", "getUanNumber", "getWorkingDays", "getYear", "buildComponents", "component1", "component10", "component11", "component12", "component13", "component14", "component15", "component16", "component17", "component18", "component19", "component2", "component20", "component21", "component22", "component23", "component24", "component25", "component26", "component27", "component28", "component29", "component3", "component30", "component31", "component32", "component33", "component34", "component35", "component36", "component37", "component38", "component39", "component4", "component40", "component41", "component42", "component43", "component44", "component45", "component46", "component47", "component48", "component49", "component5", "component50", "component51", "component6", "component7", "component8", "component9", "copy", "(Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;DDDLjava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Integer;Ljava/lang/Integer;Ljava/util/List;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/util/List;)Lcom/riskcare/app/data/models/Payslip;", "equals", "", "other", "hashCode", "toString", "app_debug"})
public final class Payslip {
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Integer id = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Integer month = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Integer year = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String status = null;
    @com.google.gson.annotations.SerializedName(value = "employee_name")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String employeeName = null;
    @com.google.gson.annotations.SerializedName(value = "employee_code")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String employeeCode = null;
    @com.google.gson.annotations.SerializedName(value = "department_name")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String departmentName = null;
    @com.google.gson.annotations.SerializedName(value = "designation_title")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String designationTitle = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double basic = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double hra = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double conveyance = null;
    @com.google.gson.annotations.SerializedName(value = "special_allowance")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double specialAllowance = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double gratuity = null;
    @com.google.gson.annotations.SerializedName(value = "other_allowance")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double foodCoupon = null;
    @com.google.gson.annotations.SerializedName(value = "gross_salary")
    private final double grossSalary = 0.0;
    @com.google.gson.annotations.SerializedName(value = "net_salary")
    private final double netSalary = 0.0;
    @com.google.gson.annotations.SerializedName(value = "total_deductions")
    private final double totalDeductions = 0.0;
    @com.google.gson.annotations.SerializedName(value = "total_deductions_display")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double totalDeductionsDisplay = null;
    @com.google.gson.annotations.SerializedName(value = "net_salary_display")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double netSalaryDisplay = null;
    @com.google.gson.annotations.SerializedName(value = "pf_employee")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double pfEmployee = null;
    @com.google.gson.annotations.SerializedName(value = "pf_employer")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double pfEmployer = null;
    @com.google.gson.annotations.SerializedName(value = "esi_employee")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double esiEmployee = null;
    @com.google.gson.annotations.SerializedName(value = "professional_tax")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double professionalTax = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double tds = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double lwf = null;
    @com.google.gson.annotations.SerializedName(value = "loan_emi_recovery")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double emiRecovery = null;
    @com.google.gson.annotations.SerializedName(value = "admin_charges")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double adminCharges = null;
    @com.google.gson.annotations.SerializedName(value = "pf_admin")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double pfAdmin = null;
    @com.google.gson.annotations.SerializedName(value = "present_days")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double presentDays = null;
    @com.google.gson.annotations.SerializedName(value = "paid_days")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double paidDays = null;
    @com.google.gson.annotations.SerializedName(value = "working_days")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double workingDays = null;
    @com.google.gson.annotations.SerializedName(value = "lop_days")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double lopDays = null;
    @com.google.gson.annotations.SerializedName(value = "absent_days")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double absentDays = null;
    @com.google.gson.annotations.SerializedName(value = "basic_salary")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double basicSalary = null;
    @com.google.gson.annotations.SerializedName(value = "days_present")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Integer daysPresent = null;
    @com.google.gson.annotations.SerializedName(value = "days_absent")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Integer daysAbsent = null;
    @org.jetbrains.annotations.Nullable()
    private final java.util.List<com.riskcare.app.data.models.PayComponent> components = null;
    @com.google.gson.annotations.SerializedName(value = "uan_number")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String uanNumber = null;
    @com.google.gson.annotations.SerializedName(value = "pf_number")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String pfNumber = null;
    @com.google.gson.annotations.SerializedName(value = "pan_number")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String panNumber = null;
    @com.google.gson.annotations.SerializedName(value = "bank_name")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String bankName = null;
    @com.google.gson.annotations.SerializedName(value = "bank_account")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String bankAccount = null;
    @com.google.gson.annotations.SerializedName(value = "bank_ifsc")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String bankIfsc = null;
    @com.google.gson.annotations.SerializedName(value = "date_of_birth")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String slipDob = null;
    @com.google.gson.annotations.SerializedName(value = "joining_date")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String slipDoj = null;
    @com.google.gson.annotations.SerializedName(value = "fixed_basic")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double fixedBasic = null;
    @com.google.gson.annotations.SerializedName(value = "fixed_hra")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double fixedHra = null;
    @com.google.gson.annotations.SerializedName(value = "fixed_conveyance")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double fixedConveyance = null;
    @com.google.gson.annotations.SerializedName(value = "fixed_special_allowance")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double fixedSpecialAllowance = null;
    @com.google.gson.annotations.SerializedName(value = "fixed_gratuity")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double fixedGratuity = null;
    @com.google.gson.annotations.SerializedName(value = "leave_balances")
    @org.jetbrains.annotations.Nullable()
    private final java.util.List<com.riskcare.app.data.models.PayslipLeaveBalance> leaveBalances = null;
    
    public Payslip(@org.jetbrains.annotations.Nullable()
    java.lang.Integer id, @org.jetbrains.annotations.Nullable()
    java.lang.Integer month, @org.jetbrains.annotations.Nullable()
    java.lang.Integer year, @org.jetbrains.annotations.Nullable()
    java.lang.String status, @org.jetbrains.annotations.Nullable()
    java.lang.String employeeName, @org.jetbrains.annotations.Nullable()
    java.lang.String employeeCode, @org.jetbrains.annotations.Nullable()
    java.lang.String departmentName, @org.jetbrains.annotations.Nullable()
    java.lang.String designationTitle, @org.jetbrains.annotations.Nullable()
    java.lang.Double basic, @org.jetbrains.annotations.Nullable()
    java.lang.Double hra, @org.jetbrains.annotations.Nullable()
    java.lang.Double conveyance, @org.jetbrains.annotations.Nullable()
    java.lang.Double specialAllowance, @org.jetbrains.annotations.Nullable()
    java.lang.Double gratuity, @org.jetbrains.annotations.Nullable()
    java.lang.Double foodCoupon, double grossSalary, double netSalary, double totalDeductions, @org.jetbrains.annotations.Nullable()
    java.lang.Double totalDeductionsDisplay, @org.jetbrains.annotations.Nullable()
    java.lang.Double netSalaryDisplay, @org.jetbrains.annotations.Nullable()
    java.lang.Double pfEmployee, @org.jetbrains.annotations.Nullable()
    java.lang.Double pfEmployer, @org.jetbrains.annotations.Nullable()
    java.lang.Double esiEmployee, @org.jetbrains.annotations.Nullable()
    java.lang.Double professionalTax, @org.jetbrains.annotations.Nullable()
    java.lang.Double tds, @org.jetbrains.annotations.Nullable()
    java.lang.Double lwf, @org.jetbrains.annotations.Nullable()
    java.lang.Double emiRecovery, @org.jetbrains.annotations.Nullable()
    java.lang.Double adminCharges, @org.jetbrains.annotations.Nullable()
    java.lang.Double pfAdmin, @org.jetbrains.annotations.Nullable()
    java.lang.Double presentDays, @org.jetbrains.annotations.Nullable()
    java.lang.Double paidDays, @org.jetbrains.annotations.Nullable()
    java.lang.Double workingDays, @org.jetbrains.annotations.Nullable()
    java.lang.Double lopDays, @org.jetbrains.annotations.Nullable()
    java.lang.Double absentDays, @org.jetbrains.annotations.Nullable()
    java.lang.Double basicSalary, @org.jetbrains.annotations.Nullable()
    java.lang.Integer daysPresent, @org.jetbrains.annotations.Nullable()
    java.lang.Integer daysAbsent, @org.jetbrains.annotations.Nullable()
    java.util.List<com.riskcare.app.data.models.PayComponent> components, @org.jetbrains.annotations.Nullable()
    java.lang.String uanNumber, @org.jetbrains.annotations.Nullable()
    java.lang.String pfNumber, @org.jetbrains.annotations.Nullable()
    java.lang.String panNumber, @org.jetbrains.annotations.Nullable()
    java.lang.String bankName, @org.jetbrains.annotations.Nullable()
    java.lang.String bankAccount, @org.jetbrains.annotations.Nullable()
    java.lang.String bankIfsc, @org.jetbrains.annotations.Nullable()
    java.lang.String slipDob, @org.jetbrains.annotations.Nullable()
    java.lang.String slipDoj, @org.jetbrains.annotations.Nullable()
    java.lang.Double fixedBasic, @org.jetbrains.annotations.Nullable()
    java.lang.Double fixedHra, @org.jetbrains.annotations.Nullable()
    java.lang.Double fixedConveyance, @org.jetbrains.annotations.Nullable()
    java.lang.Double fixedSpecialAllowance, @org.jetbrains.annotations.Nullable()
    java.lang.Double fixedGratuity, @org.jetbrains.annotations.Nullable()
    java.util.List<com.riskcare.app.data.models.PayslipLeaveBalance> leaveBalances) {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer getId() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer getMonth() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer getYear() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getStatus() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getEmployeeName() {
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
    public final java.lang.String getDesignationTitle() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getBasic() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getHra() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getConveyance() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getSpecialAllowance() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getGratuity() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getFoodCoupon() {
        return null;
    }
    
    public final double getGrossSalary() {
        return 0.0;
    }
    
    public final double getNetSalary() {
        return 0.0;
    }
    
    public final double getTotalDeductions() {
        return 0.0;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getTotalDeductionsDisplay() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getNetSalaryDisplay() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getPfEmployee() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getPfEmployer() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getEsiEmployee() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getProfessionalTax() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getTds() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getLwf() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getEmiRecovery() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getAdminCharges() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getPfAdmin() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getPresentDays() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getPaidDays() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getWorkingDays() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getLopDays() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getAbsentDays() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getBasicSalary() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer getDaysPresent() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer getDaysAbsent() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.util.List<com.riskcare.app.data.models.PayComponent> getComponents() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getUanNumber() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getPfNumber() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getPanNumber() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getBankName() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getBankAccount() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getBankIfsc() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getSlipDob() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getSlipDoj() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getFixedBasic() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getFixedHra() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getFixedConveyance() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getFixedSpecialAllowance() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getFixedGratuity() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.util.List<com.riskcare.app.data.models.PayslipLeaveBalance> getLeaveBalances() {
        return null;
    }
    
    public final double getEffectiveNet() {
        return 0.0;
    }
    
    public final double getEffectiveGross() {
        return 0.0;
    }
    
    public final double getEffectiveDed() {
        return 0.0;
    }
    
    public final double getTotalEarning() {
        return 0.0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.riskcare.app.data.models.PayComponent> buildComponents() {
        return null;
    }
    
    public Payslip() {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer component1() {
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
    
    public final double component15() {
        return 0.0;
    }
    
    public final double component16() {
        return 0.0;
    }
    
    public final double component17() {
        return 0.0;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double component18() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double component19() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer component2() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double component20() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double component21() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double component22() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double component23() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double component24() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double component25() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double component26() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double component27() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double component28() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double component29() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer component3() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double component30() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double component31() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double component32() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double component33() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double component34() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer component35() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer component36() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.util.List<com.riskcare.app.data.models.PayComponent> component37() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component38() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component39() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component4() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component40() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component41() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component42() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component43() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component44() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component45() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double component46() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double component47() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double component48() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double component49() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component5() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double component50() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.util.List<com.riskcare.app.data.models.PayslipLeaveBalance> component51() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component6() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component7() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component8() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double component9() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.riskcare.app.data.models.Payslip copy(@org.jetbrains.annotations.Nullable()
    java.lang.Integer id, @org.jetbrains.annotations.Nullable()
    java.lang.Integer month, @org.jetbrains.annotations.Nullable()
    java.lang.Integer year, @org.jetbrains.annotations.Nullable()
    java.lang.String status, @org.jetbrains.annotations.Nullable()
    java.lang.String employeeName, @org.jetbrains.annotations.Nullable()
    java.lang.String employeeCode, @org.jetbrains.annotations.Nullable()
    java.lang.String departmentName, @org.jetbrains.annotations.Nullable()
    java.lang.String designationTitle, @org.jetbrains.annotations.Nullable()
    java.lang.Double basic, @org.jetbrains.annotations.Nullable()
    java.lang.Double hra, @org.jetbrains.annotations.Nullable()
    java.lang.Double conveyance, @org.jetbrains.annotations.Nullable()
    java.lang.Double specialAllowance, @org.jetbrains.annotations.Nullable()
    java.lang.Double gratuity, @org.jetbrains.annotations.Nullable()
    java.lang.Double foodCoupon, double grossSalary, double netSalary, double totalDeductions, @org.jetbrains.annotations.Nullable()
    java.lang.Double totalDeductionsDisplay, @org.jetbrains.annotations.Nullable()
    java.lang.Double netSalaryDisplay, @org.jetbrains.annotations.Nullable()
    java.lang.Double pfEmployee, @org.jetbrains.annotations.Nullable()
    java.lang.Double pfEmployer, @org.jetbrains.annotations.Nullable()
    java.lang.Double esiEmployee, @org.jetbrains.annotations.Nullable()
    java.lang.Double professionalTax, @org.jetbrains.annotations.Nullable()
    java.lang.Double tds, @org.jetbrains.annotations.Nullable()
    java.lang.Double lwf, @org.jetbrains.annotations.Nullable()
    java.lang.Double emiRecovery, @org.jetbrains.annotations.Nullable()
    java.lang.Double adminCharges, @org.jetbrains.annotations.Nullable()
    java.lang.Double pfAdmin, @org.jetbrains.annotations.Nullable()
    java.lang.Double presentDays, @org.jetbrains.annotations.Nullable()
    java.lang.Double paidDays, @org.jetbrains.annotations.Nullable()
    java.lang.Double workingDays, @org.jetbrains.annotations.Nullable()
    java.lang.Double lopDays, @org.jetbrains.annotations.Nullable()
    java.lang.Double absentDays, @org.jetbrains.annotations.Nullable()
    java.lang.Double basicSalary, @org.jetbrains.annotations.Nullable()
    java.lang.Integer daysPresent, @org.jetbrains.annotations.Nullable()
    java.lang.Integer daysAbsent, @org.jetbrains.annotations.Nullable()
    java.util.List<com.riskcare.app.data.models.PayComponent> components, @org.jetbrains.annotations.Nullable()
    java.lang.String uanNumber, @org.jetbrains.annotations.Nullable()
    java.lang.String pfNumber, @org.jetbrains.annotations.Nullable()
    java.lang.String panNumber, @org.jetbrains.annotations.Nullable()
    java.lang.String bankName, @org.jetbrains.annotations.Nullable()
    java.lang.String bankAccount, @org.jetbrains.annotations.Nullable()
    java.lang.String bankIfsc, @org.jetbrains.annotations.Nullable()
    java.lang.String slipDob, @org.jetbrains.annotations.Nullable()
    java.lang.String slipDoj, @org.jetbrains.annotations.Nullable()
    java.lang.Double fixedBasic, @org.jetbrains.annotations.Nullable()
    java.lang.Double fixedHra, @org.jetbrains.annotations.Nullable()
    java.lang.Double fixedConveyance, @org.jetbrains.annotations.Nullable()
    java.lang.Double fixedSpecialAllowance, @org.jetbrains.annotations.Nullable()
    java.lang.Double fixedGratuity, @org.jetbrains.annotations.Nullable()
    java.util.List<com.riskcare.app.data.models.PayslipLeaveBalance> leaveBalances) {
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