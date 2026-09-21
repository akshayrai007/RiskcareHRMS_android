package com.riskcare.app.data.models;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0002\b5\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b}\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0086\b\u0018\u00002\u00020\u0001B\u00f9\u0004\u0012\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0006\u0012\b\b\u0002\u0010\u0007\u001a\u00020\u0006\u0012\b\b\u0002\u0010\b\u001a\u00020\t\u0012\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u0006\u0012\b\b\u0002\u0010\r\u001a\u00020\t\u0012\b\b\u0002\u0010\u000e\u001a\u00020\t\u0012\b\b\u0002\u0010\u000f\u001a\u00020\t\u0012\b\b\u0002\u0010\u0010\u001a\u00020\t\u0012\b\b\u0002\u0010\u0011\u001a\u00020\t\u0012\b\b\u0002\u0010\u0012\u001a\u00020\t\u0012\b\b\u0002\u0010\u0013\u001a\u00020\t\u0012\b\b\u0002\u0010\u0014\u001a\u00020\t\u0012\b\b\u0002\u0010\u0015\u001a\u00020\t\u0012\b\b\u0002\u0010\u0016\u001a\u00020\t\u0012\b\b\u0002\u0010\u0017\u001a\u00020\t\u0012\b\b\u0002\u0010\u0018\u001a\u00020\t\u0012\b\b\u0002\u0010\u0019\u001a\u00020\t\u0012\b\b\u0002\u0010\u001a\u001a\u00020\t\u0012\n\b\u0002\u0010\u001b\u001a\u0004\u0018\u00010\u0006\u0012\b\b\u0002\u0010\u001c\u001a\u00020\t\u0012\b\b\u0002\u0010\u001d\u001a\u00020\t\u0012\n\b\u0002\u0010\u001e\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\u001f\u001a\u0004\u0018\u00010\u0006\u0012\b\b\u0002\u0010 \u001a\u00020\t\u0012\n\b\u0002\u0010!\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\"\u001a\u0004\u0018\u00010\u0006\u0012\b\b\u0002\u0010#\u001a\u00020\t\u0012\b\b\u0002\u0010$\u001a\u00020\t\u0012\n\b\u0002\u0010%\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010&\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\'\u001a\u0004\u0018\u00010\u0006\u0012\b\b\u0002\u0010(\u001a\u00020\t\u0012\b\b\u0002\u0010)\u001a\u00020\t\u0012\n\b\u0002\u0010*\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010+\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010,\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010-\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010.\u001a\u0004\u0018\u00010\u0006\u0012\b\b\u0002\u0010/\u001a\u00020\t\u0012\b\b\u0002\u00100\u001a\u00020\t\u0012\b\b\u0002\u00101\u001a\u00020\t\u0012\b\b\u0002\u00102\u001a\u00020\t\u0012\b\b\u0002\u00103\u001a\u00020\t\u0012\b\b\u0002\u00104\u001a\u00020\t\u0012\b\b\u0002\u00105\u001a\u00020\t\u0012\b\b\u0002\u00106\u001a\u00020\t\u0012\b\b\u0002\u00107\u001a\u00020\t\u0012\b\b\u0002\u00108\u001a\u00020\t\u0012\b\b\u0002\u00109\u001a\u00020\t\u0012\b\b\u0002\u0010:\u001a\u00020\t\u0012\b\b\u0002\u0010;\u001a\u00020\u0006\u0012\n\b\u0002\u0010<\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010=\u001a\u0004\u0018\u00010\u0006\u0012\u0010\b\u0002\u0010>\u001a\n\u0012\u0004\u0012\u00020@\u0018\u00010?\u00a2\u0006\u0002\u0010AJ\u0011\u0010\u0081\u0001\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003\u00a2\u0006\u0002\u0010CJ\n\u0010\u0082\u0001\u001a\u00020\tH\u00c6\u0003J\n\u0010\u0083\u0001\u001a\u00020\tH\u00c6\u0003J\n\u0010\u0084\u0001\u001a\u00020\tH\u00c6\u0003J\n\u0010\u0085\u0001\u001a\u00020\tH\u00c6\u0003J\n\u0010\u0086\u0001\u001a\u00020\tH\u00c6\u0003J\n\u0010\u0087\u0001\u001a\u00020\tH\u00c6\u0003J\n\u0010\u0088\u0001\u001a\u00020\tH\u00c6\u0003J\n\u0010\u0089\u0001\u001a\u00020\tH\u00c6\u0003J\n\u0010\u008a\u0001\u001a\u00020\tH\u00c6\u0003J\n\u0010\u008b\u0001\u001a\u00020\tH\u00c6\u0003J\u0011\u0010\u008c\u0001\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003\u00a2\u0006\u0002\u0010CJ\n\u0010\u008d\u0001\u001a\u00020\tH\u00c6\u0003J\n\u0010\u008e\u0001\u001a\u00020\tH\u00c6\u0003J\n\u0010\u008f\u0001\u001a\u00020\tH\u00c6\u0003J\f\u0010\u0090\u0001\u001a\u0004\u0018\u00010\u0006H\u00c6\u0003J\n\u0010\u0091\u0001\u001a\u00020\tH\u00c6\u0003J\n\u0010\u0092\u0001\u001a\u00020\tH\u00c6\u0003J\f\u0010\u0093\u0001\u001a\u0004\u0018\u00010\u0006H\u00c6\u0003J\f\u0010\u0094\u0001\u001a\u0004\u0018\u00010\u0006H\u00c6\u0003J\n\u0010\u0095\u0001\u001a\u00020\tH\u00c6\u0003J\f\u0010\u0096\u0001\u001a\u0004\u0018\u00010\u0006H\u00c6\u0003J\n\u0010\u0097\u0001\u001a\u00020\u0006H\u00c6\u0003J\f\u0010\u0098\u0001\u001a\u0004\u0018\u00010\u0006H\u00c6\u0003J\n\u0010\u0099\u0001\u001a\u00020\tH\u00c6\u0003J\n\u0010\u009a\u0001\u001a\u00020\tH\u00c6\u0003J\f\u0010\u009b\u0001\u001a\u0004\u0018\u00010\u0006H\u00c6\u0003J\f\u0010\u009c\u0001\u001a\u0004\u0018\u00010\u0006H\u00c6\u0003J\f\u0010\u009d\u0001\u001a\u0004\u0018\u00010\u0006H\u00c6\u0003J\n\u0010\u009e\u0001\u001a\u00020\tH\u00c6\u0003J\n\u0010\u009f\u0001\u001a\u00020\tH\u00c6\u0003J\f\u0010\u00a0\u0001\u001a\u0004\u0018\u00010\u0006H\u00c6\u0003J\f\u0010\u00a1\u0001\u001a\u0004\u0018\u00010\u0006H\u00c6\u0003J\n\u0010\u00a2\u0001\u001a\u00020\u0006H\u00c6\u0003J\f\u0010\u00a3\u0001\u001a\u0004\u0018\u00010\u0006H\u00c6\u0003J\f\u0010\u00a4\u0001\u001a\u0004\u0018\u00010\u0006H\u00c6\u0003J\f\u0010\u00a5\u0001\u001a\u0004\u0018\u00010\u0006H\u00c6\u0003J\n\u0010\u00a6\u0001\u001a\u00020\tH\u00c6\u0003J\n\u0010\u00a7\u0001\u001a\u00020\tH\u00c6\u0003J\n\u0010\u00a8\u0001\u001a\u00020\tH\u00c6\u0003J\n\u0010\u00a9\u0001\u001a\u00020\tH\u00c6\u0003J\n\u0010\u00aa\u0001\u001a\u00020\tH\u00c6\u0003J\n\u0010\u00ab\u0001\u001a\u00020\tH\u00c6\u0003J\n\u0010\u00ac\u0001\u001a\u00020\tH\u00c6\u0003J\n\u0010\u00ad\u0001\u001a\u00020\tH\u00c6\u0003J\n\u0010\u00ae\u0001\u001a\u00020\tH\u00c6\u0003J\n\u0010\u00af\u0001\u001a\u00020\tH\u00c6\u0003J\n\u0010\u00b0\u0001\u001a\u00020\tH\u00c6\u0003J\n\u0010\u00b1\u0001\u001a\u00020\tH\u00c6\u0003J\n\u0010\u00b2\u0001\u001a\u00020\tH\u00c6\u0003J\n\u0010\u00b3\u0001\u001a\u00020\u0006H\u00c6\u0003J\f\u0010\u00b4\u0001\u001a\u0004\u0018\u00010\u0006H\u00c6\u0003J\f\u0010\u00b5\u0001\u001a\u0004\u0018\u00010\u0006H\u00c6\u0003J\u0012\u0010\u00b6\u0001\u001a\n\u0012\u0004\u0012\u00020@\u0018\u00010?H\u00c6\u0003J\f\u0010\u00b7\u0001\u001a\u0004\u0018\u00010\u0006H\u00c6\u0003J\f\u0010\u00b8\u0001\u001a\u0004\u0018\u00010\u0006H\u00c6\u0003J\f\u0010\u00b9\u0001\u001a\u0004\u0018\u00010\u0006H\u00c6\u0003J\n\u0010\u00ba\u0001\u001a\u00020\tH\u00c6\u0003J\u0084\u0005\u0010\u00bb\u0001\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\u00062\b\b\u0002\u0010\b\u001a\u00020\t2\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u00062\b\b\u0002\u0010\r\u001a\u00020\t2\b\b\u0002\u0010\u000e\u001a\u00020\t2\b\b\u0002\u0010\u000f\u001a\u00020\t2\b\b\u0002\u0010\u0010\u001a\u00020\t2\b\b\u0002\u0010\u0011\u001a\u00020\t2\b\b\u0002\u0010\u0012\u001a\u00020\t2\b\b\u0002\u0010\u0013\u001a\u00020\t2\b\b\u0002\u0010\u0014\u001a\u00020\t2\b\b\u0002\u0010\u0015\u001a\u00020\t2\b\b\u0002\u0010\u0016\u001a\u00020\t2\b\b\u0002\u0010\u0017\u001a\u00020\t2\b\b\u0002\u0010\u0018\u001a\u00020\t2\b\b\u0002\u0010\u0019\u001a\u00020\t2\b\b\u0002\u0010\u001a\u001a\u00020\t2\n\b\u0002\u0010\u001b\u001a\u0004\u0018\u00010\u00062\b\b\u0002\u0010\u001c\u001a\u00020\t2\b\b\u0002\u0010\u001d\u001a\u00020\t2\n\b\u0002\u0010\u001e\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\u001f\u001a\u0004\u0018\u00010\u00062\b\b\u0002\u0010 \u001a\u00020\t2\n\b\u0002\u0010!\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\"\u001a\u0004\u0018\u00010\u00062\b\b\u0002\u0010#\u001a\u00020\t2\b\b\u0002\u0010$\u001a\u00020\t2\n\b\u0002\u0010%\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010&\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\'\u001a\u0004\u0018\u00010\u00062\b\b\u0002\u0010(\u001a\u00020\t2\b\b\u0002\u0010)\u001a\u00020\t2\n\b\u0002\u0010*\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010+\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010,\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010-\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010.\u001a\u0004\u0018\u00010\u00062\b\b\u0002\u0010/\u001a\u00020\t2\b\b\u0002\u00100\u001a\u00020\t2\b\b\u0002\u00101\u001a\u00020\t2\b\b\u0002\u00102\u001a\u00020\t2\b\b\u0002\u00103\u001a\u00020\t2\b\b\u0002\u00104\u001a\u00020\t2\b\b\u0002\u00105\u001a\u00020\t2\b\b\u0002\u00106\u001a\u00020\t2\b\b\u0002\u00107\u001a\u00020\t2\b\b\u0002\u00108\u001a\u00020\t2\b\b\u0002\u00109\u001a\u00020\t2\b\b\u0002\u0010:\u001a\u00020\t2\b\b\u0002\u0010;\u001a\u00020\u00062\n\b\u0002\u0010<\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010=\u001a\u0004\u0018\u00010\u00062\u0010\b\u0002\u0010>\u001a\n\u0012\u0004\u0012\u00020@\u0018\u00010?H\u00c6\u0001\u00a2\u0006\u0003\u0010\u00bc\u0001J\u0016\u0010\u00bd\u0001\u001a\u00030\u00be\u00012\t\u0010\u00bf\u0001\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\n\u0010\u00c0\u0001\u001a\u00020\u0003H\u00d6\u0001J\n\u0010\u00c1\u0001\u001a\u00020\u0006H\u00d6\u0001R\u001a\u0010\u0004\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004\u00a2\u0006\n\n\u0002\u0010D\u001a\u0004\bB\u0010CR\u0016\u00103\u001a\u00020\t8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\bE\u0010FR\u0016\u0010\u0005\u001a\u00020\u00068\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\bG\u0010HR\u0018\u0010\u001b\u001a\u0004\u0018\u00010\u00068\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\bI\u0010HR\u0018\u0010<\u001a\u0004\u0018\u00010\u00068\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\bJ\u0010HR\u0018\u0010\f\u001a\u0004\u0018\u00010\u00068\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\bK\u0010HR\u0015\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\n\n\u0002\u0010D\u001a\u0004\bL\u0010CR\u0018\u0010\n\u001a\u0004\u0018\u00010\u00068\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\bM\u0010HR\u0018\u0010\u000b\u001a\u0004\u0018\u00010\u00068\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\bN\u0010HR\u0016\u0010)\u001a\u00020\t8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\bO\u0010FR\u0018\u0010*\u001a\u0004\u0018\u00010\u00068\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\bP\u0010HR\u0018\u0010+\u001a\u0004\u0018\u00010\u00068\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\bQ\u0010HR\u0016\u00107\u001a\u00020\t8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\bR\u0010FR\u0016\u00106\u001a\u00020\t8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\bS\u0010FR\u0016\u00105\u001a\u00020\t8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\bT\u0010FR\u0016\u00108\u001a\u00020\t8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\bU\u0010FR\u0016\u00104\u001a\u00020\t8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\bV\u0010FR\u0018\u0010,\u001a\u0004\u0018\u00010\u00068\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\bW\u0010HR\u0018\u0010-\u001a\u0004\u0018\u00010\u00068\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\bX\u0010HR\u0016\u0010/\u001a\u00020\t8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\bY\u0010FR\u0018\u0010.\u001a\u0004\u0018\u00010\u00068\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\bZ\u0010HR\u0016\u00102\u001a\u00020\t8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b[\u0010FR\u0016\u00100\u001a\u00020\t8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\\\u0010FR\u0016\u00101\u001a\u00020\t8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b]\u0010FR\u001e\u0010>\u001a\n\u0012\u0004\u0012\u00020@\u0018\u00010?8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b^\u0010_R\u0011\u0010\u0007\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b`\u0010HR\u0016\u0010\b\u001a\u00020\t8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\ba\u0010FR\u0016\u0010\u001a\u001a\u00020\t8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\bb\u0010FR\u0016\u0010\u0010\u001a\u00020\t8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\bc\u0010FR\u0016\u0010\u0012\u001a\u00020\t8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\bd\u0010FR\u0016\u0010\u0013\u001a\u00020\t8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\be\u0010FR\u0016\u0010\u000f\u001a\u00020\t8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\bf\u0010FR\u0016\u0010\u0011\u001a\u00020\t8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\bg\u0010FR\u0016\u0010\u0015\u001a\u00020\t8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\bh\u0010FR\u0016\u0010\r\u001a\u00020\t8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\bi\u0010FR\u0016\u0010\u000e\u001a\u00020\t8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\bj\u0010FR\u0016\u0010\u0014\u001a\u00020\t8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\bk\u0010FR\u0016\u0010\u0016\u001a\u00020\t8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\bl\u0010FR\u0016\u0010\u0018\u001a\u00020\t8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\bm\u0010FR\u0016\u0010\u0017\u001a\u00020\t8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\bn\u0010FR\u0016\u0010\u0019\u001a\u00020\t8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\bo\u0010FR\u0016\u0010 \u001a\u00020\t8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\bp\u0010FR\u0018\u0010!\u001a\u0004\u0018\u00010\u00068\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\bq\u0010HR\u0018\u0010\"\u001a\u0004\u0018\u00010\u00068\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\br\u0010HR\u0016\u0010(\u001a\u00020\t8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\bs\u0010FR\u0018\u0010%\u001a\u0004\u0018\u00010\u00068\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\bt\u0010HR\u0018\u0010&\u001a\u0004\u0018\u00010\u00068\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\bu\u0010HR\u0018\u0010\'\u001a\u0004\u0018\u00010\u00068\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\bv\u0010HR\u0016\u0010\u001c\u001a\u00020\t8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\bw\u0010FR\u0016\u0010\u001d\u001a\u00020\t8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\bx\u0010FR\u0018\u0010\u001e\u001a\u0004\u0018\u00010\u00068\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\by\u0010HR\u0018\u0010\u001f\u001a\u0004\u0018\u00010\u00068\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\bz\u0010HR\u0016\u0010#\u001a\u00020\t8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b{\u0010FR\u0016\u0010$\u001a\u00020\t8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b|\u0010FR\u0011\u0010;\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b}\u0010HR\u0018\u0010=\u001a\u0004\u0018\u00010\u00068\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b~\u0010HR\u0016\u00109\u001a\u00020\t8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u007f\u0010FR\u0017\u0010:\u001a\u00020\t8\u0006X\u0087\u0004\u00a2\u0006\t\n\u0000\u001a\u0005\b\u0080\u0001\u0010F\u00a8\u0006\u00c2\u0001"}, d2 = {"Lcom/riskcare/app/data/models/ITDeclaration;", "", "id", "", "employeeId", "financialYear", "", "regime", "rentPaidMonthly", "", "landlordName", "landlordPan", "hraCityType", "sec80cPf", "sec80cPpf", "sec80cLic", "sec80cElss", "sec80cNsc", "sec80cFd", "sec80cHomeLoan", "sec80cTuition", "sec80cOther", "sec80ccdNps", "sec80dSelf", "sec80dParents", "sec80dSeniorParent", "sec24bHomeLoan", "homeLoanProvider", "sec80eEduLoan", "sec80gDonation", "sec80gInstitution", "sec80gPan", "sec80ddAmount", "sec80ddDependent", "sec80ddRelation", "sec80uAmount", "sec80uPct", "sec80ddbDisease", "sec80ddbPatient", "sec80ddbRelation", "sec80ddbAmount", "ltaAmount", "ltaDestination", "ltaTravelPeriod", "prevEmployer", "prevEmployerTan", "prevPeriod", "prevGrossSalary", "prevTaxableIncome", "prevTds", "prevPf", "employerNps", "otherSavingsInt", "otherFdInt", "otherDividend", "otherCapitalGains", "otherMisc", "total80c", "totalDeductions", "status", "hrComment", "submittedAt", "proofDocuments", "", "Lcom/riskcare/app/data/models/ITProofDoc;", "(Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/String;Ljava/lang/String;DLjava/lang/String;Ljava/lang/String;Ljava/lang/String;DDDDDDDDDDDDDDLjava/lang/String;DDLjava/lang/String;Ljava/lang/String;DLjava/lang/String;Ljava/lang/String;DDLjava/lang/String;Ljava/lang/String;Ljava/lang/String;DDLjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;DDDDDDDDDDDDLjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/util/List;)V", "getEmployeeId", "()Ljava/lang/Integer;", "Ljava/lang/Integer;", "getEmployerNps", "()D", "getFinancialYear", "()Ljava/lang/String;", "getHomeLoanProvider", "getHrComment", "getHraCityType", "getId", "getLandlordName", "getLandlordPan", "getLtaAmount", "getLtaDestination", "getLtaTravelPeriod", "getOtherCapitalGains", "getOtherDividend", "getOtherFdInt", "getOtherMisc", "getOtherSavingsInt", "getPrevEmployer", "getPrevEmployerTan", "getPrevGrossSalary", "getPrevPeriod", "getPrevPf", "getPrevTaxableIncome", "getPrevTds", "getProofDocuments", "()Ljava/util/List;", "getRegime", "getRentPaidMonthly", "getSec24bHomeLoan", "getSec80cElss", "getSec80cFd", "getSec80cHomeLoan", "getSec80cLic", "getSec80cNsc", "getSec80cOther", "getSec80cPf", "getSec80cPpf", "getSec80cTuition", "getSec80ccdNps", "getSec80dParents", "getSec80dSelf", "getSec80dSeniorParent", "getSec80ddAmount", "getSec80ddDependent", "getSec80ddRelation", "getSec80ddbAmount", "getSec80ddbDisease", "getSec80ddbPatient", "getSec80ddbRelation", "getSec80eEduLoan", "getSec80gDonation", "getSec80gInstitution", "getSec80gPan", "getSec80uAmount", "getSec80uPct", "getStatus", "getSubmittedAt", "getTotal80c", "getTotalDeductions", "component1", "component10", "component11", "component12", "component13", "component14", "component15", "component16", "component17", "component18", "component19", "component2", "component20", "component21", "component22", "component23", "component24", "component25", "component26", "component27", "component28", "component29", "component3", "component30", "component31", "component32", "component33", "component34", "component35", "component36", "component37", "component38", "component39", "component4", "component40", "component41", "component42", "component43", "component44", "component45", "component46", "component47", "component48", "component49", "component5", "component50", "component51", "component52", "component53", "component54", "component55", "component56", "component57", "component58", "component6", "component7", "component8", "component9", "copy", "(Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/String;Ljava/lang/String;DLjava/lang/String;Ljava/lang/String;Ljava/lang/String;DDDDDDDDDDDDDDLjava/lang/String;DDLjava/lang/String;Ljava/lang/String;DLjava/lang/String;Ljava/lang/String;DDLjava/lang/String;Ljava/lang/String;Ljava/lang/String;DDLjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;DDDDDDDDDDDDLjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/util/List;)Lcom/riskcare/app/data/models/ITDeclaration;", "equals", "", "other", "hashCode", "toString", "app_debug"})
public final class ITDeclaration {
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Integer id = null;
    @com.google.gson.annotations.SerializedName(value = "employee_id")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Integer employeeId = null;
    @com.google.gson.annotations.SerializedName(value = "financial_year")
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String financialYear = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String regime = null;
    @com.google.gson.annotations.SerializedName(value = "rent_paid_monthly")
    private final double rentPaidMonthly = 0.0;
    @com.google.gson.annotations.SerializedName(value = "landlord_name")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String landlordName = null;
    @com.google.gson.annotations.SerializedName(value = "landlord_pan")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String landlordPan = null;
    @com.google.gson.annotations.SerializedName(value = "hra_city_type")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String hraCityType = null;
    @com.google.gson.annotations.SerializedName(value = "sec80c_pf")
    private final double sec80cPf = 0.0;
    @com.google.gson.annotations.SerializedName(value = "sec80c_ppf")
    private final double sec80cPpf = 0.0;
    @com.google.gson.annotations.SerializedName(value = "sec80c_lic")
    private final double sec80cLic = 0.0;
    @com.google.gson.annotations.SerializedName(value = "sec80c_elss")
    private final double sec80cElss = 0.0;
    @com.google.gson.annotations.SerializedName(value = "sec80c_nsc")
    private final double sec80cNsc = 0.0;
    @com.google.gson.annotations.SerializedName(value = "sec80c_fd")
    private final double sec80cFd = 0.0;
    @com.google.gson.annotations.SerializedName(value = "sec80c_home_loan")
    private final double sec80cHomeLoan = 0.0;
    @com.google.gson.annotations.SerializedName(value = "sec80c_tuition")
    private final double sec80cTuition = 0.0;
    @com.google.gson.annotations.SerializedName(value = "sec80c_other")
    private final double sec80cOther = 0.0;
    @com.google.gson.annotations.SerializedName(value = "sec80ccd_nps")
    private final double sec80ccdNps = 0.0;
    @com.google.gson.annotations.SerializedName(value = "sec80d_self")
    private final double sec80dSelf = 0.0;
    @com.google.gson.annotations.SerializedName(value = "sec80d_parents")
    private final double sec80dParents = 0.0;
    @com.google.gson.annotations.SerializedName(value = "sec80d_senior_parent")
    private final double sec80dSeniorParent = 0.0;
    @com.google.gson.annotations.SerializedName(value = "sec24b_home_loan")
    private final double sec24bHomeLoan = 0.0;
    @com.google.gson.annotations.SerializedName(value = "homeloan_provider")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String homeLoanProvider = null;
    @com.google.gson.annotations.SerializedName(value = "sec80e_edu_loan")
    private final double sec80eEduLoan = 0.0;
    @com.google.gson.annotations.SerializedName(value = "sec80g_donation")
    private final double sec80gDonation = 0.0;
    @com.google.gson.annotations.SerializedName(value = "sec80g_institution")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String sec80gInstitution = null;
    @com.google.gson.annotations.SerializedName(value = "sec80g_pan")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String sec80gPan = null;
    @com.google.gson.annotations.SerializedName(value = "sec80dd_amount")
    private final double sec80ddAmount = 0.0;
    @com.google.gson.annotations.SerializedName(value = "sec80dd_dependent")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String sec80ddDependent = null;
    @com.google.gson.annotations.SerializedName(value = "sec80dd_relation")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String sec80ddRelation = null;
    @com.google.gson.annotations.SerializedName(value = "sec80u_amount")
    private final double sec80uAmount = 0.0;
    @com.google.gson.annotations.SerializedName(value = "sec80u_pct")
    private final double sec80uPct = 0.0;
    @com.google.gson.annotations.SerializedName(value = "sec80ddb_disease")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String sec80ddbDisease = null;
    @com.google.gson.annotations.SerializedName(value = "sec80ddb_patient")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String sec80ddbPatient = null;
    @com.google.gson.annotations.SerializedName(value = "sec80ddb_relation")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String sec80ddbRelation = null;
    @com.google.gson.annotations.SerializedName(value = "sec80ddb_amount")
    private final double sec80ddbAmount = 0.0;
    @com.google.gson.annotations.SerializedName(value = "lta_amount")
    private final double ltaAmount = 0.0;
    @com.google.gson.annotations.SerializedName(value = "lta_destination")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String ltaDestination = null;
    @com.google.gson.annotations.SerializedName(value = "lta_travel_period")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String ltaTravelPeriod = null;
    @com.google.gson.annotations.SerializedName(value = "prev_employer")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String prevEmployer = null;
    @com.google.gson.annotations.SerializedName(value = "prev_employer_tan")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String prevEmployerTan = null;
    @com.google.gson.annotations.SerializedName(value = "prev_period")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String prevPeriod = null;
    @com.google.gson.annotations.SerializedName(value = "prev_gross_salary")
    private final double prevGrossSalary = 0.0;
    @com.google.gson.annotations.SerializedName(value = "prev_taxable_income")
    private final double prevTaxableIncome = 0.0;
    @com.google.gson.annotations.SerializedName(value = "prev_tds")
    private final double prevTds = 0.0;
    @com.google.gson.annotations.SerializedName(value = "prev_pf")
    private final double prevPf = 0.0;
    @com.google.gson.annotations.SerializedName(value = "employer_nps")
    private final double employerNps = 0.0;
    @com.google.gson.annotations.SerializedName(value = "other_savings_int")
    private final double otherSavingsInt = 0.0;
    @com.google.gson.annotations.SerializedName(value = "other_fd_int")
    private final double otherFdInt = 0.0;
    @com.google.gson.annotations.SerializedName(value = "other_dividend")
    private final double otherDividend = 0.0;
    @com.google.gson.annotations.SerializedName(value = "other_capital_gains")
    private final double otherCapitalGains = 0.0;
    @com.google.gson.annotations.SerializedName(value = "other_misc")
    private final double otherMisc = 0.0;
    @com.google.gson.annotations.SerializedName(value = "total_80c")
    private final double total80c = 0.0;
    @com.google.gson.annotations.SerializedName(value = "total_deductions")
    private final double totalDeductions = 0.0;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String status = null;
    @com.google.gson.annotations.SerializedName(value = "hr_comment")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String hrComment = null;
    @com.google.gson.annotations.SerializedName(value = "submitted_at")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String submittedAt = null;
    @com.google.gson.annotations.SerializedName(value = "proof_documents")
    @org.jetbrains.annotations.Nullable()
    private final java.util.List<com.riskcare.app.data.models.ITProofDoc> proofDocuments = null;
    
