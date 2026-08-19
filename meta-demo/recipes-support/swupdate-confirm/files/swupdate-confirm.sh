#!/usr/bin/env bash
set -euo pipefail

# typically you would perform some kind of health check before acknowledging the update
bg_setenv --confirm
