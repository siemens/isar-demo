# This software is a part of isar demo.
# Copyright (c) Siemens AG, 2025
#
# SPDX-License-Identifier: MIT

DESCRIPTION = "Custom C application"

SRC_URI="file://${PN}-${PV} \
        "

inherit dpkg

do_prepare_build[cleandirs] += "${S}/debian"
do_prepare_build() {
    deb_debianize
}
