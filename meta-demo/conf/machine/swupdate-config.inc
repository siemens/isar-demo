# This software is a part of isar demo.
# Copyright (c) Siemens AG, 2025
#
# SPDX-License-Identifier: MIT

# create a .swu file that can be used for updating
IMAGE_FSTYPES += "swu"

# the rootfs is already compressed. Do not compress externally
SWU_COMPRESSION_TYPE = ""

# the swu file is based on a read-only root filesystem in squashfs format
RO_ROOTFS_TYPE = "squashfs"
IMAGE_TYPEDEP:wic += "${RO_ROOTFS_TYPE}"

# set efibootguard as the bootloader for SWUpdate
SWUPDATE_BOOTLOADER ?= "efibootguard"

# specify preferred providers for SWUpdate
PREFERRED_PROVIDER_swupdate-certificates = "swupdate-certificates-snakeoil"
PREFERRED_PROVIDER_swupdate-certificates-key = "swupdate-certificates-key-snakeoil"
PREFERRED_PROVIDER_swupdate-signer = "swupdate-signer-cms"

# disable the watchdog to facilitate debugging
WDOG_TIMEOUT = "0"

# set the compatible hardware revision
SWU_HW_COMPAT ?= "isar-demo-${MACHINE}"
# set the hardware revision of the current machine
MACHINE_HW_VERSION ?= "isar-demo-${MACHINE}"

# define UUIDs for the A/B rootfs partitions
ABROOTFS_PART_UUID_A ?= "fedcba98-7654-3210-cafe-5e0710000001"
ABROOTFS_PART_UUID_B ?= "fedcba98-7654-3210-cafe-5e0710000002"
