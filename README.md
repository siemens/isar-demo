<!--
SPDX-FileCopyrightText: Copyright 2025 Siemens AG
SPDX-License-Identifier: MIT
-->

# Isar Demo

This demo project is designed to showcase some features of [Isar](https://github.com/ilbers/isar/). Isar is a build system designed for creating custom Debian-based images.

This demo illustrates:
- how to create a project layer on top of the existing Isar layers
- how to add custom packages built by Isar, as well as packages from Debian upstream, to a target image
- how to generate a Debian Trixie image tailored for either the Raspberry Pi 4B or for QEMU AMD64 emulation.

**Important Note:** This project is intended solely as a demo and should not be used as a basis for product development.

## Build

The build process is managed using the [`kas-container`](kas-container) wrapper script.
Please refer to the [kas user guide](https://kas.readthedocs.io/).
As `kas-container` runs builds in a containerized environment, you'll need [Docker](https://docs.docker.com/engine/install/) or [Podman](https://podman.io/docs/installation) installed.

The resulting images will be placed in the `build/tmp/deploy/images` directory within your project.

### For the Raspberry Pi 4B

To build an image for the Raspberry Pi 4B, run:

```
./kas-container build kas.yaml:kas/machine/rpi-arm64-v8-efi.yaml
```

The resulting raw disk image can be flashed onto an SD card using `dd`or [Balena Etcher](https://etcher.balena.io/).

### For QEMU AMD64

To build an image for QEMU AMD64, run:

```
./kas-container build kas.yaml:kas/machine/qemuamd64.yaml
```

For booting this image in QEMU, you'll need the following packages installed on your Debian system:

- `qemu-system-x86`: For emulating Intel/AMD CPUs.
- `ovmf`: UEFI firmware for QEMU virtual machines.

The image can be booted using the provided [`start-qemu.sh`](start-qemu.sh) script.
The login credentials are: user: root, password: root.

```
./start-qemu.sh amd64
```

## License

This project is licensed according to the terms of the MIT License.
A copy of the license is provided in [LICENSE](LICENSE).
