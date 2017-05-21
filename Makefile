SRC = $(wildcard src/*.java)

build: out
	javac -d out src/Verein/*.java src/Verein/VereinsMitgliedPackage/*.java src/Verein/Server/*.java src/Verein/Client/*.java
	jar cfe server.jar Verein.Server.ServerMain out/Verein/*.class out/Verein/VereinsMitgliedPackage/*.class out/Verein/Server/*.class out/Verein/Client/*.class
	jar cfe client.jar Verein.Client.ClientMain out/Verein/*.class out/Verein/VereinsMitgliedPackage/*.class out/Verein/Server/*.class out/Verein/Client/*.class

out:
	mkdir out


rerun:
	killall tnameserv

run:
	tnameserv -ORBInitialPort 1050 &