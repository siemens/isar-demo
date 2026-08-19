# This software is a part of isar demo.
# Copyright (c) Siemens AG, 2026
#
# SPDX-License-Identifier: MIT

## SWUpdate client update image

# base config on meta-demo/recipes-core/images/demo-image-client.inc
require recipes-core/images/demo-image-client.inc

IMAGE_PREINSTALL += "cowsay"

DELTA_UPDATE_TYPE = "zchunk"
DELTA_ZCK_URL = "http://10.0.5.1:8080/download/client-update.zck"
