# This software is a part of isar demo.
# Copyright (c) Siemens AG, 2026
#
# SPDX-License-Identifier: MIT

DESCRIPTION = "wfx example scripts"
MAINTAINER = "Clara Kowalsky <clara.kowalsky@siemens.com>"

inherit dpkg-raw

SRC_URI = " \
    file://create-job.sh \
    file://wfx.workflow.dau.direct.yml \
    "

DEBIAN_DEPENDS = "wfxctl"

do_install[cleandirs] += " \
    ${D}/usr/share/wfx-example \
    "

do_install() {
    install -m 755 -D ${WORKDIR}/create-job.sh ${D}/usr/share/wfx-example
    install -m 644 -D ${WORKDIR}/wfx.workflow.dau.direct.yml ${D}/usr/share/wfx-example
}
