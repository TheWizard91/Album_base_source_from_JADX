package com.firebase.client.core;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.core.view.Change;
import com.firebase.client.core.view.DataEvent;
import com.firebase.client.core.view.Event;
import com.firebase.client.core.view.QuerySpec;

public class ChildEventRegistration extends EventRegistration {
    private final ChildEventListener eventListener;
    private final Repo repo;
    private final QuerySpec spec;

    public ChildEventRegistration(Repo repo2, ChildEventListener eventListener2, QuerySpec spec2) {
        this.repo = repo2;
        this.eventListener = eventListener2;
        this.spec = spec2;
    }

    public boolean respondsTo(Event.EventType eventType) {
        return eventType != Event.EventType.VALUE;
    }

    public boolean equals(Object other) {
        return (other instanceof ChildEventRegistration) && ((ChildEventRegistration) other).eventListener.equals(this.eventListener) && ((ChildEventRegistration) other).repo.equals(this.repo) && ((ChildEventRegistration) other).spec.equals(this.spec);
    }

    public int hashCode() {
        return (((this.eventListener.hashCode() * 31) + this.repo.hashCode()) * 31) + this.spec.hashCode();
    }

    public DataEvent createEvent(Change change, QuerySpec query) {
        return new DataEvent(change.getEventType(), this, new DataSnapshot(new Firebase(this.repo, query.getPath().child(change.getChildKey())), change.getIndexedNode()), change.getPrevName() != null ? change.getPrevName().asString() : null);
    }

    public void fireEvent(DataEvent eventData) {
        if (!isZombied()) {
            int i = C09111.$SwitchMap$com$firebase$client$core$view$Event$EventType[eventData.getEventType().ordinal()];
            if (i == 1) {
                this.eventListener.onChildAdded(eventData.getSnapshot(), eventData.getPreviousName());
            } else if (i == 2) {
                this.eventListener.onChildChanged(eventData.getSnapshot(), eventData.getPreviousName());
            } else if (i == 3) {
                this.eventListener.onChildMoved(eventData.getSnapshot(), eventData.getPreviousName());
            } else if (i == 4) {
                this.eventListener.onChildRemoved(eventData.getSnapshot());
            }
        }
    }

    /* renamed from: com.firebase.client.core.ChildEventRegistration$1 */
    static /* synthetic */ class C09111 {
        static final /* synthetic */ int[] $SwitchMap$com$firebase$client$core$view$Event$EventType;

        static {
            int[] iArr = new int[Event.EventType.values().length];
            $SwitchMap$com$firebase$client$core$view$Event$EventType = iArr;
            try {
                iArr[Event.EventType.CHILD_ADDED.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$firebase$client$core$view$Event$EventType[Event.EventType.CHILD_CHANGED.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$firebase$client$core$view$Event$EventType[Event.EventType.CHILD_MOVED.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$firebase$client$core$view$Event$EventType[Event.EventType.CHILD_REMOVED.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
        }
    }

    public void fireCancelEvent(FirebaseError error) {
        this.eventListener.onCancelled(error);
    }

    public EventRegistration clone(QuerySpec newQuery) {
        return new ChildEventRegistration(this.repo, this.eventListener, newQuery);
    }

    public boolean isSameListener(EventRegistration other) {
        return (other instanceof ChildEventRegistration) && ((ChildEventRegistration) other).eventListener.equals(this.eventListener);
    }

    public QuerySpec getQuerySpec() {
        return this.spec;
    }

    public String toString() {
        return "ChildEventRegistration";
    }

    /* access modifiers changed from: package-private */
    public Repo getRepo() {
        return this.repo;
    }
}
