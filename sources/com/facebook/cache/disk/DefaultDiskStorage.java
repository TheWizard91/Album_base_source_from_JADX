package com.facebook.cache.disk;

import android.os.Environment;
import com.facebook.binaryresource.BinaryResource;
import com.facebook.binaryresource.FileBinaryResource;
import com.facebook.cache.common.CacheErrorLogger;
import com.facebook.cache.common.WriterCallback;
import com.facebook.cache.disk.DiskStorage;
import com.facebook.common.file.FileTree;
import com.facebook.common.file.FileTreeVisitor;
import com.facebook.common.file.FileUtils;
import com.facebook.common.internal.CountingOutputStream;
import com.facebook.common.internal.Preconditions;
import com.facebook.common.time.Clock;
import com.facebook.common.time.SystemClock;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import javax.annotation.Nullable;

public class DefaultDiskStorage implements DiskStorage {
    private static final String CONTENT_FILE_EXTENSION = ".cnt";
    private static final String DEFAULT_DISK_STORAGE_VERSION_PREFIX = "v2";
    private static final int SHARDING_BUCKET_COUNT = 100;
    /* access modifiers changed from: private */
    public static final Class<?> TAG = DefaultDiskStorage.class;
    private static final String TEMP_FILE_EXTENSION = ".tmp";
    static final long TEMP_FILE_LIFETIME_MS = TimeUnit.MINUTES.toMillis(30);
    /* access modifiers changed from: private */
    public final CacheErrorLogger mCacheErrorLogger;
    /* access modifiers changed from: private */
    public final Clock mClock = SystemClock.get();
    private final boolean mIsExternal;
    /* access modifiers changed from: private */
    public final File mRootDirectory;
    /* access modifiers changed from: private */
    public final File mVersionDirectory;

    public @interface FileType {
        public static final String CONTENT = ".cnt";
        public static final String TEMP = ".tmp";
    }

    public DefaultDiskStorage(File rootDirectory, int version, CacheErrorLogger cacheErrorLogger) {
        Preconditions.checkNotNull(rootDirectory);
        this.mRootDirectory = rootDirectory;
        this.mIsExternal = isExternal(rootDirectory, cacheErrorLogger);
        this.mVersionDirectory = new File(rootDirectory, getVersionSubdirectoryName(version));
        this.mCacheErrorLogger = cacheErrorLogger;
        recreateDirectoryIfVersionChanges();
    }

    private static boolean isExternal(File directory, CacheErrorLogger cacheErrorLogger) {
        try {
            File extStoragePath = Environment.getExternalStorageDirectory();
            if (extStoragePath == null) {
                return false;
            }
            try {
                if (directory.getCanonicalPath().contains(extStoragePath.toString())) {
                    return true;
                }
                return false;
            } catch (IOException e) {
                cacheErrorLogger.logError(CacheErrorLogger.CacheErrorCategory.OTHER, TAG, "failed to read folder to check if external: " + null, e);
                return false;
            }
        } catch (Exception e2) {
            cacheErrorLogger.logError(CacheErrorLogger.CacheErrorCategory.OTHER, TAG, "failed to get the external storage directory!", e2);
            return false;
        }
    }

    static String getVersionSubdirectoryName(int version) {
        return String.format((Locale) null, "%s.ols%d.%d", new Object[]{DEFAULT_DISK_STORAGE_VERSION_PREFIX, 100, Integer.valueOf(version)});
    }

    public boolean isEnabled() {
        return true;
    }

    public boolean isExternal() {
        return this.mIsExternal;
    }

    public String getStorageName() {
        String directoryName = this.mRootDirectory.getAbsolutePath();
        return "_" + directoryName.substring(directoryName.lastIndexOf(47) + 1, directoryName.length()) + "_" + directoryName.hashCode();
    }

    private void recreateDirectoryIfVersionChanges() {
        boolean recreateBase = false;
        if (!this.mRootDirectory.exists()) {
            recreateBase = true;
        } else if (!this.mVersionDirectory.exists()) {
            recreateBase = true;
            FileTree.deleteRecursively(this.mRootDirectory);
        }
        if (recreateBase) {
            try {
                FileUtils.mkdirs(this.mVersionDirectory);
            } catch (FileUtils.CreateDirectoryException e) {
                this.mCacheErrorLogger.logError(CacheErrorLogger.CacheErrorCategory.WRITE_CREATE_DIR, TAG, "version directory could not be created: " + this.mVersionDirectory, (Throwable) null);
            }
        }
    }

