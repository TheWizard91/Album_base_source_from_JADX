package p019io.grpc;

import com.google.common.p028io.BaseEncoding;
import java.nio.charset.Charset;
import p019io.grpc.Metadata;

/* renamed from: io.grpc.InternalMetadata */
public final class InternalMetadata {
    public static final BaseEncoding BASE64_ENCODING_OMIT_PADDING = Metadata.BASE64_ENCODING_OMIT_PADDING;
    public static final Charset US_ASCII = Charset.forName("US-ASCII");

    /* renamed from: io.grpc.InternalMetadata$TrustedAsciiMarshaller */
    public interface TrustedAsciiMarshaller<T> extends Metadata.TrustedAsciiMarshaller<T> {
    }

    public static <T> Metadata.Key<T> keyOf(String name, TrustedAsciiMarshaller<T> marshaller) {
        boolean isPseudo = false;
        if (name != null && !name.isEmpty() && name.charAt(0) == ':') {
            isPseudo = true;
        }
        return Metadata.Key.m192of(name, isPseudo, marshaller);
    }

    public static <T> Metadata.Key<T> keyOf(String name, Metadata.AsciiMarshaller<T> marshaller) {
        boolean isPseudo = false;
        if (name != null && !name.isEmpty() && name.charAt(0) == ':') {
            isPseudo = true;
        }
        return Metadata.Key.m191of(name, isPseudo, marshaller);
    }

    public static Metadata newMetadata(byte[]... binaryValues) {
        return new Metadata(binaryValues);
    }

    public static Metadata newMetadata(int usedNames, byte[]... binaryValues) {
        return new Metadata(usedNames, binaryValues);
    }

    public static byte[][] serialize(Metadata md) {
        return md.serialize();
    }

    public static int headerCount(Metadata md) {
        return md.headerCount();
    }

    public static Object[] serializePartial(Metadata md) {
        return md.serializePartial();
    }

    public static <T> Object parsedValue(Metadata.BinaryStreamMarshaller<T> marshaller, T value) {
        return new Metadata.LazyValue(marshaller, value);
    }

    public static Metadata newMetadataWithParsedValues(int usedNames, Object[] namesAndValues) {
        return new Metadata(usedNames, namesAndValues);
    }
}
