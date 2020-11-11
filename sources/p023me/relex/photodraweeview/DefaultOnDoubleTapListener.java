package p023me.relex.photodraweeview;

import android.graphics.RectF;
import android.view.GestureDetector;
import android.view.MotionEvent;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.view.DraweeView;

/* renamed from: me.relex.photodraweeview.DefaultOnDoubleTapListener */
public class DefaultOnDoubleTapListener implements GestureDetector.OnDoubleTapListener {
    private Attacher mAttacher;

    public DefaultOnDoubleTapListener(Attacher attacher) {
        setPhotoDraweeViewAttacher(attacher);
    }

    public boolean onSingleTapConfirmed(MotionEvent e) {
        DraweeView<GenericDraweeHierarchy> draweeView;
        RectF displayRect;
        Attacher attacher = this.mAttacher;
        if (attacher == null || (draweeView = attacher.getDraweeView()) == null) {
            return false;
        }
        if (!(this.mAttacher.getOnPhotoTapListener() == null || (displayRect = this.mAttacher.getDisplayRect()) == null)) {
            float x = e.getX();
            float y = e.getY();
            if (displayRect.contains(x, y)) {
                this.mAttacher.getOnPhotoTapListener().onPhotoTap(draweeView, (x - displayRect.left) / displayRect.width(), (y - displayRect.top) / displayRect.height());
                return true;
            }
        }
        if (this.mAttacher.getOnViewTapListener() == null) {
            return false;
        }
        this.mAttacher.getOnViewTapListener().onViewTap(draweeView, e.getX(), e.getY());
        return true;
    }

    public boolean onDoubleTap(MotionEvent event) {
        Attacher attacher = this.mAttacher;
        if (attacher == null) {
            return false;
        }
        try {
            float scale = attacher.getScale();
            float x = event.getX();
            float y = event.getY();
            if (scale < this.mAttacher.getMediumScale()) {
                Attacher attacher2 = this.mAttacher;
                attacher2.setScale(attacher2.getMediumScale(), x, y, true);
            } else if (scale < this.mAttacher.getMediumScale() || scale >= this.mAttacher.getMaximumScale()) {
                Attacher attacher3 = this.mAttacher;
                attacher3.setScale(attacher3.getMinimumScale(), x, y, true);
            } else {
                Attacher attacher4 = this.mAttacher;
                attacher4.setScale(attacher4.getMaximumScale(), x, y, true);
            }
        } catch (Exception e) {
        }
        return true;
    }

    public boolean onDoubleTapEvent(MotionEvent event) {
        return false;
    }

    public void setPhotoDraweeViewAttacher(Attacher attacher) {
        this.mAttacher = attacher;
    }
}
