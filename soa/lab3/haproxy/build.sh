#!/usr/bin/env bash

cd "$(dirname "$0")"
HAPROXY_DIR="$(pwd)"

DESTDIR="$HAPROXY_DIR"/build

if [[ $(uname -o) = "FreeBSD" ]]; then
    cd ./source || exit 2
    gmake -j "$(sysctl -n hw.ncpu)" TARGET=freebsd \
        USE_OPENSSL=1 USE_QUIC=1 USE_QUIC_OPENSSL_COMPAT=1 \
        USE_PCRE2=1  DESTDIR="$DESTDIR" || exit 3
    chmod u+x "$HAPROXY_DIR/source/haproxy"
else
    OPENSSL_INSTALL="$HAPROXY_DIR"/openssl/install
    cd ./openssl || exit 2
    ./Configure --prefix="$OPENSSL_INSTALL" || exit 4
    make -j "$(nproc)" || exit 4
    make install_sw -j "$(nproc)" || exit 4 
    cd ../

    cd ./source || exit 2
    make clean
    make -j "$(nproc)" TARGET=linux-glibc USE_OPENSSL=1 \
        SSL_LIB="$OPENSSL_INSTALL"/lib64 SSL_INC="$OPENSSL_INSTALL"/include \
        USE_LIBCRYPT=0 || exit 6
    make -j "$(nproc)" install DESTDIR="$DESTDIR" || exit 6
    chmod u+x "$HAPROXY_DIR/build/usr/local/sbin/haproxy"
fi

