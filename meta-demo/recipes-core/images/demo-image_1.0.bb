# This software is a part of isar demo.
# Copyright (c) Siemens AG, 2025
#
# SPDX-License-Identifier: MIT

require recipes-core/images/isar-image-base.bb

ISAR_RELEASE_CMD = "git -C ${LAYERDIR_meta-demo} describe --tags --dirty --always --match 'v[0-9].[0-9]*'"

IMAGE_INSTALL += "custom-app"
IMAGE_INSTALL += "customization"
IMAGE_PREINSTALL += "vim"

USERS += "root"
USER_root[flags] = "clear-text-password"
USER_root[password] = "root"
