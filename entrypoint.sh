export SCOPE_SERVICE=unitTests
echo "---------- Executing $SCOPE_SERVICE"
./mvnw -fae clean test -Pjava11 -DskipITs=true

export SCOPE_SERVICE=integrationTests
echo "---------- Executing $SCOPE_SERVICE"
./mvnw -fae clean integration-test -Pjava11 -DskipUTs=true