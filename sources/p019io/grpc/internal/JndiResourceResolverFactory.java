package p019io.grpc.internal;

import com.google.common.base.Verify;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.annotation.Nullable;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import p019io.grpc.internal.DnsNameResolver;

/* renamed from: io.grpc.internal.JndiResourceResolverFactory */
final class JndiResourceResolverFactory implements DnsNameResolver.ResourceResolverFactory {
    /* access modifiers changed from: private */
    @Nullable
    public static final Throwable JNDI_UNAVAILABILITY_CAUSE = initJndi();

    /* renamed from: io.grpc.internal.JndiResourceResolverFactory$RecordFetcher */
    interface RecordFetcher {
        List<String> getAllRecords(String str, String str2) throws NamingException;
    }

    @Nullable
    private static Throwable initJndi() {
        try {
            Class.forName("javax.naming.directory.InitialDirContext");
            Class.forName("com.sun.jndi.dns.DnsContextFactory");
            return null;
        } catch (ClassNotFoundException e) {
            return e;
        } catch (RuntimeException e2) {
            return e2;
        } catch (Error e3) {
            return e3;
        }
    }

    @Nullable
    public DnsNameResolver.ResourceResolver newResourceResolver() {
        if (unavailabilityCause() != null) {
            return null;
        }
        return new JndiResourceResolver(new JndiRecordFetcher());
    }

    @Nullable
    public Throwable unavailabilityCause() {
        return JNDI_UNAVAILABILITY_CAUSE;
    }

    /* renamed from: io.grpc.internal.JndiResourceResolverFactory$JndiResourceResolver */
    static final class JndiResourceResolver implements DnsNameResolver.ResourceResolver {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private static final Logger logger = Logger.getLogger(JndiResourceResolver.class.getName());
        private static final Pattern whitespace = Pattern.compile("\\s+");
        private final RecordFetcher recordFetcher;

        static {
            Class<JndiResourceResolverFactory> cls = JndiResourceResolverFactory.class;
        }

        public JndiResourceResolver(RecordFetcher recordFetcher2) {
            this.recordFetcher = recordFetcher2;
        }

        public List<String> resolveTxt(String serviceConfigHostname) throws NamingException {
            Logger logger2 = logger;
            if (logger2.isLoggable(Level.FINER)) {
                logger2.log(Level.FINER, "About to query TXT records for {0}", new Object[]{serviceConfigHostname});
            }
            List<String> serviceConfigRawTxtRecords = this.recordFetcher.getAllRecords("TXT", "dns:///" + serviceConfigHostname);
            if (logger2.isLoggable(Level.FINER)) {
                logger2.log(Level.FINER, "Found {0} TXT records", new Object[]{Integer.valueOf(serviceConfigRawTxtRecords.size())});
            }
            List<String> serviceConfigTxtRecords = new ArrayList<>(serviceConfigRawTxtRecords.size());
            for (String serviceConfigRawTxtRecord : serviceConfigRawTxtRecords) {
                serviceConfigTxtRecords.add(unquote(serviceConfigRawTxtRecord));
            }
            return Collections.unmodifiableList(serviceConfigTxtRecords);
        }

