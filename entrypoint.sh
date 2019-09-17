if [ "$IS_CI" = "" ]; then
  echo "----------- IS_CI is empty"
  export SCOPE_SERVICE=unitTests
  echo "---------- Executing $SCOPE_SERVICE"
  ./mvnw -fae clean test -Pjava11 -DskipITs=true

  export SCOPE_SERVICE=integrationTests
  echo "---------- Executing $SCOPE_SERVICE"
  ./mvnw -fae clean integration-test -Pjava11 -DskipUTs=true
else
  echo "----------- IS_CI NOT is empty"
  export SCOPE_SERVICE=default
  ./mvnw -fae clean verify -Pjava11
fi

echo "----------------- Finished"
ls -l
ls -l ./target

cp -a ./target/. /root/artifacts