    private static class IncompleteFileException extends IOException {
        public final long actual;
        public final long expected;

        public IncompleteFileException(long expected2, long actual2) {
            super("File was not written completely. Expected: " + expected2 + ", found: " + actual2);
            this.expected = expected2;
            this.actual = actual2;
        }
    }

    /* access modifiers changed from: package-private */
    public File getContentFileFor(String resourceId) {
        return new File(getFilename(resourceId));
    }

    private String getSubdirectoryPath(String resourceId) {
        return this.mVersionDirectory + File.separator + String.valueOf(Math.abs(resourceId.hashCode() % 100));
    }

    private File getSubdirectory(String resourceId) {
        return new File(getSubdirectoryPath(resourceId));
    }

    private class EntriesCollector implements FileTreeVisitor {
        private final List<DiskStorage.Entry> result;

        private EntriesCollector() {
            this.result = new ArrayList();
        }

        public void preVisitDirectory(File directory) {
        }

        public void visitFile(File file) {
            FileInfo info = DefaultDiskStorage.this.getShardFileInfo(file);
            if (info != null && info.type == ".cnt") {
                this.result.add(new EntryImpl(info.resourceId, file));
            }
        }

        public void postVisitDirectory(File directory) {
        }

        public List<DiskStorage.Entry> getEntries() {
            return Collections.unmodifiableList(this.result);
        }
    }

    private class PurgingVisitor implements FileTreeVisitor {
        private boolean insideBaseDirectory;

        private PurgingVisitor() {
        }

        public void preVisitDirectory(File directory) {
            if (!this.insideBaseDirectory && directory.equals(DefaultDiskStorage.this.mVersionDirectory)) {
                this.insideBaseDirectory = true;
            }
        }

        public void visitFile(File file) {
            if (!this.insideBaseDirectory || !isExpectedFile(file)) {
                file.delete();
            }
        }

        public void postVisitDirectory(File directory) {
            if (!DefaultDiskStorage.this.mRootDirectory.equals(directory) && !this.insideBaseDirectory) {
                directory.delete();
            }
            if (this.insideBaseDirectory && directory.equals(DefaultDiskStorage.this.mVersionDirectory)) {
                this.insideBaseDirectory = false;
            }
        }

        private boolean isExpectedFile(File file) {
            FileInfo info = DefaultDiskStorage.this.getShardFileInfo(file);
            boolean z = false;
            if (info == null) {
                return false;
            }
            if (info.type == ".tmp") {
                return isRecentFile(file);
            }
            if (info.type == ".cnt") {
                z = true;
            }
            Preconditions.checkState(z);
            return true;
        }

        private boolean isRecentFile(File file) {
            return file.lastModified() > DefaultDiskStorage.this.mClock.now() - DefaultDiskStorage.TEMP_FILE_LIFETIME_MS;
        }
    }

    public void purgeUnexpectedResources() {
        FileTree.walkFileTree(this.mRootDirectory, new PurgingVisitor());
    }

    private void mkdirs(File directory, String message) throws IOException {
        try {
            FileUtils.mkdirs(directory);
        } catch (FileUtils.CreateDirectoryException cde) {
            this.mCacheErrorLogger.logError(CacheErrorLogger.CacheErrorCategory.WRITE_CREATE_DIR, TAG, message, cde);
            throw cde;
        }
    }

    public DiskStorage.Inserter insert(String resourceId, Object debugInfo) throws IOException {
        FileInfo info = new FileInfo(".tmp", resourceId);
        File parent = getSubdirectory(info.resourceId);
        if (!parent.exists()) {
            mkdirs(parent, "insert");
        }
        try {
            return new InserterImpl(resourceId, info.createTempFile(parent));
        } catch (IOException ioe) {
            this.mCacheErrorLogger.logError(CacheErrorLogger.CacheErrorCategory.WRITE_CREATE_TEMPFILE, TAG, "insert", ioe);
            throw ioe;
        }
    }

    @Nullable
    public BinaryResource getResource(String resourceId, Object debugInfo) {
        File file = getContentFileFor(resourceId);
        if (!file.exists()) {
            return null;
        }
        file.setLastModified(this.mClock.now());
        return FileBinaryResource.createOrNull(file);
    }

    private String getFilename(String resourceId) {
        FileInfo fileInfo = new FileInfo(".cnt", resourceId);
        return fileInfo.toPath(getSubdirectoryPath(fileInfo.resourceId));
    }

