#! /bin/env sh
for i in {1,2,3}
do
  rm -vf multi-$i/mongod.lock
done
