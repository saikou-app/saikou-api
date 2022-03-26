#!/usr/bin/env bash

BASEDIR=$(dirname "$0")
cd $BASEDIR/src/main/kotlin/ani/saikou/parsers/

echo $PWD

ls -1 *.kt | sed -e "s/.kt//" | xargs -i bash -c "echo compiling {} && kotlinc -include-runtime {}.kt -d $PWD/compiled/"
#   ^list  |  ^ remove .kt    |    echo the name and start compiling