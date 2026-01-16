# This software is a part of isar demo.
# Copyright (c) Siemens AG, 2025
#
# SPDX-License-Identifier: MIT

DESCRIPTION = "Image customization"

inherit dpkg-raw

SRC_URI = "file://postinst \
           file://20-ethernet.network \
          "

DEBIAN_DEPENDS = "systemd-resolved"

do_install[cleandirs] += "${D}/etc/systemd/network \
                         "

do_install() {
    install -v -m 644 ${WORKDIR}/20-ethernet.network ${D}/etc/systemd/network/
}
