echo "Compiling..."
javac -cp .:junit-4.12.jar:hamcrest-core-1.3.jar *.java
echo "Running tests..."
java -cp .:junit-4.12.jar:hamcrest-core-1.3.jar org.junit.runner.JUnitCore TestSet