    public boolean contains(String resourceId, Object debugInfo) {
        return query(resourceId, false);
    }

    public boolean touch(String resourceId, Object debugInfo) {
        return query(resourceId, true);
    }

    private boolean query(String resourceId, boolean touch) {
        File contentFile = getContentFileFor(resourceId);
        boolean exists = contentFile.exists();
        if (touch && exists) {
            contentFile.setLastModified(this.mClock.now());
        }
        return exists;
    }

    public long remove(DiskStorage.Entry entry) {
        return doRemove(((EntryImpl) entry).getResource().getFile());
    }

    public long remove(String resourceId) {
        return doRemove(getContentFileFor(resourceId));
    }

    private long doRemove(File contentFile) {
        if (!contentFile.exists()) {
            return 0;
        }
        long fileSize = contentFile.length();
        if (contentFile.delete()) {
            return fileSize;
        }
        return -1;
    }

    public void clearAll() {
        FileTree.deleteContents(this.mRootDirectory);
    }

    public DiskStorage.DiskDumpInfo getDumpInfo() throws IOException {
        List<DiskStorage.Entry> entries = getEntries();
        DiskStorage.DiskDumpInfo dumpInfo = new DiskStorage.DiskDumpInfo();
        for (DiskStorage.Entry entry : entries) {
            DiskStorage.DiskDumpInfoEntry infoEntry = dumpCacheEntry(entry);
            String type = infoEntry.type;
            if (!dumpInfo.typeCounts.containsKey(type)) {
                dumpInfo.typeCounts.put(type, 0);
            }
            dumpInfo.typeCounts.put(type, Integer.valueOf(dumpInfo.typeCounts.get(type).intValue() + 1));
            dumpInfo.entries.add(infoEntry);
        }
        return dumpInfo;
    }

    private DiskStorage.DiskDumpInfoEntry dumpCacheEntry(DiskStorage.Entry entry) throws IOException {
        EntryImpl entryImpl = (EntryImpl) entry;
        String firstBits = "";
        byte[] bytes = entryImpl.getResource().read();
        String type = typeOfBytes(bytes);
        if (type.equals("undefined") && bytes.length >= 4) {
            firstBits = String.format((Locale) null, "0x%02X 0x%02X 0x%02X 0x%02X", new Object[]{Byte.valueOf(bytes[0]), Byte.valueOf(bytes[1]), Byte.valueOf(bytes[2]), Byte.valueOf(bytes[3])});
        }
        String path = entryImpl.getResource().getFile().getPath();
        return new DiskStorage.DiskDumpInfoEntry(entryImpl.getId(), path, type, (float) entryImpl.getSize(), firstBits);
    }

    private String typeOfBytes(byte[] bytes) {
        if (bytes.length < 2) {
            return "undefined";
        }
        if (bytes[0] == -1 && bytes[1] == -40) {
            return "jpg";
        }
        if (bytes[0] == -119 && bytes[1] == 80) {
            return "png";
        }
        if (bytes[0] == 82 && bytes[1] == 73) {
            return "webp";
        }
        if (bytes[0] == 71 && bytes[1] == 73) {
            return "gif";
        }
        return "undefined";
    }

    public List<DiskStorage.Entry> getEntries() throws IOException {
        EntriesCollector collector = new EntriesCollector();
        FileTree.walkFileTree(this.mVersionDirectory, collector);
        return collector.getEntries();
    }

    static class EntryImpl implements DiskStorage.Entry {

        /* renamed from: id */
        private final String f73id;
        private final FileBinaryResource resource;
        private long size;
        private long timestamp;

        private EntryImpl(String id, File cachedFile) {
            Preconditions.checkNotNull(cachedFile);
            this.f73id = (String) Preconditions.checkNotNull(id);
            this.resource = FileBinaryResource.createOrNull(cachedFile);
            this.size = -1;
            this.timestamp = -1;
        }

        public String getId() {
            return this.f73id;
        }

        public long getTimestamp() {
            if (this.timestamp < 0) {
                this.timestamp = this.resource.getFile().lastModified();
            }
            return this.timestamp;
        }

        public FileBinaryResource getResource() {
            return this.resource;
        }

        public long getSize() {
            if (this.size < 0) {
                this.size = this.resource.size();
            }
            return this.size;
        }
    }