    public ITDeclaration(@org.jetbrains.annotations.Nullable()
    java.lang.Integer id, @org.jetbrains.annotations.Nullable()
    java.lang.Integer employeeId, @org.jetbrains.annotations.NotNull()
    java.lang.String financialYear, @org.jetbrains.annotations.NotNull()
    java.lang.String regime, double rentPaidMonthly, @org.jetbrains.annotations.Nullable()
    java.lang.String landlordName, @org.jetbrains.annotations.Nullable()
    java.lang.String landlordPan, @org.jetbrains.annotations.Nullable()
    java.lang.String hraCityType, double sec80cPf, double sec80cPpf, double sec80cLic, double sec80cElss, double sec80cNsc, double sec80cFd, double sec80cHomeLoan, double sec80cTuition, double sec80cOther, double sec80ccdNps, double sec80dSelf, double sec80dParents, double sec80dSeniorParent, double sec24bHomeLoan, @org.jetbrains.annotations.Nullable()
    java.lang.String homeLoanProvider, double sec80eEduLoan, double sec80gDonation, @org.jetbrains.annotations.Nullable()
    java.lang.String sec80gInstitution, @org.jetbrains.annotations.Nullable()
    java.lang.String sec80gPan, double sec80ddAmount, @org.jetbrains.annotations.Nullable()
    java.lang.String sec80ddDependent, @org.jetbrains.annotations.Nullable()
    java.lang.String sec80ddRelation, double sec80uAmount, double sec80uPct, @org.jetbrains.annotations.Nullable()
    java.lang.String sec80ddbDisease, @org.jetbrains.annotations.Nullable()
    java.lang.String sec80ddbPatient, @org.jetbrains.annotations.Nullable()
    java.lang.String sec80ddbRelation, double sec80ddbAmount, double ltaAmount, @org.jetbrains.annotations.Nullable()
    java.lang.String ltaDestination, @org.jetbrains.annotations.Nullable()
    java.lang.String ltaTravelPeriod, @org.jetbrains.annotations.Nullable()
    java.lang.String prevEmployer, @org.jetbrains.annotations.Nullable()
    java.lang.String prevEmployerTan, @org.jetbrains.annotations.Nullable()
    java.lang.String prevPeriod, double prevGrossSalary, double prevTaxableIncome, double prevTds, double prevPf, double employerNps, double otherSavingsInt, double otherFdInt, double otherDividend, double otherCapitalGains, double otherMisc, double total80c, double totalDeductions, @org.jetbrains.annotations.NotNull()
    java.lang.String status, @org.jetbrains.annotations.Nullable()
    java.lang.String hrComment, @org.jetbrains.annotations.Nullable()
    java.lang.String submittedAt, @org.jetbrains.annotations.Nullable()
    java.util.List<com.riskcare.app.data.models.ITProofDoc> proofDocuments) {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer getId() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer getEmployeeId() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getFinancialYear() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getRegime() {
        return null;
    }
    
    public final double getRentPaidMonthly() {
        return 0.0;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getLandlordName() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getLandlordPan() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getHraCityType() {
        return null;
    }
    
    public final double getSec80cPf() {
        return 0.0;
    }
    
    public final double getSec80cPpf() {
        return 0.0;
    }
    
    public final double getSec80cLic() {
        return 0.0;
    }
    
    public final double getSec80cElss() {
        return 0.0;
    }
    
    public final double getSec80cNsc() {
        return 0.0;
    }
    
    public final double getSec80cFd() {
        return 0.0;
    }
    
    public final double getSec80cHomeLoan() {
        return 0.0;
    }
    
    public final double getSec80cTuition() {
        return 0.0;
    }
    
    public final double getSec80cOther() {
        return 0.0;
    }
    
    public final double getSec80ccdNps() {
        return 0.0;
    }
    
    public final double getSec80dSelf() {
        return 0.0;
    }
    
    public final double getSec80dParents() {
        return 0.0;
    }
    
    public final double getSec80dSeniorParent() {
        return 0.0;
    }
    
    public final double getSec24bHomeLoan() {
        return 0.0;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getHomeLoanProvider() {
        return null;
    }
    
    public final double getSec80eEduLoan() {
        return 0.0;
    }
    
    public final double getSec80gDonation() {
        return 0.0;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getSec80gInstitution() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getSec80gPan() {
        return null;
    }
    
    public final double getSec80ddAmount() {
        return 0.0;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getSec80ddDependent() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getSec80ddRelation() {
        return null;
    }
    
    public final double getSec80uAmount() {
        return 0.0;
    }
    
    public final double getSec80uPct() {
        return 0.0;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getSec80ddbDisease() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getSec80ddbPatient() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getSec80ddbRelation() {
        return null;
    }
    
    public final double getSec80ddbAmount() {
        return 0.0;
    }
    
    public final double getLtaAmount() {
        return 0.0;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getLtaDestination() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getLtaTravelPeriod() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getPrevEmployer() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getPrevEmployerTan() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getPrevPeriod() {
        return null;
    }
    
    public final double getPrevGrossSalary() {
        return 0.0;
    }
    
    public final double getPrevTaxableIncome() {
        return 0.0;
    }
    
    public final double getPrevTds() {
        return 0.0;
    }
    
    public final double getPrevPf() {
        return 0.0;
    }
    
    public final double getEmployerNps() {
        return 0.0;
    }
    
    public final double getOtherSavingsInt() {
        return 0.0;
    }
    
    public final double getOtherFdInt() {
        return 0.0;
    }
    
    public final double getOtherDividend() {
        return 0.0;
    }
    
    public final double getOtherCapitalGains() {
        return 0.0;
    }
    
    public final double getOtherMisc() {
        return 0.0;
    }
    
    public final double getTotal80c() {
        return 0.0;
    }
    
    public final double getTotalDeductions() {
        return 0.0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getStatus() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getHrComment() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getSubmittedAt() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.util.List<com.riskcare.app.data.models.ITProofDoc> getProofDocuments() {
        return null;
    }
    
    public ITDeclaration() {
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
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer component2() {
        return null;
    }
    
    public final double component20() {
        return 0.0;
    }
    
    public final double component21() {
        return 0.0;
    }
    
    public final double component22() {
        return 0.0;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component23() {
        return null;
    }
    
    public final double component24() {
        return 0.0;
    }
    
    public final double component25() {
        return 0.0;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component26() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component27() {
        return null;
    }
    
    public final double component28() {
        return 0.0;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component29() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component3() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component30() {
        return null;
    }
    
    public final double component31() {
        return 0.0;
    }
    
    public final double component32() {
        return 0.0;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component33() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component34() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component35() {
        return null;
    }
    
    public final double component36() {
        return 0.0;
    }
    
    public final double component37() {
        return 0.0;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component38() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component39() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
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
    
    public final double component43() {
        return 0.0;
    }
    
    public final double component44() {
        return 0.0;
    }
    
    public final double component45() {
        return 0.0;
    }
    
    public final double component46() {
        return 0.0;
    }
    
    public final double component47() {
        return 0.0;
    }
    
    public final double component48() {
        return 0.0;
    }
    
    public final double component49() {
        return 0.0;
    }
    
    public final double component5() {
        return 0.0;
    }
    
    public final double component50() {
        return 0.0;
    }
    
    public final double component51() {
        return 0.0;
    }
    
    public final double component52() {
        return 0.0;
    }
    
    public final double component53() {
        return 0.0;
    }
    
    public final double component54() {
        return 0.0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component55() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component56() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component57() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.util.List<com.riskcare.app.data.models.ITProofDoc> component58() {
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
    
    public final double component9() {
        return 0.0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.riskcare.app.data.models.ITDeclaration copy(@org.jetbrains.annotations.Nullable()
    java.lang.Integer id, @org.jetbrains.annotations.Nullable()
    java.lang.Integer employeeId, @org.jetbrains.annotations.NotNull()
    java.lang.String financialYear, @org.jetbrains.annotations.NotNull()
    java.lang.String regime, double rentPaidMonthly, @org.jetbrains.annotations.Nullable()
    java.lang.String landlordName, @org.jetbrains.annotations.Nullable()
    java.lang.String landlordPan, @org.jetbrains.annotations.Nullable()
    java.lang.String hraCityType, double sec80cPf, double sec80cPpf, double sec80cLic, double sec80cElss, double sec80cNsc, double sec80cFd, double sec80cHomeLoan, double sec80cTuition, double sec80cOther, double sec80ccdNps, double sec80dSelf, double sec80dParents, double sec80dSeniorParent, double sec24bHomeLoan, @org.jetbrains.annotations.Nullable()
    java.lang.String homeLoanProvider, double sec80eEduLoan, double sec80gDonation, @org.jetbrains.annotations.Nullable()
    java.lang.String sec80gInstitution, @org.jetbrains.annotations.Nullable()
    java.lang.String sec80gPan, double sec80ddAmount, @org.jetbrains.annotations.Nullable()
    java.lang.String sec80ddDependent, @org.jetbrains.annotations.Nullable()
    java.lang.String sec80ddRelation, double sec80uAmount, double sec80uPct, @org.jetbrains.annotations.Nullable()
    java.lang.String sec80ddbDisease, @org.jetbrains.annotations.Nullable()
    java.lang.String sec80ddbPatient, @org.jetbrains.annotations.Nullable()
    java.lang.String sec80ddbRelation, double sec80ddbAmount, double ltaAmount, @org.jetbrains.annotations.Nullable()
    java.lang.String ltaDestination, @org.jetbrains.annotations.Nullable()
    java.lang.String ltaTravelPeriod, @org.jetbrains.annotations.Nullable()
    java.lang.String prevEmployer, @org.jetbrains.annotations.Nullable()
    java.lang.String prevEmployerTan, @org.jetbrains.annotations.Nullable()
    java.lang.String prevPeriod, double prevGrossSalary, double prevTaxableIncome, double prevTds, double prevPf, double employerNps, double otherSavingsInt, double otherFdInt, double otherDividend, double otherCapitalGains, double otherMisc, double total80c, double totalDeductions, @org.jetbrains.annotations.NotNull()
    java.lang.String status, @org.jetbrains.annotations.Nullable()
    java.lang.String hrComment, @org.jetbrains.annotations.Nullable()
    java.lang.String submittedAt, @org.jetbrains.annotations.Nullable()
    java.util.List<com.riskcare.app.data.models.ITProofDoc> proofDocuments) {
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