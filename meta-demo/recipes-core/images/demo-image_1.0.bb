# This software is a part of isar demo.
# Copyright (c) Siemens AG, 2025
#
# SPDX-License-Identifier: MIT

## Basic demo image

# base config on meta-demo/recipes-core/images/base.inc
require recipes-core/images/base.inc

# meta-demo/recipes-app/custom-app
IMAGE_INSTALL += "custom-app"
