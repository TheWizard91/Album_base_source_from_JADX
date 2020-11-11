package com.facebook.soloader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
import kotlin.UShort;

public final class MinElf {
    public static final int DT_NEEDED = 1;
    public static final int DT_NULL = 0;
    public static final int DT_STRTAB = 5;
    public static final int ELF_MAGIC = 1179403647;
    public static final int PN_XNUM = 65535;
    public static final int PT_DYNAMIC = 2;
    public static final int PT_LOAD = 1;

    public enum ISA {
        NOT_SO("not_so"),
        X86("x86"),
        ARM("armeabi-v7a"),
        X86_64("x86_64"),
        AARCH64("arm64-v8a"),
        OTHERS("others");
        
        private final String value;

        private ISA(String value2) {
            this.value = value2;
        }

        public String toString() {
            return this.value;
        }
    }

    public static String[] extract_DT_NEEDED(File elfFile) throws IOException {
        FileInputStream is = new FileInputStream(elfFile);
        try {
            return extract_DT_NEEDED(is.getChannel());
        } finally {
            is.close();
        }
    }

    public static String[] extract_DT_NEEDED(FileChannel fc) throws IOException {
        long j;
        long phdr;
        long d_tag;
        long dynStart;
        long d_tag2;
        long j2;
        long e_phoff;
        long e_phnum;
        long p_type;
        long p_type2;
        long p_memsz;
        long p_offset;
        long p_type3;
        long p_offset2;
        long sh_info;
        FileChannel fileChannel = fc;
        ByteBuffer bb = ByteBuffer.allocate(8);
        bb.order(ByteOrder.LITTLE_ENDIAN);
        if (getu32(fileChannel, bb, 0) == 1179403647) {
            boolean z = true;
            if (getu8(fileChannel, bb, 4) != 1) {
                z = false;
            }
            boolean is32 = z;
            if (getu8(fileChannel, bb, 5) == 2) {
                bb.order(ByteOrder.BIG_ENDIAN);
            }
            long e_phoff2 = is32 ? getu32(fileChannel, bb, 28) : get64(fileChannel, bb, 32);
            long e_phnum2 = is32 ? (long) getu16(fileChannel, bb, 44) : (long) getu16(fileChannel, bb, 56);
            if (is32) {
                j = 42;
            } else {
                j = 54;
            }
            int e_phentsize = getu16(fileChannel, bb, j);
            if (e_phnum2 == 65535) {
                long e_shoff = is32 ? getu32(fileChannel, bb, 32) : get64(fileChannel, bb, 40);
                if (is32) {
                    sh_info = getu32(fileChannel, bb, 28 + e_shoff);
                } else {
                    sh_info = getu32(fileChannel, bb, 44 + e_shoff);
                }
                e_phnum2 = sh_info;
            }
            long dynStart2 = 0;
            long phdr2 = e_phoff2;
            long i = 0;
            while (true) {
                if (i >= e_phnum2) {
                    break;
                }
                if (is32) {
                    p_type3 = getu32(fileChannel, bb, phdr2 + 0);
                } else {
                    p_type3 = getu32(fileChannel, bb, phdr2 + 0);
                }
                if (p_type3 == 2) {
                    if (is32) {
                        long j3 = p_type3;
                        p_offset2 = getu32(fileChannel, bb, phdr2 + 4);
                    } else {
                        p_offset2 = get64(fileChannel, bb, phdr2 + 8);
                    }
                    dynStart2 = p_offset2;
                } else {
                    phdr2 += (long) e_phentsize;
                    i++;
                }
            }
            if (dynStart2 != 0) {
                int nr_DT_NEEDED = 0;
                long dyn = dynStart2;
                long ptr_DT_STRTAB = 0;
                while (true) {
                    if (is32) {
                        phdr = phdr2;
                        d_tag = getu32(fileChannel, bb, dyn + 0);
                    } else {
                        phdr = phdr2;
                        d_tag = get64(fileChannel, bb, dyn + 0);
                    }
                    if (d_tag == 1) {
                        dynStart = dynStart2;
                        if (nr_DT_NEEDED != Integer.MAX_VALUE) {
                            nr_DT_NEEDED++;
                        } else {
                            throw new ElfError("malformed DT_NEEDED section");
                        }
                    } else {
                        dynStart = dynStart2;
                        if (d_tag == 5) {
                            ptr_DT_STRTAB = is32 ? getu32(fileChannel, bb, dyn + 4) : get64(fileChannel, bb, dyn + 8);
                        }
                    }
                    long dyn2 = dyn + (is32 ? 8 : 16);
                    if (d_tag != 0) {
                        long j4 = d_tag;
                        long j5 = e_phoff2;
                        e_phnum2 = e_phnum2;
                        phdr2 = phdr;
                        dynStart2 = dynStart;
                        dyn = dyn2;
                    } else if (ptr_DT_STRTAB != 0) {
                        long off_DT_STRTAB = 0;
                        long phdr3 = e_phoff2;
                        int i2 = 0;
                        while (true) {
                            long dyn3 = dyn2;
                            if (((long) i2) >= e_phnum2) {
                                d_tag2 = d_tag;
                                break;
                            }
                            if (is32) {
                                e_phnum = e_phnum2;
                                p_type = getu32(fileChannel, bb, phdr3 + 0);
                            } else {
                                e_phnum = e_phnum2;
                                p_type = getu32(fileChannel, bb, phdr3 + 0);
                            }
                            if (p_type == 1) {
                                if (is32) {
                                    long j6 = p_type;
                                    p_type2 = getu32(fileChannel, bb, phdr3 + 8);
                                } else {
                                    long j7 = p_type;
                                    p_type2 = get64(fileChannel, bb, phdr3 + 16);
                                }
                                if (is32) {
                                    d_tag2 = d_tag;
                                    p_memsz = getu32(fileChannel, bb, phdr3 + 20);
                                } else {
                                    d_tag2 = d_tag;
                                    p_memsz = get64(fileChannel, bb, phdr3 + 40);
                                }
                                if (p_type2 > ptr_DT_STRTAB || ptr_DT_STRTAB >= p_type2 + p_memsz) {
                                } else {
                                    if (is32) {
                                        long j8 = p_memsz;
                                        p_offset = getu32(fileChannel, bb, phdr3 + 4);
                                    } else {
                                        p_offset = get64(fileChannel, bb, phdr3 + 8);
                                    }
                                    off_DT_STRTAB = p_offset + (ptr_DT_STRTAB - p_type2);
                                }
                            } else {
                                d_tag2 = d_tag;
                            }
                            phdr3 += (long) e_phentsize;
                            i2++;
                            e_phnum2 = e_phnum;
                            d_tag = d_tag2;
                            dyn2 = dyn3;
                        }
                        if (off_DT_STRTAB != 0) {
                            String[] needed = new String[nr_DT_NEEDED];
                            int nr_DT_NEEDED2 = 0;
                            long dyn4 = dynStart;
                            long d_tag3 = d_tag2;
                            while (true) {
                                if (is32) {
                                    long j9 = d_tag3;
                                    j2 = getu32(fileChannel, bb, dyn4 + 0);
                                } else {
                                    j2 = get64(fileChannel, bb, dyn4 + 0);
                                }
                                d_tag3 = j2;
                                if (d_tag3 == 1) {
                                    e_phoff = e_phoff2;
                                    needed[nr_DT_NEEDED2] = getSz(fileChannel, bb, off_DT_STRTAB + (is32 ? getu32(fileChannel, bb, dyn4 + 4) : get64(fileChannel, bb, dyn4 + 8)));
                                    if (nr_DT_NEEDED2 != Integer.MAX_VALUE) {
                                        nr_DT_NEEDED2++;
                                    } else {
                                        throw new ElfError("malformed DT_NEEDED section");
                                    }
                                } else {
                                    e_phoff = e_phoff2;
                                }
                                dyn4 += is32 ? 8 : 16;
                                if (d_tag3 != 0) {
                                    e_phoff2 = e_phoff;
                                } else if (nr_DT_NEEDED2 == needed.length) {
                                    return needed;
                                } else {
                                    throw new ElfError("malformed DT_NEEDED section");
                                }
                            }
                        } else {
                            throw new ElfError("did not find file offset of DT_STRTAB table");
                        }
                    } else {
                        throw new ElfError("Dynamic section string-table not found");
                    }
                }
            } else {
                throw new ElfError("ELF file does not contain dynamic linking information");
            }
        } else {
            throw new ElfError("file is not ELF");
        }
    }

