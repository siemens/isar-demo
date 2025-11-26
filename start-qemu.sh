#!/bin/sh
#
# This software is a part of isar demo.
# Copyright (c) Siemens AG, 2025
#
# SPDX-License-Identifier: MIT
#
set -x

if [ -n "${QEMU_PATH}" ]; then
	QEMU_PATH="${QEMU_PATH}/"
fi
if [ -z "${TARGET}" ]; then
	TARGET="demo-image"
fi
if [ -z "${DISTRO}" ]; then
	DISTRO="debian-trixie"
fi
if [ -z "${MACHINE}" ]; then
	MACHINE="qemuamd64"
fi
arch="$1"
shift 1

case "${arch}" in
	x86|x86_64|amd64)
		QEMU=qemu-system-x86_64
		QEMU_EXTRA_ARGS=" \
			-cpu qemu64 \
			-smp 4 \
			-machine q35,accel=kvm:tcg \
			-device virtio-net-pci,netdev=net \
			-device ide-hd,drive=disk,bootindex=0 \
			-global ICH9-LPC.disable_s3=1 \
			-global isa-fdc.driveA= \
			-bios OVMF.fd"
	;;
	arm64|aarch64)
		QEMU=qemu-system-aarch64
		u_boot_bin=${FIRMWARE_BIN:-./build/tmp/deploy/images/${TARGET}-${MACHINE}/firmware.bin}
		QEMU_EXTRA_ARGS=" \
			-cpu cortex-a57 \
			-smp 4 \
			-machine virt \
			-device virtio-serial-device \
			-device virtconsole,chardev=con -chardev vc,id=con \
			-device virtio-blk-device,drive=disk \
			-device virtio-net-device,netdev=net \
			-bios ${u_boot_bin} \
			"
		;;
	*)
		echo "Unsupported architecture: ${arch}"
		exit 1
		;;
esac

if [ -z "$IMAGE_PREFIX" ]; then
	IMAGE_PREFIX="build/tmp/deploy/images/${MACHINE}/${TARGET}-${DISTRO}-${MACHINE}"
fi
IMAGE_FILE=$(readlink -f "${IMAGE_PREFIX}".wic)
if [ ! -f "$IMAGE_FILE" ]; then
	echo "ERROR: Could not find disk image $IMAGE_FILE!"
	exit 1
fi
if [ -z "${DISPLAY}" ]; then
   QEMU_EXTRA_ARGS="${QEMU_EXTRA_ARGS} -nographic"
fi

MEM_SIZE="2G"
LOCAL_SSH_PORT="22222"

"${QEMU_PATH}"${QEMU} \
	-serial mon:stdio \
	-m ${MEM_SIZE} -netdev user,id=net,hostfwd=tcp:127.0.0.1:${LOCAL_SSH_PORT}-:22 \
	-drive file="${IMAGE_FILE}",discard=unmap,if=none,id=disk,format=raw \
	${QEMU_EXTRA_ARGS} "$@"
