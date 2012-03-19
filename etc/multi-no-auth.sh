#! /bin/env sh
sed -i "s@\(keyFile = .*\)@# \1@" multi-{1,2,3}.cfg
sed -i "s@\(auth = .*\)@# \1@" multi-{1,2,3}.cfg
