# Isar Demo

This demo project is designed to show some features of the build system [isar](https://github.com/ilbers/isar/):

- how to create a project layer on top of the isar layers
- how to add custom packages built by isar and packages from Debian upstream to an image
- how to generate a Debian Trixie image for the Raspberry Pi 4B or for QEMU AMD64

The project is intended solely as a demo and should not be used as a basis for product development.

## Build

The build can be done using [kas-container](https://github.com/siemens/kas/blob/master/kas-container).

### For the Raspberry Pi 4B

```
./kas-container build kas.yaml:kas/machine/rpi-arm64-v8-efi.yaml
```

The resulting raw disk image can be flashed onto an SD card.

### For QEMU AMD64

```
./kas-container build kas.yaml:kas/machine/qemuamd64.yaml
```

## License

This project is licensed according to the terms of the MIT License.
A copy of the license is provided in [LICENSE](LICENSE).
