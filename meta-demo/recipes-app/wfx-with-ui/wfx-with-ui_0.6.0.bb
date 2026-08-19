# This software is a part of isar demo.
# Copyright (c) Siemens AG, 2026
#
# SPDX-License-Identifier: MIT

DESCRIPTION = "wfx with embedded web UI"
MAINTAINER = "Clara Kowalsky <clara.kowalsky@siemens.com>"

inherit dpkg-prebuilt

SRC_URI = "https://github.com/siemens/wfx/releases/download/v${PV}/${PN}_${PV}_${DISTRO_ARCH}.deb"
SRC_URI[sha256sum] = "${SHA256SUM}"

SHA256SUM:amd64 = "d55b4e716dc626bb852cb6ff2d37e1ab6b9b784454b74b0c0a1635ec4646fa6b"
SHA256SUM:arm64 = "c673cfc38e378747deb80e2df522599beaa451b89d76f0764bfe63d1ce555cb6"
