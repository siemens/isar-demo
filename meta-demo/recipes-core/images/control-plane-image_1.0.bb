# This software is a part of isar demo.
# Copyright (c) Siemens AG, 2026
#
# SPDX-License-Identifier: MIT

## Control-plane image with wfx and web UI

# base config on meta-demo/recipes-core/images/base.inc
require recipes-core/images/base.inc

# meta-demo/recipes-core/customization
IMAGE_INSTALL:remove = "customization"
IMAGE_INSTALL += "customization-server"

# packages from upstream Debian
IMAGE_PREINSTALL += " \
  jq \
  "

# Isar-built packages
# meta-demo/recipes-app and meta-demo/recipes-support
IMAGE_INSTALL += " \
  wfx-with-ui \
  wfx-config \
  wfxctl \
  wfx-example \
  "
