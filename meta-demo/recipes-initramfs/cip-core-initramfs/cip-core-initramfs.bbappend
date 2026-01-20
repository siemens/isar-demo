# This software is a part of isar demo.
# Copyright (c) Siemens AG, 2025
#
# SPDX-License-Identifier: MIT

# compress the initrd with zstd to speedup decompression
INITRAMFS_PREINSTALL += "zstd"

# include a hook for squashfs-based A/B rootfs in the initramfs
INITRAMFS_INSTALL += " \
    initramfs-abrootfs-hook \
    initramfs-squashfs-hook \
    "
