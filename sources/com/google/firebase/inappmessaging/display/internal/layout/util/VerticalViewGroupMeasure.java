package com.google.firebase.inappmessaging.display.internal.layout.util;

import android.view.View;
import com.google.firebase.inappmessaging.display.internal.Logging;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class VerticalViewGroupMeasure {

    /* renamed from: h */
    private int f1743h;
    private List<ViewMeasure> vms;

    /* renamed from: w */
    private int f1744w;

    public VerticalViewGroupMeasure(int w, int h) {
        this.vms = new ArrayList();
        this.f1744w = w;
        this.f1743h = h;
    }

    public VerticalViewGroupMeasure() {
        this.vms = new ArrayList();
        this.f1744w = 0;
        this.f1743h = 0;
    }

    public void reset(int w, int h) {
        this.f1744w = w;
        this.f1743h = h;
        this.vms = new ArrayList();
    }

    public void add(View view, boolean flex) {
        ViewMeasure vm = new ViewMeasure(view, flex);
        vm.setMaxDimens(this.f1744w, this.f1743h);
        this.vms.add(vm);
    }

    public List<ViewMeasure> getViews() {
        return this.vms;
    }

    public int getTotalHeight() {
        int sum = 0;
        for (ViewMeasure vm : this.vms) {
            sum += vm.getDesiredHeight();
        }
        return sum;
    }

    public int getTotalFixedHeight() {
        int sum = 0;
        for (ViewMeasure vm : this.vms) {
            if (!vm.isFlex()) {
                sum += vm.getDesiredHeight();
            }
        }
        return sum;
    }

    public void allocateSpace(int flexAvail) {
        List<ViewMeasure> flexVms = new ArrayList<>();
        for (ViewMeasure vm : this.vms) {
            if (vm.isFlex()) {
                flexVms.add(vm);
            }
        }
        Collections.sort(flexVms, new Comparator<ViewMeasure>() {
            public int compare(ViewMeasure o1, ViewMeasure o2) {
                if (o1.getDesiredHeight() > o2.getDesiredHeight()) {
                    return -1;
                }
                if (o1.getDesiredHeight() < o2.getDesiredHeight()) {
                    return 1;
                }
                return 0;
            }
        });
        int flexSum = 0;
        for (ViewMeasure vm2 : flexVms) {
            flexSum += vm2.getDesiredHeight();
        }
        int flexCount = flexVms.size();
        if (flexCount < 6) {
            float maxFrac = 1.0f - (((float) (flexCount - 1)) * 0.2f);
            Logging.logdPair("VVGM (minFrac, maxFrac)", 0.2f, maxFrac);
            float extraFracPool = 0.0f;
            for (ViewMeasure vm3 : flexVms) {
                float desiredFrac = ((float) vm3.getDesiredHeight()) / ((float) flexSum);
                float grantedFrac = desiredFrac;
                if (desiredFrac > maxFrac) {
                    extraFracPool += grantedFrac - maxFrac;
                    grantedFrac = maxFrac;
                }
                if (desiredFrac < 0.2f) {
                    float addOn = Math.min(0.2f - desiredFrac, extraFracPool);
                    grantedFrac = desiredFrac + addOn;
                    extraFracPool -= addOn;
                }
                Logging.logdPair("\t(desired, granted)", desiredFrac, grantedFrac);
                vm3.setMaxDimens(this.f1744w, (int) (((float) flexAvail) * grantedFrac));
            }
            return;
        }
        throw new IllegalStateException("VerticalViewGroupMeasure only supports up to 5 children");
    }
}
