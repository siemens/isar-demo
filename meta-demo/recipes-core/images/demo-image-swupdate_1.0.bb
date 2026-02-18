# This software is a part of isar demo.
# Copyright (c) Siemens AG, 2025
#
# SPDX-License-Identifier: MIT

## image with SWUpdate configured

# base config on meta-demo/recipes-core/images/base.inc
require recipes-core/images/base.inc
# base config on isar-cip-core/recipes-core/images/swupdate.inc
require recipes-core/images/swupdate.inc

# isar-cip-core/recipes-core/swupdate-config
IMAGE_INSTALL += "swupdate-config-${MACHINE}"
