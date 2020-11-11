package p018id.zelory.compressor;

import android.content.Context;
import android.graphics.Bitmap;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.Callable;
import p019io.reactivex.Flowable;

/* renamed from: id.zelory.compressor.Compressor */
public class Compressor {
    private Bitmap.CompressFormat compressFormat = Bitmap.CompressFormat.JPEG;
    private String destinationDirectoryPath;
    private int maxHeight = 816;
    private int maxWidth = 612;
    private int quality = 80;

    public Compressor(Context context) {
        this.destinationDirectoryPath = context.getCacheDir().getPath() + File.separator + "images";
    }

    public Compressor setMaxWidth(int maxWidth2) {
        this.maxWidth = maxWidth2;
        return this;
    }

    public Compressor setMaxHeight(int maxHeight2) {
        this.maxHeight = maxHeight2;
        return this;
    }

    public Compressor setCompressFormat(Bitmap.CompressFormat compressFormat2) {
        this.compressFormat = compressFormat2;
        return this;
    }

    public Compressor setQuality(int quality2) {
        this.quality = quality2;
        return this;
    }

    public Compressor setDestinationDirectoryPath(String destinationDirectoryPath2) {
        this.destinationDirectoryPath = destinationDirectoryPath2;
        return this;
    }

    public File compressToFile(File imageFile) throws IOException {
        return compressToFile(imageFile, imageFile.getName());
    }

    public File compressToFile(File imageFile, String compressedFileName) throws IOException {
        return ImageUtil.compressImage(imageFile, this.maxWidth, this.maxHeight, this.compressFormat, this.quality, this.destinationDirectoryPath + File.separator + compressedFileName);
    }

    public Bitmap compressToBitmap(File imageFile) throws IOException {
        return ImageUtil.decodeSampledBitmapFromFile(imageFile, this.maxWidth, this.maxHeight);
    }

    public Flowable<File> compressToFileAsFlowable(File imageFile) {
        return compressToFileAsFlowable(imageFile, imageFile.getName());
    }

    public Flowable<File> compressToFileAsFlowable(final File imageFile, final String compressedFileName) {
        return Flowable.defer(new Callable<Flowable<File>>() {
            public Flowable<File> call() {
                try {
                    return Flowable.just(Compressor.this.compressToFile(imageFile, compressedFileName));
                } catch (IOException e) {
                    return Flowable.error((Throwable) e);
                }
            }
        });
    }

    public Flowable<Bitmap> compressToBitmapAsFlowable(final File imageFile) {
        return Flowable.defer(new Callable<Flowable<Bitmap>>() {
            public Flowable<Bitmap> call() {
                try {
                    return Flowable.just(Compressor.this.compressToBitmap(imageFile));
                } catch (IOException e) {
                    return Flowable.error((Throwable) e);
                }
            }
        });
    }
}
