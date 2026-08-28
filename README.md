<!--
SPDX-FileCopyrightText: Copyright 2025 Siemens AG
SPDX-License-Identifier: MIT
-->

# Isar Demo

This is a demo project showcasing the use of [Isar](https://github.com/ilbers/isar) and [Isar-cip-core](https://gitlab.com/cip-project/cip-core/isar-cip-core) for building Debian-based images.

Targets:
- Raspberry Pi 4B (ARM64)
- QEMU AMD64

Image variants:
- `demo-image`: Basic demo image with custom C application installed. A description of this image variant can be found [in this recording](https://youtu.be/j5OqhlvZGTE?si=nXsdTbEu_HAthn8G).
- `demo-image-swupdate`: Demo image with SWUpdate in place. This illustrates:
    - Read-only root filesystem with mutable data partition
    - A/B partitioning
    - Robust firmware updates via [SWUpdate](https://github.com/sbabic/swupdate)
    - SBOM reports via [debsbom](https://github.com/siemens/debsbom)

**Important Note:** This project is intended solely as a demo and should not be used as a basis for product development.

## Build and run

The build can be done using [`kas-container`](https://github.com/siemens/kas/blob/master/kas-container).
Please refer to the [kas user guide](https://kas.readthedocs.io/).
As `kas-container` performs builds in a containerized environment, install [Docker](https://docs.docker.com/engine/install/) or [Podman](https://podman.io/docs/installation).

The resulting images are located in the `build/tmp/deploy/images` directory.

### For the Raspberry Pi

#### Build the Raspberry Pi image

Build an image variant for the Raspberry Pi:

- The [`demo-image`](meta-demo/recipes-core/images/demo-image_1.0.bb):

    ```
    ./kas-container build kas.yaml:kas/machine/rpi-arm64-v8-efi.yaml
    ```

- Or the [`demo-image-swupdate`](meta-demo/recipes-core/images/demo-image-swupdate_1.0.bb):

    ```
    ./kas-container build kas-swupdate.yaml:kas/opt/ab-rootfs.yaml:kas/machine/rpi-arm64-v8-swupdate.yaml
    ```

Unpack the image:

```
unzstd build/tmp/deploy/images/rpi*/*.wic.zst
```

#### Boot the Raspberry Pi image

Flash the image onto an SD card using `dd`or [Balena Etcher](https://etcher.balena.io/).
Boot the Raspberry Pi with the SD card inserted.
If you have a USB connection to the Raspberry Pi, you can connect via:

```
screen /dev/ttyUSB0 115200
```

Connect an Ethernet cable from the Raspberry Pi to your router.
After logging in via the USB connection, check the IP address of the en* interface:

```
ip a
```

From another shell, you can connect to the Raspberry Pi via SSH:

```
ssh root@<ip_address>
```

### For QEMU AMD64

#### Build the QEMU AMD64 image

Build an image variant for QEMU AMD64:

- The [`demo-image`](meta-demo/recipes-core/images/demo-image_1.0.bb):

    ```
    ./kas-container build kas.yaml
    ```

- Or the [`demo-image-swupdate`](meta-demo/recipes-core/images/demo-image-swupdate_1.0.bb):

    ```
    ./kas-container build kas-swupdate.yaml:kas/opt/ab-rootfs.yaml:kas/machine/qemuamd64-swupdate.yaml
    ```

Unpack the image:

```
unzstd build/tmp/deploy/images/qemuamd64*/*.wic.zst
```

Make sure that you have these packages installed on your Debian system:
- `qemu-system-x86`: For emulating Intel/AMD CPUs.
- `ovmf`: UEFI firmware for QEMU virtual machines.

#### Boot the QEMU AMD64 image

Boot the image (user: root, password: root):

- The `demo-image`:

    ```
    ./start-qemu.sh amd64
    ```

- Or the `demo-image-swupdate`:

    ```
    TARGET=demo-image-swupdate MACHINE=qemuamd64-swupdate ./start-qemu.sh amd64
    ```

From another shell, connect to the QEMU image via SSH:

```
ssh root@localhost -p 22222
```

## Integrate a new feature into the demo - Example SWUpdate

This section shows what needs to be considered when adding a new feature to the demo.
For this, we use the example of adding the image variant with SWUpdate (`demo-image-swupdate`).
The [Isar-cip-core](https://gitlab.com/cip-project/cip-core/isar-cip-core) repository provides recipes to get [SWUpdate](https://sbabic.github.io/swupdate/swupdate.html) up and running in an image.
Have a look at the [Isar-cip-core SWUpdate README](https://gitlab.com/cip-project/cip-core/isar-cip-core/-/blob/master/doc/README.swupdate.md).
In contrast to Isar-cip-core, where SWUpdate configurations are made via kas snippets, we define concrete machines and a dedicated image recipe for the implementation.

### 1. Add needed repositories to the kas configuration

*   In this case, include `isar-cip-core` into the [`kas.yaml`](kas.yaml).

    ```yaml
      repos:
        # ... other repos ...

        isar-cip-core:
          url: https://gitlab.com/cip-project/cip-core/isar-cip-core.git
          branch: master
    ```

    Make sure that the versions of `isar-cip-core` and `isar` are compatible.

*   Update [`kas.lock.yaml`](kas.lock.yaml): The `kas` lock file ensures that the build is reproducible by locking the commit hash of each repository.

    ```
    ./kas-container lock kas.yaml
    ```

### 2. Set SWUpdate options

Machine-specific configurations should be placed in dedicated `.conf` files: [`rpi-arm64-v8-swupdate.conf`](meta-demo/conf/machine/rpi-arm64-v8-swupdate.conf) and [`qemuamd64-swupdate.conf`](meta-demo/conf/machine/qemuamd64-swupdate.conf).
Image-specific configurations should be done in a dedicated image recipe for SWUpdate: [`demo-image-swupdate_1.0.bb`](meta-demo/recipes-core/images/demo-image-swupdate_1.0.bb).

*   Build the `demo-image-swupdate` variant where SWUpdate is configured, for the [Raspberry Pi](#Build-the-Raspberry-Pi-image) or [QEMU](#Build-the-QEMU-AMD64-image).

*   Boot the `demo-image-swupdate` variant, for the [Raspberry Pi](#Boot-the-Raspberry-Pi-image) or [QEMU](#Boot-the-QEMU-AMD64-image).

### 3. Prepare the image for SWUpdate

To test the update process, we need to produce an image that is different to the booted version.

*   Make a small change in the image recipe:

    Let's add the package `cowsay` in [`demo-image-swupdate_1.0.bb`](meta-demo/recipes-core/images/demo-image-swupdate_1.0.bb): `IMAGE_PREINSTALL += "cowsay"`

*   Rebuild the `demo-image-swupdate` variant. This creates an updated `.swu` file in `build/tmp/deploy/images/*-swupdate/`.

*   Copy the `.swu` file into your booted image with SSH:


    For the Raspberry Pi: `scp build/tmp/deploy/images/rpi*-swupdate/demo-image-swupdate-debian-trixie-*-swupdate.swu root@<ip_address>:`

    For QEMU AMD64: `scp -P 22222 build/tmp/deploy/images/qemuamd64-swupdate/demo-image-swupdate-debian-trixie-*-swupdate.swu root@localhost:`

### 4. Run SWUpdate in the image

Follow the steps described in the [Isar-cip-core SWUpdate README](https://gitlab.com/cip-project/cip-core/isar-cip-core/-/blob/master/doc/README.swupdate.md#swupdate-verification).

*   The SWUpdate command would be:

    ```
    swupdate -i demo-image-swupdate-debian-trixie-*-swupdate.swu
    ```

*   After reboot, the image contains `cowsay`:

    ```
    root@isar-demo:~# /usr/games/cowsay Hello Isar
    ____________
    < Hello Isar >
    ------------
            \   ^__^
             \  (oo)\_______
                (__)\       )\/\
                    ||----w |
                    ||     ||
    ```

*   Make sure to confirm the update in the end:

    ```
    bg_setenv -c
    ```

## License

This project is licensed according to the terms of the MIT License.
A copy of the license is provided in [LICENSE](LICENSE).
