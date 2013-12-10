#!/bin/sh
mkdir -p ./compiledfiles
shopt -s nullglob
for f in *.zip
do
  echo "extract - $f"
  unzip $f
  for gtfsfile in *.txt
  do
    echo "Merge - $f/$gtfsfile"
    if [ -s ./compiledfiles/$gtfsfile ] ; then
      sed -i 1d $gtfsfile
    fi
    cat $gtfsfile >> ./compiledfiles/$gtfsfile
    rm $gtfsfile
  done
done

