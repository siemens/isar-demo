# This software is a part of isar demo.
# Copyright (c) Siemens AG, 2026
#
# SPDX-License-Identifier: MIT

DESCRIPTION = "SWUpdate confirmation script"
MAINTAINER = "Clara Kowalsky <clara.kowalsky@siemens.com>"

inherit dpkg-raw

SRC_URI = " \
    file://${PN}.sh \
    file://${PN}.service \
    "

DEBIAN_DEPENDS = "efibootguard-tools"

do_install[cleandirs] = " \
    ${D}/usr/sbin \
    "

do_install() {
    install -m 755 -D "${WORKDIR}/${PN}.sh" "${D}/usr/sbin"
}
