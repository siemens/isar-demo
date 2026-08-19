# This software is a part of isar demo.
# Copyright (c) Siemens AG, 2026
#
# SPDX-License-Identifier: MIT

DESCRIPTION = "wfx command line client"
MAINTAINER = "Clara Kowalsky <clara.kowalsky@siemens.com>"

inherit dpkg-prebuilt

SRC_URI = "https://github.com/siemens/wfx/releases/download/v${PV}/${PN}_${PV}_${DISTRO_ARCH}.deb"
SRC_URI[sha256sum] = "${SHA256SUM}"

SHA256SUM:amd64 = "455343ae25592e5794780d40a27079016658e5fb83c775b30cffcaab0f6d80b1"
SHA256SUM:arm64 = "b54682d4f729c4342a52804f72dcd69b7e4a17c9b1859857a97d1a23f6faa35c"