        /* JADX WARNING: Removed duplicated region for block: B:29:0x011a  */
        /* JADX WARNING: Removed duplicated region for block: B:34:0x013d  */
        /* JADX WARNING: Removed duplicated region for block: B:45:0x0141 A[SYNTHETIC] */
        /* JADX WARNING: Removed duplicated region for block: B:48:0x0141 A[SYNTHETIC] */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public java.util.List<p019io.grpc.EquivalentAddressGroup> resolveSrv(p019io.grpc.internal.DnsNameResolver.AddressResolver r19, java.lang.String r20) throws java.lang.Exception {
            /*
                r18 = this;
                r1 = r20
                java.util.logging.Logger r0 = logger
                java.util.logging.Level r2 = java.util.logging.Level.FINER
                boolean r2 = r0.isLoggable(r2)
                r3 = 0
                r4 = 1
                if (r2 == 0) goto L_0x0019
                java.util.logging.Level r2 = java.util.logging.Level.FINER
                java.lang.Object[] r5 = new java.lang.Object[r4]
                r5[r3] = r1
                java.lang.String r6 = "About to query SRV records for {0}"
                r0.log(r2, r6, r5)
            L_0x0019:
                r2 = r18
                io.grpc.internal.JndiResourceResolverFactory$RecordFetcher r5 = r2.recordFetcher
                java.lang.StringBuilder r6 = new java.lang.StringBuilder
                r6.<init>()
                java.lang.String r7 = "dns:///"
                java.lang.StringBuilder r6 = r6.append(r7)
                java.lang.StringBuilder r6 = r6.append(r1)
                java.lang.String r6 = r6.toString()
                java.lang.String r7 = "SRV"
                java.util.List r5 = r5.getAllRecords(r7, r6)
                java.util.logging.Level r6 = java.util.logging.Level.FINER
                boolean r6 = r0.isLoggable(r6)
                if (r6 == 0) goto L_0x0051
                java.util.logging.Level r6 = java.util.logging.Level.FINER
                java.lang.Object[] r7 = new java.lang.Object[r4]
                int r8 = r5.size()
                java.lang.Integer r8 = java.lang.Integer.valueOf(r8)
                r7[r3] = r8
                java.lang.String r8 = "Found {0} SRV records"
                r0.log(r6, r8, r7)
            L_0x0051:
                java.util.ArrayList r0 = new java.util.ArrayList
                int r6 = r5.size()
                r0.<init>(r6)
                r6 = r0
                r0 = 0
                java.util.logging.Level r7 = java.util.logging.Level.WARNING
                java.util.Iterator r8 = r5.iterator()
                r9 = r7
                r7 = r0
            L_0x0064:
                boolean r0 = r8.hasNext()
                if (r0 == 0) goto L_0x0147
                java.lang.Object r0 = r8.next()
                r10 = r0
                java.lang.String r10 = (java.lang.String) r10
                io.grpc.internal.JndiResourceResolverFactory$JndiResourceResolver$SrvRecord r0 = parseSrvRecord(r10)     // Catch:{ UnknownHostException -> 0x0120, RuntimeException -> 0x00fd }
                java.lang.String r11 = r0.host     // Catch:{ UnknownHostException -> 0x0120, RuntimeException -> 0x00fd }
                java.lang.String r12 = "."
                boolean r11 = r11.endsWith(r12)     // Catch:{ UnknownHostException -> 0x0120, RuntimeException -> 0x00fd }
                if (r11 == 0) goto L_0x00dc
                java.lang.String r11 = r0.host     // Catch:{ UnknownHostException -> 0x0120, RuntimeException -> 0x00fd }
                java.lang.String r12 = r0.host     // Catch:{ UnknownHostException -> 0x0120, RuntimeException -> 0x00fd }
                int r12 = r12.length()     // Catch:{ UnknownHostException -> 0x0120, RuntimeException -> 0x00fd }
                int r12 = r12 - r4
                java.lang.String r11 = r11.substring(r3, r12)     // Catch:{ UnknownHostException -> 0x0120, RuntimeException -> 0x00fd }
                java.lang.String r12 = r0.host     // Catch:{ UnknownHostException -> 0x0120, RuntimeException -> 0x00fd }
                r13 = r19
                java.util.List r12 = r13.resolveAddress(r12)     // Catch:{ UnknownHostException -> 0x00fb, RuntimeException -> 0x00f9 }
                java.util.ArrayList r14 = new java.util.ArrayList     // Catch:{ UnknownHostException -> 0x00fb, RuntimeException -> 0x00f9 }
                int r15 = r12.size()     // Catch:{ UnknownHostException -> 0x00fb, RuntimeException -> 0x00f9 }
                r14.<init>(r15)     // Catch:{ UnknownHostException -> 0x00fb, RuntimeException -> 0x00f9 }
                java.util.Iterator r15 = r12.iterator()     // Catch:{ UnknownHostException -> 0x00fb, RuntimeException -> 0x00f9 }
            L_0x00a1:
                boolean r16 = r15.hasNext()     // Catch:{ UnknownHostException -> 0x00fb, RuntimeException -> 0x00f9 }
                if (r16 == 0) goto L_0x00c0
                java.lang.Object r16 = r15.next()     // Catch:{ UnknownHostException -> 0x00fb, RuntimeException -> 0x00f9 }
                java.net.InetAddress r16 = (java.net.InetAddress) r16     // Catch:{ UnknownHostException -> 0x00fb, RuntimeException -> 0x00f9 }
                r17 = r16
                java.net.InetSocketAddress r3 = new java.net.InetSocketAddress     // Catch:{ UnknownHostException -> 0x00fb, RuntimeException -> 0x00f9 }
                int r4 = r0.port     // Catch:{ UnknownHostException -> 0x00fb, RuntimeException -> 0x00f9 }
                r1 = r17
                r3.<init>(r1, r4)     // Catch:{ UnknownHostException -> 0x00fb, RuntimeException -> 0x00f9 }
                r14.add(r3)     // Catch:{ UnknownHostException -> 0x00fb, RuntimeException -> 0x00f9 }
                r1 = r20
                r3 = 0
                r4 = 1
                goto L_0x00a1
            L_0x00c0:
                io.grpc.Attributes$Builder r1 = p019io.grpc.Attributes.newBuilder()     // Catch:{ UnknownHostException -> 0x00fb, RuntimeException -> 0x00f9 }
                io.grpc.Attributes$Key<java.lang.String> r3 = p019io.grpc.internal.GrpcAttributes.ATTR_LB_ADDR_AUTHORITY     // Catch:{ UnknownHostException -> 0x00fb, RuntimeException -> 0x00f9 }
                io.grpc.Attributes$Builder r1 = r1.set(r3, r11)     // Catch:{ UnknownHostException -> 0x00fb, RuntimeException -> 0x00f9 }
                io.grpc.Attributes r1 = r1.build()     // Catch:{ UnknownHostException -> 0x00fb, RuntimeException -> 0x00f9 }
                io.grpc.EquivalentAddressGroup r3 = new io.grpc.EquivalentAddressGroup     // Catch:{ UnknownHostException -> 0x00fb, RuntimeException -> 0x00f9 }
                java.util.List r4 = java.util.Collections.unmodifiableList(r14)     // Catch:{ UnknownHostException -> 0x00fb, RuntimeException -> 0x00f9 }
                r3.<init>((java.util.List<java.net.SocketAddress>) r4, (p019io.grpc.Attributes) r1)     // Catch:{ UnknownHostException -> 0x00fb, RuntimeException -> 0x00f9 }
                r6.add(r3)     // Catch:{ UnknownHostException -> 0x00fb, RuntimeException -> 0x00f9 }
                goto L_0x0141
            L_0x00dc:
                r13 = r19
                java.lang.RuntimeException r1 = new java.lang.RuntimeException     // Catch:{ UnknownHostException -> 0x00fb, RuntimeException -> 0x00f9 }
                java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ UnknownHostException -> 0x00fb, RuntimeException -> 0x00f9 }
                r3.<init>()     // Catch:{ UnknownHostException -> 0x00fb, RuntimeException -> 0x00f9 }
                java.lang.String r4 = "Returned SRV host does not end in period: "
                java.lang.StringBuilder r3 = r3.append(r4)     // Catch:{ UnknownHostException -> 0x00fb, RuntimeException -> 0x00f9 }
                java.lang.String r4 = r0.host     // Catch:{ UnknownHostException -> 0x00fb, RuntimeException -> 0x00f9 }
                java.lang.StringBuilder r3 = r3.append(r4)     // Catch:{ UnknownHostException -> 0x00fb, RuntimeException -> 0x00f9 }
                java.lang.String r3 = r3.toString()     // Catch:{ UnknownHostException -> 0x00fb, RuntimeException -> 0x00f9 }
                r1.<init>(r3)     // Catch:{ UnknownHostException -> 0x00fb, RuntimeException -> 0x00f9 }
                throw r1     // Catch:{ UnknownHostException -> 0x00fb, RuntimeException -> 0x00f9 }
            L_0x00f9:
                r0 = move-exception
                goto L_0x0100
            L_0x00fb:
                r0 = move-exception
                goto L_0x0123
            L_0x00fd:
                r0 = move-exception
                r13 = r19
            L_0x0100:
                java.util.logging.Logger r1 = logger
                java.lang.StringBuilder r3 = new java.lang.StringBuilder
                r3.<init>()
                java.lang.String r4 = "Failed to construct SRV record "
                java.lang.StringBuilder r3 = r3.append(r4)
                java.lang.StringBuilder r3 = r3.append(r10)
                java.lang.String r3 = r3.toString()
                r1.log(r9, r3, r0)
                if (r7 != 0) goto L_0x0141
                r1 = r0
                java.util.logging.Level r3 = java.util.logging.Level.FINE
                r7 = r1
                r9 = r3
                goto L_0x0141
            L_0x0120:
                r0 = move-exception
                r13 = r19
            L_0x0123:
                java.util.logging.Logger r1 = logger
                java.lang.StringBuilder r3 = new java.lang.StringBuilder
                r3.<init>()
                java.lang.String r4 = "Can't find address for SRV record "
                java.lang.StringBuilder r3 = r3.append(r4)
                java.lang.StringBuilder r3 = r3.append(r10)
                java.lang.String r3 = r3.toString()
                r1.log(r9, r3, r0)
                if (r7 != 0) goto L_0x0140
                r7 = r0
                java.util.logging.Level r9 = java.util.logging.Level.FINE
            L_0x0140:
            L_0x0141:
                r1 = r20
                r3 = 0
                r4 = 1
                goto L_0x0064
            L_0x0147:
                r13 = r19
                boolean r0 = r6.isEmpty()
                if (r0 == 0) goto L_0x0153
                if (r7 != 0) goto L_0x0152
                goto L_0x0153
            L_0x0152:
                throw r7
            L_0x0153:
                java.util.List r0 = java.util.Collections.unmodifiableList(r6)
                return r0
            */
            throw new UnsupportedOperationException("Method not decompiled: p019io.grpc.internal.JndiResourceResolverFactory.JndiResourceResolver.resolveSrv(io.grpc.internal.DnsNameResolver$AddressResolver, java.lang.String):java.util.List");
        }

