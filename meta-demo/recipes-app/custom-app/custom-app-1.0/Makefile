# This software is a part of isar demo.
# Copyright (c) Siemens AG, 2025
#
# SPDX-License-Identifier: MIT

CC ?= gcc
CFLAGS = -Wall -Wextra -O2

TARGET = custom-app
SOURCES = main.c
OBJECTS = $(SOURCES:.c=.o)

prefix ?= /usr
bindir = $(prefix)/bin

all: $(TARGET)

$(TARGET): $(OBJECTS)
	$(CC) $(OBJECTS) -o $(TARGET)

%.o: %.c
	$(CC) $(CFLAGS) -c $< -o $@

clean:
	rm -f $(TARGET) $(OBJECTS)

install: $(TARGET)
	install -D -m 755 $(TARGET) $(DESTDIR)$(bindir)/$(TARGET)

uninstall:
	rm -f $(DESTDIR)$(bindir)/$(TARGET)

.PHONY: all clean install uninstall
