server:
	java -jar target/robot_worlds-1.0-SNAPSHOT-jar-with-dependencies.jar -p 5000 -s 2

clean:
	mvn clean

unittest:
	mvn -Dtest=ResponseTest test
	mvn -Dtest=ClientConstructorTest test
	mvn -Dtest=CreateAndConnectClientSocketTest test
	mvn -Dtest=JsonServerClientTest test
	mvn -Dtest=QuitTest test
	mvn -Dtest=RobotCommandTest test
	mvn -Dtest=SocketTest test
	mvn -Dtest=SQLiteDBTest test
	make kill

acceptance-tests:
	mvn -Dtest=LaunchRobotAcceptanceTest test
	mvn -Dtest=LookAroundAcceptanceTest test
	mvn -Dtest=StateCommandAcceptanceTest test
	mvn -Dtest=LaunchAcceptanceTest test
	mvn -Dtest=LookAroundTest test
	mvn -Dtest=LaunchAcceptanceTest test
	mvn -Dtest=LookAroundAcceptanceTest test
	mvn -Dtest=MoveForwardTest test
	mvn -Dtest=ManyWorldsTest test
	make kill

validate: server
	mvn validate

compile: test  validate
	mvn compile

package: server
	mvn package

kill:
	./kill5000.sh
