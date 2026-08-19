# This software is a part of isar demo.
# Copyright (c) Siemens AG, 2026
#
# SPDX-License-Identifier: MIT

DESCRIPTION = "wfx configuration"
MAINTAINER = "Clara Kowalsky <clara.kowalsky@siemens.com>"

inherit dpkg-raw

SRC_URI = " \
    file://postinst \
    file://wfx.yml \
    file://wfx-config.tmpfiles \
    "

DEBIAN_DEPENDS = "wfx-with-ui"

do_install[cleandirs] += " \
    ${D}/etc/wfx \
    "

do_install() {
    install -m 0644 -D ${WORKDIR}/wfx.yml ${D}/etc/wfx
}
