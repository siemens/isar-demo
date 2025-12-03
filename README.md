# Isar Demo

This demo project is designed to show some features of the build system [isar](https://github.com/ilbers/isar/):

- how to create a project layer on top of the isar layers
- how to add custom packages built by isar and packages from Debian upstream to an image
- how to generate a Debian Trixie image for the Raspberry Pi 4B

The project is intended solely as a demo and should not be used as a basis for product development.

## Build

The build can be done using [kas-container](https://github.com/siemens/kas/blob/master/kas-container).

```
./kas-container build kas.yaml
```

The resulting raw disk image can be flashed onto an SD card.

## License

This project is licensed according to the terms of the MIT License.
A copy of the license is provided in [LICENSE](LICENSE).
