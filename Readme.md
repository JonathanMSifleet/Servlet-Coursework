#Setup#
##Local##
1. Ensure Node and NPM are installed
2. Open the project root in a terminal, such as the default terminal in VS Code
3. Enter "cd client"
4. Followed by "npm i"
5. Once the installation has complete enter "npm start". This will start the front-end of the project
6. Open the file "./src/interfaces/ISQLOperations.java"
7. Uncomment, and replace the variables url, password, and username with your own, based upon your database
8. If appropriate, comment out the GCP connection code and variables
9. Save and close the file
10. Execute "createfilms.sql" using your preferred MySQL client, importing the file into your database
11. Run the project using Tomcat 9.0. I.e. in Eclipse Enterprise "Run As" -> "Run on Server"

##Cloud##
1. Sign up for AWS, and install AWS CLI
2. Create an IAM user using the AWS (web) Console
    1. Enable "Access key - Programmatic access" & "Password - AWS Management Console access"
    2. Create a group and attach "AdministratorAccess" policy
3. If no access key has been generated, generate an access key by clicking the new user -> Security credentials -> Create access key 
4. In your terminal run "aws configure"
    1. Access Key ID and Secret Access Key can be found inside the generated access key csv file
    2. Region: "eu-west-2"
    3. Output format: "json"
5. In the terminal enter "cd client"
6. Next, enter "npm i"
7. Next, enter "npm run serverlessDeploy"

---
To do:
- cloud
- Write up
