# Setup #
## Local ##
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

## Cloud ##
### Back-end and Database ###
1. After signing-up for Google Cloud Platform, access the Console via the following url: "https://console.cloud.google.com/"
2. In the header, click "Select Project" -> "New Project"
3. Give the project an appropriate name [img]
4. Open the project files in Eclipse
5. 


### Front-end ###

Pre-setup:
1. Access the file with the path: "Servlet-Coursework/client/serverless.yml"
2. Under the "custom" section, modify the property under ClientBucket, name
    - Replacing "9uo2ftpc" with a random string containing lowercase letters and numbers from "servletcourseworkclient9uo2ftpc-${self:provider.stage}"
    - This is due to buckets needing a unique name across the entirety of AWS

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
8. Go to the AWS (web) console, and go to CloudFront -> Distributions
9. The URL for the frontend can be found under "Domain name" for the distribution with a description of "Servlet Coursework Client"
10. Go to "Amazon S3" in the console
11. Click on the bucket with the suffix "-prod", e.g. "servletcourseworkclient9uo2ftpc-prod"
12. Copy the name of the bucket including the suffix
13. Open the "package.json" file inside the client folder
14. Modify the "sync" script such that it is as follows: "aws s3 rm s3://[bucket name] --recursive && aws s3 sync ./build s3://[bucket name]" e.g. "aws s3 rm s3://servletcourseworkclient9uo2ftpc-prod --recursive && aws s3 sync ./build s3://servletcourseworkclient9uo2ftpc-prod"
15. Ensure that your terminal is still inside the client folder
16. Enter "npm build"
17. Enter "npm sync"
18. Front-end can now be accessed via 

---
To do:
- cloud
- Write up