    private static String getSz(FileChannel fc, ByteBuffer bb, long offset) throws IOException {
        StringBuilder sb = new StringBuilder();
        while (true) {
            long offset2 = 1 + offset;
            short u8Var = getu8(fc, bb, offset);
            short b = u8Var;
            if (u8Var == 0) {
                return sb.toString();
            }
            sb.append((char) b);
            offset = offset2;
        }
    }

    private static void read(FileChannel fc, ByteBuffer bb, int sz, long offset) throws IOException {
        int numBytesRead;
        bb.position(0);
        bb.limit(sz);
        while (bb.remaining() > 0 && (numBytesRead = fc.read(bb, offset)) != -1) {
            offset += (long) numBytesRead;
        }
        if (bb.remaining() <= 0) {
            bb.position(0);
            return;
        }
        throw new ElfError("ELF file truncated");
    }

    private static long get64(FileChannel fc, ByteBuffer bb, long offset) throws IOException {
        read(fc, bb, 8, offset);
        return bb.getLong();
    }

    private static long getu32(FileChannel fc, ByteBuffer bb, long offset) throws IOException {
        read(fc, bb, 4, offset);
        return ((long) bb.getInt()) & 4294967295L;
    }

    private static int getu16(FileChannel fc, ByteBuffer bb, long offset) throws IOException {
        read(fc, bb, 2, offset);
        return bb.getShort() & UShort.MAX_VALUE;
    }

    private static short getu8(FileChannel fc, ByteBuffer bb, long offset) throws IOException {
        read(fc, bb, 1, offset);
        return (short) (bb.get() & 255);
    }

    private static class ElfError extends RuntimeException {
        ElfError(String why) {
            super(why);
        }
    }
}
