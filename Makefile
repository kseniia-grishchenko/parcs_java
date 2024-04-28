all: run

clean:
	rm -f out/SolovayStrassen.jar out/SolovayStrassenTask.jar

out/SolovayStrassen.jar: out/parcs.jar src/SolovayStrassen.java src/Node.java
	@javac -cp out/parcs.jar src/SolovayStrassen.java src/Node.java
	@jar cf out/SolovayStrassen.jar -C src SolovayStrassen.class -C src Node.class
	@rm -f src/SolovayStrassen.class src/Node.class

out/SolovayStrassenTask.jar: out/parcs.jar src/SolovayStrassenTask.java src/Node.java
	@javac -cp out/parcs.jar src/SolovayStrassenTask.java src/Node.java
	@jar cf out/SolovayStrassenTask.jar -C src SolovayStrassenTask.class -C src Node.class
	@rm -f src/SolovayStrassenTask.class src/Node.class

build: out/SolovayStrassen.jar out/SolovayStrassenTask.jar

run: out/SolovayStrassen.jar out/SolovayStrassenTask.jar
	@cd out && java -cp 'parcs.jar:SolovayStrassen.jar' SolovayStrassen
