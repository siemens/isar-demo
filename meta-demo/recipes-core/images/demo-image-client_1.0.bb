# This software is a part of isar demo.
# Copyright (c) Siemens AG, 2026
#
# SPDX-License-Identifier: MIT

## SWUpdate client baseline image

# base config on meta-demo/recipes-core/images/demo-image-swupdate.inc
require recipes-core/images/demo-image-swupdate.inc

# meta-demo/recipes-support/swupdate-config-wfx
IMAGE_INSTALL += "swupdate-config-wfx"

# meta-demo/recipes-support/swupdate-confirm
IMAGE_INSTALL += "swupdate-confirm"

# meta-demo/recipes-core/customization
IMAGE_INSTALL:remove = "customization"
IMAGE_INSTALL += "customization-client"
