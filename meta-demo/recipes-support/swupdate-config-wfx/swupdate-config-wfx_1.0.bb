# This software is a part of isar demo.
# Copyright (c) Siemens AG, 2026
#
# SPDX-License-Identifier: MIT

DESCRIPTION = "wfx backend configuration for SWUpdate"
MAINTAINER = "Clara Kowalsky <clara.kowalsky@siemens.com>"

WFX_TENANT ??= "default"
WFX_CLIENT_ID ??= "isar-demo-client"
WFX_SERVER ??= "http://10.0.5.1:8080"

inherit dpkg-raw

SRC_URI = "file://wfx.conf.tmpl"

TEMPLATE_FILES += "wfx.conf.tmpl"

TEMPLATE_VARS += " \
    WFX_SERVER \
    WFX_TENANT \
    WFX_CLIENT_ID \
    "

do_install[cleandirs] = " \
    ${D}/etc/swupdate/conf.d \
    "

do_install() {
    install -m 644 ${WORKDIR}/wfx.conf ${D}/etc/swupdate/conf.d
}
