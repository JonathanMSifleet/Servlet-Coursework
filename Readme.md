# Setup #
## Local ##
1. Ensure Node and NPM are installed
2. Open the project root in a terminal, such as the default terminal in VS Code
3. Enter "cd client"
4. Followed by "npm i"
5. Once the installation has complete enter "npm start". This will start the front-end of the project
6. Open the file "./src/interfaces/ISQLOperations.java"
7. Uncomment, and replace the variables url, password, and username with your own, based upon your database
8. Comment out the GCP connection code and variables
9. Save and close the file
10. Execute "createfilms.sql" using your preferred MySQL client, importing the file into your database
11. Run the project using Tomcat 9.0. I.e. in Eclipse Enterprise "Run As" -> "Run on Server"

## Cloud ##
### Back-end ###
1. After signing-up for Google Cloud Platform, access the Console via the following url: "https://console.cloud.google.com/"
2. In the header, click "Select Project" -> "New Project"
3. Give the project an appropriate name [img]
4. Open Cloud Shell inside the GCP Console
5. Enter the command "gcloud app create"
    - If an error occurs suggesting no project is selected, close Cloud Shell, ensure the project you created is selected, reopen Cloud Shell and retry step 5
6. When prompted enter the number "12", and press enter
7. Open the project files in Eclipse Enterprise
8. Install "Google Cloud Tools for Eclipse" via "Help" -> "Eclipse Marketplace" -> Search tab [img]
9. Right click the project in Eclipse and click "Deploy to App Engine Standard"
10. Under Eclipse's console, the URL for the backend can be found / accessed via the console's output similar to the following:

"Deployed service [default] to [https://servletcoursework-336513.nw.r.appspot.com]

You can stream logs from the command line by running:
  $ gcloud app logs tail -s default

To view your application in the web browser run:
  $ gcloud app browse --project=servletcoursework-336513"

11. After accessing the backend URL, open the file from the project root/client/src/constants/endpoints.js
12. Replace the existing URL in the root constant to the value of the copied URL with no forward slashes appended to the end of the URL
    - const root = 'https://industrial-pad-336114.nw.r.appspot.com';
    - becomes:
    - const root = 'https://servletcoursework-336513.nw.r.appspot.com';
    
### Database ###
1. In GCP Console, access SQL
2. Click "create instance" [img]
3. Click "Choose MySQL"
4. Click "Enable API"
5. Set up Database:
    - Enter an Instance ID e.g. "servletcourseworkdb"
    - Check "No password"
    - Database version: "MySQL 5.7"
    - Region: "euroupe-west2"
    - Zonal availibility: "Single Zone"
    - Machine type: "Lightweight", "1 vCPU, 3.75 GB"
    - Storage type: "SSD"
    - Storage capacity: "10 GB"
    - Connections: "Public IP"
    - Authorized networks:
        - Name: "Public"
        - Network: "0.0.0.0/0"
    - Uncheck "Automate backups"
    - Create instance
6. Go to "Cloud Storage" in GCP Console
7. Click "Create Bucket"
    - Enter a unique name e.g. "servletcourseworksqlfile"
    - Location type: "Region"
    - Location: "europe-west2"
    - Storage class: "Standard"
    - Uncheck Enforce public access prevention
    - Access control: uniform
    - Create bucket
    - Click "Upload Files"
    - Select "createfilms.sql" from inside project root
8. Go to "SQL" in GCP Console
9. Click the Instance ID of the previously created MySQL Instance
10. Click "Databases" in the left side-panel
    - Click "Create Database"
    - Enter the database name "servletcoursework"
    - Click "Create"
10. Click "Overview" in the left side-panel
11. Click "Import"
    - Source: Click "browse" and select the previously created bucket and uploaded "createfilms.sql"
    - File format: SQL
    - Database: "servletcoursework"
    - Click "Import"
12. Scroll down to the section "Connect to this instance"
13. Copy the Public IP address, e.g. "34.142.42.120"
14. Open the file found in the project root/src/ISQLOperations.java
15. Replace the IP address found in the url with the copied IP address, e.g.:
    - String url = "jdbc:mysql://34.105.148.68:3306/servletcoursework?user=root";
    - becomes:
    - String url = "jdbc:mysql://34.142.42.120/servletcoursework?user=root";
16. Repeat step 10 from the Back-end setup section

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
    - Access Key ID and Secret Access Key can be found inside the generated access key csv file
    - Region: "eu-west-2"
    - Output format: "json"
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
16. Enter "npm deploy"
17. Front-end can now be accessed via the URL from step 9

---
To do:
- cloud
- Write up
