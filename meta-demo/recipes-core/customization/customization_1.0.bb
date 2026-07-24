# This software is a part of isar demo.
# Copyright (c) Siemens AG, 2025
#
# SPDX-License-Identifier: MIT

DESCRIPTION = "Image customization"
MAINTAINER = "Clara Kowalsky <clara.kowalsky@siemens.com>"

inherit dpkg-raw

SRC_URI = "file://postinst \
           file://20-ethernet.network \
           file://ssh-permit-root.conf \
          "

# isar/meta/recipes-support/sshd-regen-keys
# isar-cip-core/recipes-core/change-root-homedir
RDEPENDS += "sshd-regen-keys change-root-homedir"

DEBIAN_DEPENDS = "systemd-resolved, ssh, sshd-regen-keys, change-root-homedir"

do_install[cleandirs] += "${D}/etc/systemd/network \
                          ${D}/etc/ssh/sshd_config.d \
                         "

do_install() {
    install -v -m 644 ${WORKDIR}/20-ethernet.network ${D}/etc/systemd/network/
    install -v -m 644 ${WORKDIR}/ssh-permit-root.conf ${D}/etc/ssh/sshd_config.d/
}
