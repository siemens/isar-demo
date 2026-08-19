#!/bin/sh
#
# This software is a part of isar demo.
# Copyright (c) Siemens AG, 2026
#
# SPDX-License-Identifier: MIT
#
# Manual steps:
#   Copy .swu and .zck from build/tmp/deploy/images/.../*.swu to /var/lib/wfx/
#   Run ./create-job.sh
#   Optional: Connect target via ssh remote forward, port 8080 (add wfx.local to /etc/hosts)
#   Follow progress with wfxctl job events | jq
#   After "Activating", the device will reboot. Reconnect with remote forward

set -eu

CLIENT_ID="${WFX_CLIENT_ID:-isar-demo-client}"
DOWNLOAD_BASE="${WFX_DOWNLOAD_BASE:-http://10.0.5.1:8080/download}"
SWU_DIR="${WFX_ARTIFACT_DIR:-/var/lib/wfx/files}"
ARTIFACT="${1:-${ARTIFACT:-}}"

if [ -z "${ARTIFACT}" ]; then
    ARTIFACT=$(find "${SWU_DIR}" -maxdepth 1 -name '*.swu' -type f | sort | tail -n 1)
fi

if [ -z "${ARTIFACT}" ] || [ ! -e "${ARTIFACT}" ]; then
    echo "FATAL: no .swu artifact found" >&2
    exit 1
fi

SCRIPT_DIR=$(dirname -- "$0")
SCRIPT_DIR=$(cd -- "${SCRIPT_DIR}" && pwd)
cd "${SCRIPT_DIR}"

if ! wfxctl workflow get --name wfx.workflow.dau.direct >/dev/null 2>&1; then
    wfxctl workflow create ./wfx.workflow.dau.direct.yml
fi

cat <<EOF | wfxctl job create --workflow wfx.workflow.dau.direct --client-id "${CLIENT_ID}" -
{
    "version": "1.0",
    "type": [
        "firmware"
    ],
    "artifacts": [
        {
            "name": "Firmware Update",
            "version": "1.0",
            "uri": "${DOWNLOAD_BASE}/$(basename "${ARTIFACT}")"
        }
    ]
}
EOF
