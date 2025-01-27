
for dockerize project :
1.

    mvn clean package
This will generate a .jar file (e.g., WalletManager.jar) in the target/ directory.

2. Create a Dockerfile
   Next, you need to create a Dockerfile in the root of your project. This file will define how to build your application’s Docker image.

Here’s an example Dockerfile:
