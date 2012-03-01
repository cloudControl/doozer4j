Doozer4j - Another doozer client
================================

A doozer client for java to access the highly-available [doozerd](https://github.com/ha/doozerd) programmatically from your java application.


Usage
-----


	import java.io.IOException;
	import java.net.InetAddress;
	import java.net.UnknownHostException;

	import com.cloudcontrol.doozer4j.DoozerClient;
	import com.cloudcontrol.doozer4j.IDoozerClient;

[...]

		private String host = "localhost";
		private int port = 8046

[...]

		InetAddress doozerd = InetAddress.getByName(host)

		IDoozerClient doozerClient = new DoozerClient(doozerd, port);
		doozerClient.add("/my/first/file", "contentOfTheFile");

[...]



Requirements
------------
You will need following:

- [Apache Maven 2.2.1](http://maven.apache.org/)

Install via your package manager, otherwise get the libraries.

Descriptions can be found here:

For Maven: [Building a Project with Maven](http://maven.apache.org/run-maven/index.html)


How to setup this project?
--------------------------
1. Clone this project

        $ git clone git@github.com:cloudControl/doozer4j.git

2. Change into the directory:

        $ cd doozer4j

3. Run maven:

        $ mvn instal

   Or if you use any derivate of eclipse/Equinox

	$ mvn install eclipse:eclipse

This will fetch all dependencies.


Import into IDE?
----------------

Eclipse:

    $ eclipse create-src


