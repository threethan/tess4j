> [!CAUTION]
> This fork, alongside the use of moditect for depedences, makes Tess4J compatible with jPackage. However, it strips out a lot of functionality, and breaks things!

## Tess4J

[![Join the chat at https://gitter.im/nguyenq/tess4j](https://badges.gitter.im/Join%20Chat.svg)](https://gitter.im/nguyenq/tess4j?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)

A Java JNA wrapper for [Tesseract OCR API](https://github.com/tesseract-ocr).

Tess4J is released and distributed under the [Apache License, v2.0](http://www.apache.org/licenses/LICENSE-2.0).

## Features

The library provides optical character recognition (OCR) support for:

* TIFF, JPEG, GIF, PNG, and BMP image formats
* Multi-page TIFF images
* PDF document format

## Dependencies

On Windows: Microsoft Visual C++ 2022 x86 and x64 Runtimes

Since Tesseract and Leptonica Windows binaries were built using Visual Studio 2022 (v143) Platform Toolset, please ensure you have [Microsoft Visual C++ 2022 Redistributable](https://visualstudio.microsoft.com/downloads/) installed.

## Tutorial

[Development with Tess4J in NetBeans, Eclipse, and Command-line](http://tess4j.sourceforge.net/tutorial/)

## Contributors

A big thanks to GitHub and all of Tess4J's contributors.