    /* access modifiers changed from: private */
    @Nullable
    public FileInfo getShardFileInfo(File file) {
        FileInfo info = FileInfo.fromFile(file);
        if (info != null && getSubdirectory(info.resourceId).equals(file.getParentFile())) {
            return info;
        }
        return null;
    }

    /* access modifiers changed from: private */
    @Nullable
    public static String getFileTypefromExtension(String extension) {
        if (".cnt".equals(extension)) {
            return ".cnt";
        }
        if (".tmp".equals(extension)) {
            return ".tmp";
        }
        return null;
    }

    private static class FileInfo {
        public final String resourceId;
        public final String type;

        private FileInfo(String type2, String resourceId2) {
            this.type = type2;
            this.resourceId = resourceId2;
        }

        public String toString() {
            return this.type + "(" + this.resourceId + ")";
        }

        public String toPath(String parentPath) {
            return parentPath + File.separator + this.resourceId + this.type;
        }

        public File createTempFile(File parent) throws IOException {
            return File.createTempFile(this.resourceId + ".", ".tmp", parent);
        }

        @Nullable
        public static FileInfo fromFile(File file) {
            String type2;
            String name = file.getName();
            int pos = name.lastIndexOf(46);
            if (pos <= 0 || (type2 = DefaultDiskStorage.getFileTypefromExtension(name.substring(pos))) == null) {
                return null;
            }
            String resourceId2 = name.substring(0, pos);
            if (type2.equals(".tmp")) {
                int numPos = resourceId2.lastIndexOf(46);
                if (numPos <= 0) {
                    return null;
                }
                resourceId2 = resourceId2.substring(0, numPos);
            }
            return new FileInfo(type2, resourceId2);
        }
    }

    class InserterImpl implements DiskStorage.Inserter {
        private final String mResourceId;
        final File mTemporaryFile;

        public InserterImpl(String resourceId, File temporaryFile) {
            this.mResourceId = resourceId;
            this.mTemporaryFile = temporaryFile;
        }

        /* JADX INFO: finally extract failed */
        public void writeData(WriterCallback callback, Object debugInfo) throws IOException {
            try {
                FileOutputStream fileStream = new FileOutputStream(this.mTemporaryFile);
                try {
                    CountingOutputStream countingStream = new CountingOutputStream(fileStream);
                    callback.write(countingStream);
                    countingStream.flush();
                    long length = countingStream.getCount();
                    fileStream.close();
                    if (this.mTemporaryFile.length() != length) {
                        throw new IncompleteFileException(length, this.mTemporaryFile.length());
                    }
                } catch (Throwable th) {
                    fileStream.close();
                    throw th;
                }
            } catch (FileNotFoundException fne) {
                DefaultDiskStorage.this.mCacheErrorLogger.logError(CacheErrorLogger.CacheErrorCategory.WRITE_UPDATE_FILE_NOT_FOUND, DefaultDiskStorage.TAG, "updateResource", fne);
                throw fne;
            }
        }

        public BinaryResource commit(Object debugInfo) throws IOException {
            CacheErrorLogger.CacheErrorCategory category;
            File targetFile = DefaultDiskStorage.this.getContentFileFor(this.mResourceId);
            try {
                FileUtils.rename(this.mTemporaryFile, targetFile);
                if (targetFile.exists()) {
                    targetFile.setLastModified(DefaultDiskStorage.this.mClock.now());
                }
                return FileBinaryResource.createOrNull(targetFile);
            } catch (FileUtils.RenameException re) {
                Throwable cause = re.getCause();
                if (cause == null) {
                    category = CacheErrorLogger.CacheErrorCategory.WRITE_RENAME_FILE_OTHER;
                } else if (cause instanceof FileUtils.ParentDirNotFoundException) {
                    category = CacheErrorLogger.CacheErrorCategory.WRITE_RENAME_FILE_TEMPFILE_PARENT_NOT_FOUND;
                } else if (cause instanceof FileNotFoundException) {
                    category = CacheErrorLogger.CacheErrorCategory.WRITE_RENAME_FILE_TEMPFILE_NOT_FOUND;
                } else {
                    category = CacheErrorLogger.CacheErrorCategory.WRITE_RENAME_FILE_OTHER;
                }
                DefaultDiskStorage.this.mCacheErrorLogger.logError(category, DefaultDiskStorage.TAG, "commit", re);
                throw re;
            }
        }

        public boolean cleanUp() {
            return !this.mTemporaryFile.exists() || this.mTemporaryFile.delete();
        }
    }
}