        /* renamed from: io.grpc.internal.JndiResourceResolverFactory$JndiResourceResolver$SrvRecord */
        private static final class SrvRecord {
            final String host;
            final int port;

            SrvRecord(String host2, int port2) {
                this.host = host2;
                this.port = port2;
            }
        }

        private static SrvRecord parseSrvRecord(String rawRecord) {
            String[] parts = whitespace.split(rawRecord);
            Verify.verify(parts.length == 4, "Bad SRV Record: %s", (Object) rawRecord);
            return new SrvRecord(parts[3], Integer.parseInt(parts[2]));
        }

        /* JADX WARNING: Code restructure failed: missing block: B:13:0x002a, code lost:
            r2 = r2 + 1;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        static java.lang.String unquote(java.lang.String r6) {
            /*
                java.lang.StringBuilder r0 = new java.lang.StringBuilder
                int r1 = r6.length()
                r0.<init>(r1)
                r1 = 0
                r2 = 0
            L_0x000b:
                int r3 = r6.length()
                if (r2 >= r3) goto L_0x0041
                char r3 = r6.charAt(r2)
                r4 = 34
                if (r1 != 0) goto L_0x0022
                r5 = 32
                if (r3 != r5) goto L_0x001e
                goto L_0x003e
            L_0x001e:
                if (r3 != r4) goto L_0x003b
                r1 = 1
                goto L_0x003e
            L_0x0022:
                if (r3 != r4) goto L_0x0026
                r1 = 0
                goto L_0x003e
            L_0x0026:
                r5 = 92
                if (r3 != r5) goto L_0x003b
                int r2 = r2 + 1
                char r3 = r6.charAt(r2)
                if (r3 == r4) goto L_0x003b
                if (r3 != r5) goto L_0x0035
                goto L_0x003b
            L_0x0035:
                java.lang.AssertionError r4 = new java.lang.AssertionError
                r4.<init>()
                throw r4
            L_0x003b:
                r0.append(r3)
            L_0x003e:
                int r2 = r2 + 1
                goto L_0x000b
            L_0x0041:
                java.lang.String r2 = r0.toString()
                return r2
            */
            throw new UnsupportedOperationException("Method not decompiled: p019io.grpc.internal.JndiResourceResolverFactory.JndiResourceResolver.unquote(java.lang.String):java.lang.String");
        }
    }

    /* renamed from: io.grpc.internal.JndiResourceResolverFactory$JndiRecordFetcher */
    static final class JndiRecordFetcher implements RecordFetcher {
        static final /* synthetic */ boolean $assertionsDisabled = false;

        static {
            Class<JndiResourceResolverFactory> cls = JndiResourceResolverFactory.class;
        }

        JndiRecordFetcher() {
        }

        public List<String> getAllRecords(String recordType, String name) throws NamingException {
            checkAvailable();
            String[] rrType = {recordType};
            List<String> records = new ArrayList<>();
            Hashtable<String, String> env = new Hashtable<>();
            env.put("com.sun.jndi.ldap.connect.timeout", "5000");
            env.put("com.sun.jndi.ldap.read.timeout", "5000");
            DirContext dirContext = new InitialDirContext(env);
            try {
                NamingEnumeration<? extends Attribute> rrGroups = dirContext.getAttributes(name, rrType).getAll();
                while (rrGroups.hasMore()) {
                    try {
                        Attribute rrEntry = (Attribute) rrGroups.next();
                        if (Arrays.asList(rrType).contains(rrEntry.getID())) {
                            NamingEnumeration<?> rrValues = rrEntry.getAll();
                            while (rrValues.hasMore()) {
                                try {
                                    records.add(String.valueOf(rrValues.next()));
                                } catch (NamingException ne) {
                                    closeThenThrow(rrValues, ne);
                                }
                            }
                            rrValues.close();
                        } else {
                            throw new AssertionError();
                        }
                    } catch (NamingException ne2) {
                        closeThenThrow((NamingEnumeration<?>) rrGroups, ne2);
                    }
                }
                rrGroups.close();
            } catch (NamingException ne3) {
                closeThenThrow(dirContext, ne3);
            }
            dirContext.close();
            return records;
        }

        private static void closeThenThrow(NamingEnumeration<?> namingEnumeration, NamingException e) throws NamingException {
            try {
                namingEnumeration.close();
            } catch (NamingException e2) {
            }
            throw e;
        }

        private static void closeThenThrow(DirContext ctx, NamingException e) throws NamingException {
            try {
                ctx.close();
            } catch (NamingException e2) {
            }
            throw e;
        }

        private static void checkAvailable() {
            if (JndiResourceResolverFactory.JNDI_UNAVAILABILITY_CAUSE != null) {
                throw new UnsupportedOperationException("JNDI is not currently available", JndiResourceResolverFactory.JNDI_UNAVAILABILITY_CAUSE);
            }
        }
    }
}
