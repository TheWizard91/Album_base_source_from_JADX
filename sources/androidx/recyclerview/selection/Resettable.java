package androidx.recyclerview.selection;

public interface Resettable {
    boolean isResetRequired();

    void reset();
}
