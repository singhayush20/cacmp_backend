# Running this application

## Cloudinary
This application is using cloudinary as a storage service. Since, the credentials must not be pushed to repository, they are set using environment variables.  
The credentials are picked up by the ```CloudinaryConfigurationProperties``` from the environment variables in production and, from ```secrets.properties``` (added to gitignore) in development environment.  
Provide following cloudinary properties-  
* cloudinary.api-key
* cloudinary.api-secret
* cloudinary.cloud-name

## Google Drive
**Google Drive API** is used to store the documents. The configuration file must be downloaded from **Google Cloud Console** and added to the project root folder. Also, the **public folder id** is picked up by the ```DriveConfigurationProperties``` from the ```secrets.properties``` (added to gitignore) file.
Provide the following google drive properties-
* drive.folder-id

## Twilio
This application is using **Twilio** as a storage service for images and videos. The credentials are picked up by the ```TwilioConfigurationProperties``` from ```secrets.properties``` (added to gitignore).
Provide following cloudinary properties-  
* twilio.account-sid
* twilio.auth-token
* twilio.service-sid

## Email
Email verification requires **username** and **password** of the **gmail account** which would be used for sending emails. The **password** is an app password which can be generated from the **google account settings.** The configurations are picked up by the ```EmailConfigurationProperties``` from ```secrets.properties``` (added to gitignore) file.
Provide the following email credentials-
* email.username
* email.password
