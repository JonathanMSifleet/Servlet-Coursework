# Miscellaneous

- Frontend: https://d2icepsfo58cxr.cloudfront.net/
- Backend: https://servletcoursework-336513.nw.r.appspot.com/

# Setup

## Back-end

1. After signing-up for Google Cloud Platform, access the Console via the following url: "https://console.cloud.google.com/"
2. In the header, click "Select Project" -> "New Project"
3. Give the project an appropriate name
4. Open Cloud Shell inside the GCP Console
5. Enter the command "gcloud app create"
   - If an error occurs suggesting no project is selected, close Cloud Shell, ensure the project you created is selected, reopen Cloud Shell and retry step 5
6. When prompted enter the number "12", and press enter
7. Open the project files in Eclipse Enterprise
8. Install "Google Cloud Tools for Eclipse" via "Help" -> "Eclipse Marketplace" -> Search tab
9. Right click the project in Eclipse and click "Deploy to App Engine Standard"
10. Under Eclipse's console, the URL for the backend can be found / accessed via the console's output similar to the following:

"Deployed service [default] to [https://servletcoursework-336513.nw.r.appspot.com]"

You can stream logs from the command line by running:
$ gcloud app logs tail -s default

To view your application in the web browser run: - "gcloud app browse --project=servletcoursework-336513"

11. After accessing the backend URL, open the file from the 'project root/client/src/constants/endpoints.ts'
12. Replace the existing URL in the root constant to the value of the copied URL with no forward slashes appended to the end of the URL
    - const ROOT = 'https://industrial-pad-336114.nw.r.appspot.com';
    - becomes:
    - const ROOT = 'https://servletcoursework-336513.nw.r.appspot.com';

## Database

1. In GCP Console, access SQL
2. Click "create instance"
3. Click "Choose MySQL"
4. Click "Enable API" if not already enabled
5. Set up Database:
   - Enter an Instance ID e.g. "servletcourseworkdb"
   - Generate password, and make a note of it for later
   - Database version: "MySQL 5.7"
   - Region: "euroupe-west2"
   - Zonal availibility: "Single Zone"
   - Machine type: "Standard", "1 vCPU, 1.7 GB"
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
11. Click "Overview" in the left side-panel
12. Click "Import"
    - Source: Click "browse" and select the previously created bucket and uploaded "createfilms.sql"
    - File format: SQL
    - Database: "servletcoursework"
    - Click "Import"
13. Scroll down to the section "Connect to this instance"
14. Copy the text under "Connection name", e.g. "servletcoursework-336513:europe-west2:servletcourseworkdb2"
15. Open the file found in the project root/src/connectionPool/ConnectionPoolSingleton.java
16. Replace the variable "connectionName" with the previously copied connection name
17. Replace the variable "databaseName" with the name of the database, it should be "servletcoursework" if step 10 was followed correctly
18. Replace the variable "password" with the password generated in step 5
19. Repeat step 9 from the Back-end setup section

## Front-end

1. Access the file with the path: "Servlet-Coursework/client/serverless.yml"
2. Under the "custom" section, modify the property under ClientBucket, name

   - Replacing "9uo2ftpc" with a random string containing lowercase letters and numbers from "servletcourseworkclient9uo2ftpc-${self:provider.stage}"
   - This is due to buckets needing a unique name across the entirety of AWS

3. Sign up for AWS, and install AWS CLI
4. Create an IAM user using the AWS (web) Console
   1. Enable "Access key - Programmatic access" & "Password - AWS Management Console access"
   2. Create a group and attach "AdministratorAccess" policy
5. If no access key has been generated, generate an access key by clicking the new user -> Security credentials -> Create access key
6. In your terminal run "aws configure"
   - Access Key ID and Secret Access Key can be found inside the generated access key csv file
   - Region: "eu-west-2"
   - Output format: "json"
7. In the terminal enter "cd client"
8. Next, enter "npm i"
9. Next, enter "npm run serverlessDeploy"
10. Go to the AWS (web) console, and go to CloudFront -> Distributions
11. The URL for the frontend can be found under "Domain name" for the distribution with a description of "Servlet Coursework Client"
12. Go to "Amazon S3" in the console
13. Click on the bucket with the suffix "-prod", e.g. "servletcourseworkclient9uo2ftpc-prod"
14. Copy the name of the bucket including the suffix
15. Open the "package.json" file inside the client folder
16. Modify the "sync" script such that it is as follows: "aws s3 rm s3://[bucket name] --recursive && aws s3 sync ./build s3://[bucket name]" e.g. "aws s3 rm s3://servletcourseworkclient9uo2ftpc-prod --recursive && aws s3 sync ./build s3://servletcourseworkclient9uo2ftpc-prod"
17. Ensure that your terminal is still inside the client folder
18. Enter "npm deploy"
19. Front-end can now be accessed via the URL from step 9
