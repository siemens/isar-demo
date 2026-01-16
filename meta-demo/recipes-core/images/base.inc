# This software is a part of isar demo.
# Copyright (c) Siemens AG, 2025
#
# SPDX-License-Identifier: MIT

# base config on isar/meta-isar/recipes-core/images/isar-image-base.bb
require recipes-core/images/isar-image-base.bb

ISAR_RELEASE_CMD = "git -C ${LAYERDIR_meta-demo} describe --tags --dirty --always --match 'v[0-9].[0-9]*'"

# configure root user
USERS += "root"
USER_root[flags] = "clear-text-password"
USER_root[password] = "root"

# packages from upstream Debian
IMAGE_PREINSTALL += "\
  vim-tiny \
  iproute2 \
  inetutils-ping \
  less \
  curl \
  "

# Isar-built packages
# meta-demo/recipes-core/customization
IMAGE_INSTALL += "customization"
