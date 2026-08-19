# This software is a part of isar demo.
# Copyright (c) Siemens AG, 2025
#
# SPDX-License-Identifier: MIT

DESCRIPTION = "Image customization"
MAINTAINER = "Clara Kowalsky <clara.kowalsky@siemens.com>"

inherit dpkg-raw

ISAR_DEMO_HOSTNAME ?= "isar-demo"
ISAR_DEMO_ETHERNET_IPV4 ?= ""

SRC_URI += " \
    file://postinst.tmpl \
    file://20-ethernet-static.network.tmpl \
    file://20-ethernet-dhcp.network \
    file://ssh-permit-root.conf \
    "

TEMPLATE_FILES += " \
    postinst.tmpl \
    20-ethernet-static.network.tmpl \
    "

TEMPLATE_VARS += " \
    ISAR_DEMO_HOSTNAME \
    ISAR_DEMO_ETHERNET_IPV4 \
    "

# isar/meta/recipes-support/sshd-regen-keys
# isar-cip-core/recipes-core/change-root-homedir
RDEPENDS += "sshd-regen-keys change-root-homedir"

DEBIAN_DEPENDS = "systemd-resolved, systemd-timesyncd, ssh, sshd-regen-keys, change-root-homedir"

do_install[cleandirs] += " \
    ${D}/etc/systemd/network \
    ${D}/etc/ssh/sshd_config.d \
    "

do_install() {
    if [ -n "${ISAR_DEMO_ETHERNET_IPV4}" ]; then
        install -v -m 644 ${WORKDIR}/20-ethernet-static.network ${D}/etc/systemd/network
    else
        install -v -m 644 ${WORKDIR}/20-ethernet-dhcp.network ${D}/etc/systemd/network
    fi
    install -v -m 644 ${WORKDIR}/ssh-permit-root.conf ${D}/etc/ssh/sshd_config.d
}
